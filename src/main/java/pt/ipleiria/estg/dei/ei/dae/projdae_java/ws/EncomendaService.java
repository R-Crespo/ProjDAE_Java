package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.RegraDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EncomendaProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("encomendas") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
public class EncomendaService {
    @EJB
    private EncomendaBean encomendaBean;@EJB
    private EncomendaProdutoBean encomendaProdutoBean;

    // Converts an entity Encomenda to a DTO Encomenda class
    private EncomendaDTO toDTO(Encomenda encomenda) {
        /*
        private long id;
        private String operadorUsername;
        private String clienteUsername;
        private String morada;
        private String estado;
        private Date dataEntrega;
        private String armazem;
        private List<EncomendaProdutoDTO> encomendaProdutoDTOs;
        private long embalagemTransporteId;
        private List<SensorDTO> sensoreDTOs;
        */
        String operadorUsername = "Não Atribuido";
        if (encomenda.getOperador() != null){
            operadorUsername = encomenda.getOperador().getUsername();
        }
        return new EncomendaDTO(
                encomenda.getId(),
                operadorUsername,
                encomenda.getCliente().getUsername(),
                encomenda.getMorada(),
                encomenda.getEstado(),
                encomenda.getDataEntrega(),
                encomenda.getArmazem(),
                encomenda.getEmbalagemTransporte().getId()
        );
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
    @Path("/naoAtribuidas") // /api/encomendas/naoAtribuidas
    public List<EncomendaDTO> getAllEncomendasNaoAtribuidas() {
        return toDTOs(encomendaBean.getAllNaoAtribuidas());
    }

    @GET
    @Path("/operador/{username}") // /api/encomendas/operador/{username}
    public List<EncomendaDTO> getEncomendasOperador(@PathParam("username") String operadorUsername) {
        return toDTOs(encomendaBean.getEncomendasOperador(operadorUsername));
    }

    @GET
    @Path("/operador/{username}/entregas") // /api/encomendas/operador/{username}/entregas
    public List<EncomendaDTO> getEncomendasOperadorEntreges(@PathParam("username") String operadorUsername) {
        return toDTOs(encomendaBean.getEncomendasOperadorEntreges(operadorUsername));
    }

    @GET
    @Path("/cliente/{username}") // /api/encomendas/clientes/{username}
    public List<EncomendaDTO> getEncomendasCliente(@PathParam("username") String clienteUsername) {
        return toDTOs(encomendaBean.getEncomendasCliente(clienteUsername));
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
    public Response createNewEncomenda (EncomendaDTO encomendaDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        System.out.println("HV");
        Encomenda encomenda = encomendaBean.create(
                encomendaDTO.getClienteUsername(),
                encomendaDTO.getMorada(),
                encomendaDTO.getArmazem(),
                encomendaDTO.getEmbalagemTransporteId()
        );
        System.out.println(encomenda.getId());
        for(EncomendaProdutoDTO encomendaProdutoDTO : encomendaDTO.getEncomendaProdutoDTOs()){
            encomendaProdutoBean.create(
                    encomenda.getId(),
                    encomendaProdutoDTO.getProdutoId(),
                    encomendaProdutoDTO.getQuantidade());
        }

        //Encomenda encomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(encomenda)).build();
    }

    @PUT
    @Path("/{encomendaId}") // /api/encomendas/{id}
    public Response updateEncomenda (EncomendaDTO encomendaDTO) throws MyEntityNotFoundException{
        encomendaBean.update(
                encomendaDTO.getId(),
                encomendaDTO.getOperadorUsername(),
                encomendaDTO.getClienteUsername(),
                encomendaDTO.getMorada(),
                encomendaDTO.getEstado(),
                encomendaDTO.getDataEntrega(),
                encomendaDTO.getArmazem(),
                encomendaDTO.getEmbalagemTransporteId());

        Encomenda encomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.OK).entity(toDTO(encomenda)).build();
    }
}
