package com.mavha.personascrud.mappers;

import java.util.ArrayList;
import java.util.List;

public class ResponseEntity {

    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ResponseEntity resp = new ResponseEntity();

        public Builder addError(String error) {
            if (resp.errors == null) {
                resp.errors = new ArrayList<String>(5);
            }
            resp.errors.add(error);
            return this;
        }

        public ResponseEntity build() {
            return resp;
        }
    }
}
