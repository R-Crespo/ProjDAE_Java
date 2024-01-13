package pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyNotAuthorizedException;

import java.util.logging.Logger;

public class MyNotAuthorizedExceptionMapper implements ExceptionMapper<MyNotAuthorizedException> {
    private static final Logger logger =
            Logger.getLogger(MyNotAuthorizedException.class.getCanonicalName());
    @Override
    public Response toResponse(MyNotAuthorizedException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorMsg)
                .build();
    }
}
