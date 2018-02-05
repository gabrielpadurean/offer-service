package org.personal.util;

import org.personal.domain.Offer;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import static java.lang.System.currentTimeMillis;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.SECOND;

/**
 * Utility methods for creating various types of offers for testing.
 *
 * @author gabrielpadurean
 */
public class OfferUtils {

    public static Offer createDummyCanceledOffer() {
        Offer offer = createDummyOffer();

        offer.setEnabled(false);

        return offer;
    }

    /**
     * Creates a dummy offer valid for the given amount of seconds.
     */
    public static Offer createDummyOfferValidFor(int seconds) {
        Offer offer = createDummyOffer();

        offer.setStartDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(SECOND, seconds);
        offer.setEndDate(calendar.getTime());

        return offer;
    }

    /**
     * Create dummy offer that is already expired.
     */
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

    /**
     * Create dummy offer valid for 10 hours.
     */
    public static Offer createDummyOffer() {
        Offer offer = new Offer();

        offer.setProductId(1L);
        offer.setName("A test offer");
        offer.setDescription("Test description");
        offer.setActive(true);
        offer.setPrice(5d);
        offer.setCurrency(Currency.getInstance("EUR"));

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(HOUR, 5);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(HOUR, -5);

        offer.setStartDate(endCalendar.getTime());
        offer.setEndDate(startCalendar.getTime());

        return offer;
    }
}