package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EncomendaBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
    @Path("/") //api/encomendas
    public List<EncomendaDTO> getAllEncomendass() {
        return toDTOs(encomendaBean.getAll());
    }

    @POST
    @Path("/") //api/encomendas long id, String clienteUsername, String morada, String estado, String armazem, Date dataEntrega
    public Response createNewStudent (EncomendaDTO encomendaDTO) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        encomendaBean.create(
                encomendaDTO.getId(),
                encomendaDTO.getClienteUsername(),
                encomendaDTO.getMorada(),
                encomendaDTO.getEstado(),
                encomendaDTO.getArmazem(),
                encomendaDTO.getDataEntrega()
        );
        Encomenda encomenda = encomendaBean.find(encomendaDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(encomenda)).build();
    }
}
