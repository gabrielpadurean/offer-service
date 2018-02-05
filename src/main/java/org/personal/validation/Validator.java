package org.personal.validation;

/**
 * Generic interface for creating uniform validators.
 *
 * @author gabrielpadurean
 */
public interface Validator<T> {

    void validate(T t);
}