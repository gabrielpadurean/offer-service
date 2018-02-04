package org.personal.service;

import org.personal.domain.Offer;
import org.personal.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gabrielpadurean
 */
@Service
public class ValidatorService {
    @Autowired
    private List<Validator<Offer>> offerValidators;


    /**
     * Validates the given offer object against a list of specific validators.
     */
    public void validate(Offer offer) {
        offerValidators.forEach(offerValidator -> offerValidator.validate(offer));
    }
}