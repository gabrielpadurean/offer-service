package org.personal.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(BAD_REQUEST)
public class MissingFieldException extends RuntimeException {

    public MissingFieldException(String message) {
        super(message);
    }
}