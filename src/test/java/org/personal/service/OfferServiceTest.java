package org.personal.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.personal.domain.Offer;
import org.personal.exception.ExpiredOfferException;
import org.personal.exception.NotFoundOfferException;
import org.personal.repository.OfferRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.personal.util.OfferUtils.createDummyCanceledOffer;
import static org.personal.util.OfferUtils.createDummyExpiredOffer;
import static org.personal.util.OfferUtils.createDummyOffer;

/**
 * @author gabrielpadurean
 */
public class OfferServiceTest {
    @Mock
    private OfferRepository offerRepository;

    @Captor
    private ArgumentCaptor<Offer> offerArgumentCaptor;

    @InjectMocks
    private OfferService victim;


    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testSaveSuccessfully() {
        Offer offer = createDummyOffer();

        when(offerRepository.save(offer)).thenReturn(offer);

        Offer createdOffer = victim.save(offer);

        assertEquals(offer, createdOffer);
        verify(offerRepository, times(1)).save(offer);
    }

    @Test(expected = RuntimeException.class)
    public void testSaveWithException() {
        Offer offer = createDummyOffer();

        when(offerRepository.save(offer)).thenThrow(new RuntimeException());

        victim.save(offer);
    }

    @Test
    public void testUpdateSuccessfully() {
        long offerId = 1;
        Offer offer = createDummyOffer();
        offer.setId(offerId);

        when(offerRepository.findOne(offerId)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);

        Offer updatedOffer = victim.update(offer);

        assertEquals(offer, updatedOffer);
        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, times(1)).save(offer);
    }

    @Test(expected = NotFoundOfferException.class)
    public void testUpdateNotFoundOffer() {
        long offerId = 1;
        Offer offer = createDummyOffer();

        when(offerRepository.findOne(offerId)).thenReturn(null);

        victim.update(offer);

        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    public void testCancelSuccessfully() {
        long offerId = 1;
        Offer offer = createDummyOffer();

        when(offerRepository.findOne(offerId)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);

        victim.cancel(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, times(1)).save(offerArgumentCaptor.capture());
        assertEquals(false, offerArgumentCaptor.getValue().isEnabled());
    }

    @Test(expected = NotFoundOfferException.class)
    public void testCancelNotFoundOffer() {
        long offerId = 1;

        when(offerRepository.findOne(offerId)).thenReturn(null);

        victim.cancel(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    public void testDeleteSuccessfully() {
        long offerId = 1;

        when(offerRepository.findOne(offerId)).thenReturn(new Offer());

        victim.delete(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, times(1)).delete(offerId);
    }

    @Test(expected = NotFoundOfferException.class)
    public void testDeleteNotFoundOffer() {
        long offerId = 1;

        when(offerRepository.findOne(offerId)).thenReturn(null);

        victim.delete(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
        verify(offerRepository, never()).delete(offerId);
    }

    @Test
    public void testGetOfferSuccessfully() {
        long offerId = 1;
        Offer offer = createDummyOffer();

        when(offerRepository.findOne(offerId)).thenReturn(offer);

        Offer retrievedOffer = victim.get(offerId);

        assertEquals(offer, retrievedOffer);
        verify(offerRepository, times(1)).findOne(offerId);
    }

    @Test(expected = ExpiredOfferException.class)
    public void testGetInactiveOffer() {
        long offerId = 1;
        Offer offer = createDummyCanceledOffer();

        when(offerRepository.findOne(offerId)).thenReturn(offer);

        victim.get(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
    }

    @Test(expected = ExpiredOfferException.class)
    public void testGetExpiredOffer() {
        long offerId = 1;
        Offer offer = createDummyExpiredOffer();

        when(offerRepository.findOne(offerId)).thenReturn(offer);

        victim.get(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
    }

    @Test(expected = NotFoundOfferException.class)
    public void testGetNotFoundOffer() {
        long offerId = 1;

        when(offerRepository.findOne(offerId)).thenReturn(null);

        victim.get(offerId);

        verify(offerRepository, times(1)).findOne(offerId);
    }
}