package org.personal.util;

import org.personal.domain.Offer;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * @author gabrielpadurean
 */
public class OfferUtils {

    public static Offer createDummyCanceledOffer() {
        Offer offer = createDummyOffer();

        offer.setEnabled(false);

        return offer;
    }

    public static Offer createDummyExpiredOffer() {
        Offer offer = createDummyOffer();

        /**
         * Move offer validity in the past.
         */
        long currentTimeMillis = currentTimeMillis();
        offer.setStartDate(new Date(currentTimeMillis - (1000 * 60)));
        offer.setEndDate(new Date(currentTimeMillis - (1000 * 30)));

        return offer;
    }

    public static Offer createDummyOffer() {
        Offer offer = new Offer();

        offer.setProductId(1L);
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