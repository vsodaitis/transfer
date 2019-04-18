package com.vytenis.transfer.resource;

import javax.json.Json;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int code = 500;
        return Response.status(code)
                .entity(Json.createObjectBuilder()
                        .add("error", exception.getMessage())
                        .add("code", code)
                        .build())
                .build();
    }
}
