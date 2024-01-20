package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
@Path("embalagensProduto") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
//@RolesAllowed("Administrador")
public class EmbalagemProdutoService {

    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;

    @EJB
    private ProdutoBean produtoBean;

    public EmbalagemProdutoDTO toDTO(EmbalagemProduto embalagemProduto){
        return new EmbalagemProdutoDTO(
                embalagemProduto.getId(),
                embalagemProduto.getTipo(),
                embalagemProduto.getFuncao(),
                embalagemProduto.getDataFabrico(),
                embalagemProduto.getMaterial(),
                embalagemProduto.getPeso(),
                embalagemProduto.getVolume(),
                embalagemProduto.getProduto().getId()
        );
    }
    @GET
    @Path("{id}")
    public EmbalagemProdutoDTO getEmbalagemProduto(@PathParam("id") long id) {
        return toDTO(embalagemProdutoBean.find(id));
    }

    private List<EmbalagemProdutoDTO> toDTOs(List<EmbalagemProduto> embalagens) {
        return embalagens.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<EmbalagemProdutoDTO> getAllEmbalagensProduto() {
        return toDTOs(embalagemProdutoBean.getAll());
    }
}
