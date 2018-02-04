package org.personal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gabrielpadurean
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundOfferException extends RuntimeException {
}