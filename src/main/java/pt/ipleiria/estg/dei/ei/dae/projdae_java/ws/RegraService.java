package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.RegraDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.RegraBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Regra;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("regras") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class RegraService {
    @EJB
    private RegraBean regraBean;

    // Converts an entity Regra to a DTO Regra class
    private RegraDTO toDTO(Regra regra) {
        /*
        private long id;
        private int valor;
        private String comparador;
        private String mensagem;
        private String tipoSensor;
        */
        return new RegraDTO(
                regra.getId(),
                regra.getValor(),
                regra.getComparador(),
                regra.getMensagem(),
                regra.getTipoSensor(),
                regra.getProduto().getId()
        );
    }
    // converts an entire list of entities into a list of DTOs
    private List<RegraDTO> toDTOs(List<Regra> regras) {
        return regras.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") // /api/regras
    public List<RegraDTO> getAllRegras() {
        return toDTOs(regraBean.getAll());
    }

    @GET
    @Path("/{produtoId}") // /api/regras/{produtoId}
    public List<RegraDTO> getAllRegrasProduto(@PathParam("produtoId") long produtoId) {
        return toDTOs(regraBean.getAllRegrasProduto(produtoId));
    }

    @POST
    @Path("/") // /api/regras
    public Response createNewRegra (RegraDTO regraDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Regra regra = regraBean.create(
                regraDTO.getValor(),
                regraDTO.getComparador(),
                regraDTO.getMensagem(),
                regraDTO.getTipoSensor(),
                regraDTO.getProdutoId());

        return Response.status(Response.Status.CREATED).entity(toDTO(regra)).build();
    }

    @PUT
    @Path("/{regraId}") // /api/regras/{regraId}
    public Response updateRegra (RegraDTO regraDTO) throws MyEntityNotFoundException{
        regraBean.update(
                regraDTO.getId(),
                regraDTO.getValor(),
                regraDTO.getComparador(),
                regraDTO.getMensagem(),
                regraDTO.getTipoSensor()
                );
        Regra regra = regraBean.find(regraDTO.getId());
        return Response.status(Response.Status.OK).entity(toDTO(regra)).build();
    }

    @DELETE
    @Path("/{regraId}") // /api/regras/{regraId}
    public Response deleteRegra(@PathParam("regraId") long regraId) throws MyEntityNotFoundException {
        Regra regraRemoved = regraBean.delete(regraId);

        return Response.status(Response.Status.OK).entity(toDTO(regraRemoved)).build();
    }
}
