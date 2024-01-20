package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.ObservacaoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.ObservacaoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("observacoes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ObservacaoService {

    @EJB
    private ObservacaoBean observacaoBean;

    // Converte uma entidade Observacao para um DTO Observacao
    public ObservacaoDTO toDTO(Observacao observacao) {
        return new ObservacaoDTO(
                observacao.getId(),
                observacao.getSensor().getId(),
                observacao.getTimestamp(),
                observacao.getTipo(),
                observacao.getValor(),
                observacao.getUnidadeMedida()
        );
    }

    // Converte uma lista de entidades Observacao para uma lista de DTOs Observacao
    private List<ObservacaoDTO> toDTOs(List<Observacao> observacoes) {
        return observacoes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @POST
    @Path("/{sensorId}")
    public Response createObservacao( ObservacaoDTO observacaoDTO) {
        try {
            observacaoBean.create(
                    observacaoDTO.getSensorId(),
                    observacaoDTO.getValor(),
                    observacaoDTO.getTipo(),
                    observacaoDTO.getUnidadeMedida()
            );
            // Aqui poderíamos buscar a observação recém-criada para retornar o DTO correspondente
            Observacao observacao = observacaoBean.find(observacaoDTO.getId());
            return Response.status(Response.Status.CREATED).entity(toDTO(observacao)).build();
        } catch (MyEntityExistsException | MyEntityNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{encomendaId}")
    public Response getObservacoesPorEncomenda(@PathParam("encomendaId") long encomendaId) {
        List<Observacao> observacoes = observacaoBean.getObservacoesPorEncomenda(encomendaId);
        return Response.ok(toDTOs(observacoes)).build();
    }

    @GET
    @Path("/{produtoId}")
    public Response getObservacoesPorProdutoNaEncomenda(@PathParam("produtoId") long produtoId) {
        List<Observacao> observacoes = observacaoBean.getObservacoesPorProduto(produtoId);
        return Response.ok(toDTOs(observacoes)).build();
    }


}
