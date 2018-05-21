package com.mavha.personascrud.mappers;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        final ResponseEntity resp = ResponseEntity.builder()
                .addError(exception.getOriginalMessage()).build();

        return Response.status(Status.BAD_REQUEST).entity(resp).build();
    }
}
