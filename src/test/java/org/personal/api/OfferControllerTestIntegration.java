package org.personal.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.personal.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.personal.util.OfferUtils.createDummyOffer;
import static org.personal.util.OfferUtils.createDummyOfferValidFor;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author gabrielpadurean
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTestIntegration {
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testCreateOfferSuccessfully() {
        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", createDummyOffer(), Offer.class);
        Offer createdOffer = createOfferResponse.getBody();
        assertEquals(CREATED, createOfferResponse.getStatusCode());
        assertTrue(createdOffer.getId() > 0);

        Offer retrievedOffer = restTemplate.getForObject("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertNotNull(retrievedOffer);
        assertEquals(createdOffer, retrievedOffer);
    }

    @Test
    public void testCreateOfferWithMissingFields() {
        Offer initialOffer = createDummyOffer();
        initialOffer.setName(null);
        initialOffer.setCurrency(null);

        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", initialOffer, Offer.class);
        assertEquals(BAD_REQUEST, createOfferResponse.getStatusCode());
    }

    @Test
    public void testCreateOfferWithWrongPrice() {
        Offer initialOffer = createDummyOffer();
        initialOffer.setPrice(-16);
        initialOffer.setCurrency(null);

        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", initialOffer, Offer.class);
        assertEquals(BAD_REQUEST, createOfferResponse.getStatusCode());
    }

    @Test
    public void testCreateOfferWithWrongDates() throws InterruptedException {
        Offer initialOffer = createDummyOffer();
        initialOffer.setEndDate(new Date());
        SECONDS.sleep(1);
        initialOffer.setStartDate(new Date());

        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", initialOffer, Offer.class);
        assertEquals(BAD_REQUEST, createOfferResponse.getStatusCode());
    }

    @Test
    public void testCancelOfferSuccessfully() {
        Offer initialOffer = createDummyOffer();

        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();
        assertEquals(CREATED, createOfferResponse.getStatusCode());
        assertTrue(createdOffer.getId() > 0);

        ResponseEntity<String> cancelOfferResponse = restTemplate.exchange("/offers/{offerId}/cancel", PUT, null, String.class, createdOffer.getId());
        assertEquals(NO_CONTENT, cancelOfferResponse.getStatusCode());

        ResponseEntity<Offer> retrieveOfferResponse = restTemplate.getForEntity("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertEquals(GONE, retrieveOfferResponse.getStatusCode());
    }

    @Test
    public void testCancelNonExistingOffer() {
        ResponseEntity<String> cancelOfferResponse = restTemplate.exchange("/offers/{offerId}/cancel", PUT, null, String.class, 9999);
        assertEquals(NOT_FOUND, cancelOfferResponse.getStatusCode());
    }

    @Test
    public void testGetNonExistingOffer() {
        ResponseEntity<Offer> retrieveOfferResponse = restTemplate.getForEntity("/offers/{offerId}", Offer.class, 99999L);
        assertEquals(NOT_FOUND, retrieveOfferResponse.getStatusCode());
    }

    @Test
    public void testGetExpiredOffer() throws InterruptedException {
        /**
         * Create offer valid for ~30 seconds.
         */
        Offer initialOffer = createDummyOfferValidFor(20);

        ResponseEntity<Offer> createOfferResponse = restTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();
        assertEquals(CREATED, createOfferResponse.getStatusCode());
        assertTrue(createdOffer.getId() > 0);

        /**
         * At this time the offer should be available.
         */
        Offer retrievedOffer = restTemplate.getForObject("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertNotNull(retrievedOffer);
        assertEquals(createdOffer, retrievedOffer);

        /**
         * Wait for the offer to expire.
         */
        SECONDS.sleep(30);

        ResponseEntity<Offer> retrieveOfferResponse = restTemplate.getForEntity("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertEquals(GONE, retrieveOfferResponse.getStatusCode());
    }

    @Test
    public void testGetOffers() throws InterruptedException {
        /**
         * Get initial number of offers.
         */
        ResponseEntity<List<Offer>> retrieveOffersResponse = restTemplate.exchange("/offers", GET, null, new ParameterizedTypeReference<List<Offer>>(){});
        assertEquals(HttpStatus.OK, retrieveOffersResponse.getStatusCode());
        int initialNumberOfOffers = retrieveOffersResponse.getBody().size();

        /**
         * Create two more valid offers and one that expires in 20 seconds.
         */
        ResponseEntity<Offer> createOfferResponse = null;

        createOfferResponse = restTemplate.postForEntity("/offers", createDummyOffer(), Offer.class);
        assertEquals(CREATED, createOfferResponse.getStatusCode());

        createOfferResponse = restTemplate.postForEntity("/offers", createDummyOffer(), Offer.class);
        assertEquals(CREATED, createOfferResponse.getStatusCode());

        createOfferResponse = restTemplate.postForEntity("/offers", createDummyOfferValidFor(20), Offer.class);
        assertEquals(CREATED, createOfferResponse.getStatusCode());

        /**
         * Wait for one offer to expire.
         */
        SECONDS.sleep(30);

        /**
         * Check new numbers of retrieved offers.
         */
        retrieveOffersResponse = restTemplate.exchange("/offers", GET, null, new ParameterizedTypeReference<List<Offer>>(){});
        assertEquals(OK, retrieveOffersResponse.getStatusCode());
        assertEquals(initialNumberOfOffers + 2, retrieveOffersResponse.getBody().size());
    }
}