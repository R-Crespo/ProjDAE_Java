package pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyForbiddenException;

import java.util.logging.Logger;

public class MyForbiddenExceptionMapper implements ExceptionMapper<MyForbiddenException> {
    private static final Logger logger = Logger.getLogger(MyForbiddenException.class.getCanonicalName());

    @Override
    public Response toResponse(MyForbiddenException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorMsg)
                .build();
    }
}
