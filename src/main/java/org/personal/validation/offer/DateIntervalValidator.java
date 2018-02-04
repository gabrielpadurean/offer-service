package org.personal.validation.offer;

import org.personal.domain.Offer;
import org.personal.validation.Validator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author gabrielpadurean
 */
@Component
@Order(value = 3)
public class DateIntervalValidator implements Validator<Offer> {

    @Override
    public void validate(Offer offer) {

    }
}