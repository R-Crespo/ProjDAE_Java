package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("encomendas") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class EncomendaService {
    @EJB
    private EncomendaBean encomendaBean;

    // Converts an entity Student to a DTO Student class
    private EncomendaDTO toDTO(Encomenda encomenda) {
        return new EncomendaDTO(
                encomenda.getId(),
                encomenda.getOperador().getUsername(),
                encomenda.getCliente().getUsername(),
                encomenda.getMorada(),
                encomenda.getEstado(),
                encomenda.getDataEntrega(),
                encomenda.getArmazem());
    }
    // converts an entire list of entities into a list of DTOs
    private List<EncomendaDTO> toDTOs(List<Encomenda> encomendas) {
        return encomendas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") // /api/encomendas
    public List<EncomendaDTO> getAllEncomendas() {
        return toDTOs(encomendaBean.getAll());
    }

    @GET
    @Path("/{encomendaId}") // /api/encomendas/{encomendaId}
    public Response getEncomendaDetails(@PathParam("encomendaId") long encomendaId) {
        Encomenda encomenda = encomendaBean.find(encomendaId);
        if (encomenda != null) {
            return Response.ok(toDTO(encomenda)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_ENCOMENDA").build();
    }

    @POST
    @Path("/") // /api/encomendas
    public Response createNewStudent (EncomendaDTO encomendaDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        encomendaBean.create(
                encomendaDTO.getId(),
                encomendaDTO.getClienteUsername(),
                encomendaDTO.getMorada(),
                encomendaDTO.getEstado(),
                encomendaDTO.getArmazem()
        );
        Encomenda encomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(encomenda)).build();
    }

    @PUT
    @Path("/{username}/{encomendaId}") // /api/encomendas/{id}
    public Response updateEncomenda (EncomendaDTO encomendaDTO) throws MyEntityNotFoundException{
        encomendaBean.update(encomendaDTO.getId(), encomendaDTO.getClienteUsername(), encomendaDTO.getMorada(), encomendaDTO.getEstado(), encomendaDTO.getArmazem(), encomendaDTO.getDataEntrega(), encomendaDTO.getOperadorUsername());
        Encomenda encomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.OK).entity(toDTO(encomenda)).build();
    }
}
