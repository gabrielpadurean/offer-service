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

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

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

        Offer createdOffer = victim.createOffer(offer);

        assertEquals(offer, createdOffer);
        verify(validatorService, times(1)).validate(offer);
        verify(offerService, times(1)).save(offer);
    }

    @Test
    public void testUpdateOfferSuccessfully() {
        Offer offer = createDummyOffer();

        doNothing().when(validatorService).validate(offer);
        when(offerService.update(offer)).thenReturn(offer);

        Offer updatedOffer = victim.updateOffer(offer);

        assertEquals(offer, updatedOffer);
        verify(validatorService, times(1)).validate(offer);
        verify(offerService, times(1)).update(offer);
    }

    @Test
    public void testDeleteOfferSuccessfully() {
        long offerId = 1;

        doNothing().when(offerService).delete(offerId);

        victim.deleteOffer(offerId);

        verify(offerService, times(1)).delete(offerId);
    }

    @Test
    public void testCancelOfferSuccessfully() {
        long offerId = 1;

        doNothing().when(offerService).cancel(offerId);

        victim.cancelOffer(offerId);

        verify(offerService, times(1)).cancel(offerId);
    }

    @Test
    public void testGetOfferSuccessfully() {
        Offer offer = createDummyOffer();
        long offerId = offer.getId();

        when(offerService.get(offerId)).thenReturn(offer);

        Offer retrievedOffer = victim.getOffer(offerId);

        assertNotNull(offer);
        assertEquals(offer, retrievedOffer);
        verify(offerService, times(1)).get(offerId);
    }

    @Test
    public void testGetOffersSuccessfully() {
        Pageable pageable = new PageRequest(0, 10);

        when(offerService.get(pageable)).thenReturn(Collections.emptyList());

        List<Offer> offers = victim.getOffers(pageable);

        assertEquals(0, offers.size());
        verify(offerService, times(1)).get(pageable);
    }

    private Offer createDummyOffer() {
        Offer offer = new Offer();

        offer.setId(1);
        offer.setProductId(1);
        offer.setName("A test offer");
        offer.setDescription("Test description");
        offer.setActive(true);
        offer.setPrice(5d);
        offer.setCurrency(Currency.getInstance("EUR"));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);

        offer.setStartDate(new Date());
        offer.setEndDate(calendar.getTime());

        return offer;
    }
}