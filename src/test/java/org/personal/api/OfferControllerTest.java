package org.personal.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.personal.domain.Offer;
import org.personal.service.OfferService;
import org.personal.service.ValidatorService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.personal.util.OfferUtils.createDummyOffer;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author gabrielpadurean
 */
public class OfferControllerTest {
    @Mock
    private OfferService offerService;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private OfferController victim;


    @Before
    public void setup() {
        initMocks(this);;
    }

    @Test
    public void testCreateOfferSuccessfully() {
        Offer offer = createDummyOffer();

        doNothing().when(validatorService).validate(offer);
        when(offerService.save(offer)).thenReturn(offer);

        ResponseEntity<Offer> createOfferResponse = victim.createOffer(offer);

        assertEquals(CREATED, createOfferResponse.getStatusCode());
        assertEquals(offer, createOfferResponse.getBody());
        verify(validatorService, times(1)).validate(offer);
        verify(offerService, times(1)).save(offer);
    }

    @Test
    public void testUpdateOfferSuccessfully() {
        Offer offer = createDummyOffer();

        doNothing().when(validatorService).validate(offer);
        when(offerService.update(offer)).thenReturn(offer);

        ResponseEntity<Offer> updateOfferResponse = victim.updateOffer(offer);

        assertEquals(OK, updateOfferResponse.getStatusCode());
        assertEquals(offer, updateOfferResponse.getBody());
        verify(validatorService, times(1)).validate(offer);
        verify(offerService, times(1)).update(offer);
    }

    @Test
    public void testDeleteOfferSuccessfully() {
        long offerId = 1;

        doNothing().when(offerService).delete(offerId);

        ResponseEntity<Offer> deleteOfferResponse = victim.deleteOffer(offerId);

        assertEquals(NO_CONTENT, deleteOfferResponse.getStatusCode());
        verify(offerService, times(1)).delete(offerId);
    }

    @Test
    public void testCancelOfferSuccessfully() {
        long offerId = 1;

        doNothing().when(offerService).cancel(offerId);

        ResponseEntity<Offer> cancelOfferResponse = victim.cancelOffer(offerId);

        assertEquals(NO_CONTENT, cancelOfferResponse.getStatusCode());
        verify(offerService, times(1)).cancel(offerId);
    }

    @Test
    public void testGetOfferSuccessfully() {
        Offer offer = createDummyOffer();
        Long offerId = offer.getId();

        when(offerService.get(offerId)).thenReturn(offer);

        ResponseEntity<Offer> retrieveOfferResponse = victim.getOffer(offerId);

        assertEquals(OK, retrieveOfferResponse.getStatusCode());
        assertEquals(offer, retrieveOfferResponse.getBody());
        verify(offerService, times(1)).get(offerId);
    }

    @Test
    public void testGetOffersSuccessfully() {
        Pageable pageable = new PageRequest(0, 10);

        when(offerService.get(pageable)).thenReturn(emptyList());

        ResponseEntity<List<Offer>> retrieveOffersResponse = victim.getOffers(pageable);

        assertEquals(OK, retrieveOffersResponse.getStatusCode());
        assertEquals(0, retrieveOffersResponse.getBody().size());
        verify(offerService, times(1)).get(pageable);
    }
}