package com.mavha.personascrud.validation;

import javax.validation.ValidationException;

public final class Validate {
    private Validate() {
        throw new AssertionError("No instance for you");
    }

    public static void notNull(final Object obj, final String msg)
            throws ValidationException {
        if (obj == null) {
            throw new ValidationException(msg);
        }
    }
}
