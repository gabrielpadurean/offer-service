package org.personal.validation;

/**
 * @author gabrielpadurean
 */
public interface Validator<T> {

    void validate(T t);
}