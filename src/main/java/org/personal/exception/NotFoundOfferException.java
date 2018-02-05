package org.personal.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(NOT_FOUND)
public class NotFoundOfferException extends RuntimeException {

    public NotFoundOfferException(String message) {
        super(message);
    }
}