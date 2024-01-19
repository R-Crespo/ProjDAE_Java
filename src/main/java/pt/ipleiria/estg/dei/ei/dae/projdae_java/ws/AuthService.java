package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.UserDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.AuthenticationFilter;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;
    @EJB
    private UserBean userBean;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/user")
    @Authenticated
    public Response getUserDetails(@Context SecurityContext securityContext) {
        // Extract the username from the token
        String username = securityContext.getUserPrincipal().getName();

        User user = userBean.find(username);
        if (user != null) {
            UserDTO userDTO = userToDTO(user);
            return Response.ok(userDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private UserDTO userToDTO(User user){
        return new UserDTO(user.getUsername(), user.getNome(), user.getDtype());
    }

}
