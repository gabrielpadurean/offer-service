package org.personal.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.personal.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.personal.util.OfferUtils.createDummyExpiredOffer;
import static org.personal.util.OfferUtils.createDummyOffer;

/**
 * @author gabrielpadurean
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTestIntegration {
    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testCreateOfferSuccessfully() {
        Offer initialOffer = createDummyOffer();

        ResponseEntity<Offer> createOfferResponse = testRestTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();
        assertEquals(HttpStatus.OK, createOfferResponse.getStatusCode());
        assertTrue(createdOffer.getId() > 0);

        Offer retrievedOffer = testRestTemplate.getForObject("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertNotNull(retrievedOffer);
        assertEquals(createdOffer, retrievedOffer);
    }

    @Test
    public void testCreateOfferWithMissingFields() {
        Offer initialOffer = createDummyOffer();
        initialOffer.setName(null);
        initialOffer.setCurrency(null);

        ResponseEntity<Offer> createOfferResponse = testRestTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, createOfferResponse.getStatusCode());
        assertNull(createdOffer);
    }

    @Test
    public void testCreateOfferWithWrongPrice() {
        Offer initialOffer = createDummyOffer();
        initialOffer.setPrice(-16);
        initialOffer.setCurrency(null);

        ResponseEntity<Offer> createOfferResponse = testRestTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, createOfferResponse.getStatusCode());
        assertNull(createdOffer);
    }

    @Test
    public void testCreateOfferWithWrongDates() {
        Offer initialOffer = createDummyExpiredOffer();

        ResponseEntity<Offer> createOfferResponse = testRestTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, createOfferResponse.getStatusCode());
        assertNull(createdOffer);
    }

    @Test
    public void testCancelOfferSuccessfully() {
        Offer initialOffer = createDummyOffer();

        ResponseEntity<Offer> createOfferResponse = testRestTemplate.postForEntity("/offers", initialOffer, Offer.class);
        Offer createdOffer = createOfferResponse.getBody();
        assertEquals(HttpStatus.OK, createOfferResponse.getStatusCode());
        assertTrue(createdOffer.getId() > 0);

        ResponseEntity<String> cancelOfferResponse = testRestTemplate.exchange("/offers/{offerId}/cancel", HttpMethod.PUT, null, String.class, createdOffer.getId());
        assertEquals(HttpStatus.OK, cancelOfferResponse.getStatusCode());

        ResponseEntity<Offer> retrieveOfferResponse = testRestTemplate.getForEntity("/offers/{offerId}", Offer.class, createdOffer.getId());
        assertEquals(HttpStatus.GONE, retrieveOfferResponse.getStatusCode());
    }

    @Test
    public void testCancelNonExistingOffer() {
        ResponseEntity<String> cancelOfferResponse = testRestTemplate.exchange("/offers/{offerId}/cancel", HttpMethod.PUT, null, String.class, 9999);
        assertEquals(HttpStatus.NOT_FOUND, cancelOfferResponse.getStatusCode());
    }
}