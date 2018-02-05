package org.personal.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.GONE;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(GONE)
public class ExpiredOfferException extends RuntimeException {

    public ExpiredOfferException(String message) {
        super(message);
    }
}