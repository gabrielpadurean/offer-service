package org.personal.util;

import org.personal.domain.Offer;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

/**
 * @author gabrielpadurean
 */
public class OfferUtils {

    public static Offer createDummyOffer() {
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