package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.ProdutoEmbalagemRegraDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.RegraDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.RegraBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;

import java.util.List;
import java.util.stream.Collectors;

@Path("produtos")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProdutoService {

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private RegraBean regraBean;

    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;

    private ProdutoDTO toDTO(Produto produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getTipo(),
                produto.getMarca(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getUnidadeMedida(),
                produto.getPreco()
        );
    }

    public List<ProdutoDTO> toDTOs(List<Produto> produtos){
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public  List<ProdutoDTO> getAllProdutos(){
        return toDTOs(produtoBean.getAll());
    }

    @POST
    @Path("/")
    public Response createNewProduto(ProdutoEmbalagemRegraDTO produtoEmbalagemRegraDTO){
        Produto produto;
        try{
            produto = produtoBean.create(
                    produtoEmbalagemRegraDTO.getProduto().getNome(),
                    produtoEmbalagemRegraDTO.getProduto().getTipo(),
                    produtoEmbalagemRegraDTO.getProduto().getMarca(),
                    produtoEmbalagemRegraDTO.getProduto().getQuantidade(),
                    produtoEmbalagemRegraDTO.getProduto().getUnidadeMedida(),
                    produtoEmbalagemRegraDTO.getProduto().getPreco(),
                    produtoEmbalagemRegraDTO.getProduto().getDescricao()
            );

            embalagemProdutoBean.create(
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getTipo(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getFuncao(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getDataFabrico(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getMaterial(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getPeso(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getVolume(),
                    produto
            );

            for(RegraDTO regraDTO : produtoEmbalagemRegraDTO.getRegras()){
                regraBean.create(
                        regraDTO.getValor(),
                        regraDTO.getComparador(),
                        regraDTO.getMensagem(),
                        regraDTO.getTipo_sensor(),
                        produto
                );
            }

        }catch(Exception ex){
            System.err.println("Exceção " + ex + " na operação");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(produto == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(produto)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateProduto (ProdutoDTO produtoDTO){
        try {
            produtoBean.update(
                    produtoDTO.getId(),
                    produtoDTO.getNome(),
                    produtoDTO.getTipo(),
                    produtoDTO.getMarca(),
                    produtoDTO.getQuantidade(),
                    produtoDTO.getUnidadeMedida(),
                    produtoDTO.getPreco(),
                    produtoDTO.getDescricao()
            );
        }catch(Exception ex){
            System.err.println("Erro na atualização dos dados do produto: " + ex);
        }
        Produto produto = produtoBean.find(produtoDTO.getId());
        if(produto == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return
                Response.status(Response.Status.OK).entity(toDTO(produto)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduto (@PathParam("id") long id){
        try {
            produtoBean.delete(id);
            return Response.status(Response.Status.OK).build();
        }catch (Exception ex){
            System.err.println("Erro enquanto tentava apagar produto: " + ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
