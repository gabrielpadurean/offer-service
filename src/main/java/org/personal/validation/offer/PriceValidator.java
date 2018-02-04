package org.personal.validation.offer;

import org.personal.domain.Offer;
import org.personal.exception.InvalidPriceException;
import org.personal.validation.Validator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author gabrielpadurean
 */
@Component
@Order(value = 2)
public class PriceValidator implements Validator<Offer> {

    @Override
    public void validate(Offer offer) {
        if (offer.getPrice() <= 0) {
            throw new InvalidPriceException("Price must be a positive number");
        }
    }
}