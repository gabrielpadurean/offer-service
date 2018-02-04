package org.personal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingFieldException extends RuntimeException {

    public MissingFieldException(String message) {
        super(message);
    }
}