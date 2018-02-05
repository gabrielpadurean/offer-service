package org.personal.validation.offer;

import org.personal.domain.Offer;
import org.personal.exception.MissingFieldException;
import org.personal.validation.Validator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * Checks for missing field on a {@link Offer} instance.
 * Should be used first in the chain of validators.
 *
 * @author gabrielpadurean
 */
@Component
@Order(value = 1)
public class RequiredFieldsValidator implements Validator<Offer> {

    @Override
    public void validate(Offer offer) {
        try {
            requireNonNull(offer.getName(), "Name is required");
            requireNonNull(offer.getDescription(), "Description is required");
            requireNonNull(offer.getProductId(), "Product id is required");
            requireNonNull(offer.getCurrency(), "Currency is required");
            requireNonNull(offer.getStartDate(), "Start date is required");
            requireNonNull(offer.getEndDate(), "End date is required");
        } catch (NullPointerException e) {
            throw new MissingFieldException(e.getMessage());
        }
    }
}