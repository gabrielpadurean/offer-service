package org.personal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(HttpStatus.GONE)
public class ExpiredOfferException extends RuntimeException {

    public ExpiredOfferException(String message) {
        super(message);
    }
}