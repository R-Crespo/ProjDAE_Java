package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EmbalagemProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.ProdutoBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.RegraBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Regra;

import java.util.ArrayList;
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
                produto.getPreco(),
                regrasToDTOs(produto.getRegras())
        );
    }

    private RegraDTO toDTO(Regra regra){
        return new RegraDTO(
                regra.getId(),
                regra.getValor(),
                regra.getComparador(),
                regra.getMensagem(),
                regra.getTipo_sensor(),
                regra.getProduto().getId()
        );
    }
    public List<RegraDTO> regrasToDTOs(List<Regra> regras){
        return regras.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ProdutoDTO> toDTOs(List<Produto> produtos){
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public  List<ProdutoDTO> getAllProdutos(){
        return toDTOs(produtoBean.getAll());
    }

    @GET
    @Path("{id}")
    public ProdutoDTO getProduto(@PathParam("id") long id) {
        return toDTO(produtoBean.find(id));
    }

    @POST
    @Path("/")
    public Response createNewProduto(ProdutoEmbalagemRegraDTO produtoEmbalagemRegraDTO){
        Produto produto = null;
        EmbalagemProduto embalagemProduto = null;
        List<Regra> regras = new ArrayList<Regra>();
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

            embalagemProduto = embalagemProdutoBean.create(
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getTipo(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getFuncao(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getDataFabrico(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getMaterial(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getPeso(),
                    produtoEmbalagemRegraDTO.getEmbalagemProduto().getVolume(),
                    produto
            );

            for(RegraDTO regraDTO : produtoEmbalagemRegraDTO.getRegras()){
                regras.add(regraBean.create(
                        regraDTO.getValor(),
                        regraDTO.getComparador(),
                        regraDTO.getMensagem(),
                        regraDTO.getTipo_sensor(),
                        produto.getId()
                ));
            }
        }catch(Exception ex){
            System.err.println("Exceção " + ex + " na operação");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(produto == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        for(Regra regra : regras){
            try {
                produtoBean.update(produto.getId(), produto.getNome(), produto.getTipo(), produto.getMarca(), produto.getQuantidade(), produto.getUnidadeMedida(), produto.getPreco(), produto.getDescricao(), regra.getId(), embalagemProduto.getId());
            }catch (Exception e){
                System.err.println("Exceção "+ e +" na operação");
            }
        }
        ProdutoDTO produtoDTO = toDTO(produto);
        produtoDTO.setRegras(regrasToDTOs(regras));
        return Response.status(Response.Status.CREATED).entity(produtoDTO).build();
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
                    produtoDTO.getDescricao(),
                    null,null
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
