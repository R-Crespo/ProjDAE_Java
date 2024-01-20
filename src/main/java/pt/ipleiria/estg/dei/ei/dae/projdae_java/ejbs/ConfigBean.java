package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.ProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EncomendaProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private EncomendaProdutoBean encomendaProdutoBean;
    @EJB
    private ClienteBean clienteBean;

    @EJB
    private OperadorBean operadorBean;

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private EncomendaBean encomendaBean;
    @EJB

    private EmbalagemTransporteBean embalagemTransporteBean;

    @EJB
    private EmbalagemProdutoBean embalagemProdutoBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");


    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        administratorBean.create("Admin1", "123", "Admin1", "Admin1@sapo.pt");
        administratorBean.create("Admin2", "123", "Admin2", "Admin2@sapo.pt");
        administratorBean.create("Admin3", "123", "Admin3", "Admin3@sapo.pt");
        operadorBean.create("Roger10", "123", "Rogerio Sousa", "roger10@sapo.pt");
        operadorBean.create("Pinto12", "123", "Anibal Pinto", "pinto@sapo.pt");
        operadorBean.create("Mario12", "123", "Mario Miguel", "mario1@sapo.pt");
        clienteBean.create("Marco", "123", "Marco Miguel", "marco@sapo.pt");
        clienteBean.create("Ricardo", "123", "Ricardo Miguel", "ricardo@sapo.pt");
        clienteBean.create("Rui", "123", "Rui Miguel", "rui@sapo.pt");

        embalagemTransporteBean.create("Transporte","Tranportar",new Date(), "Cartao", 1, 500);

        /*embalagemTransporteBean.create("tipo1", "guardar", new Date(), "cartão", 10, 10);
        encomendaBean.create("Marco", "Pousos",  "Leiria", 1, new ArrayList<>());
        encomendaBean.create("Marco", "pousos",  "leiria", 1, new ArrayList<>());
        encomendaBean.create("Rui", "leiria",  "lisboa", 1, new ArrayList<>());
        encomendaBean.create("Ricardo", "leiria",  "lisboa", 1, new ArrayList<>());
        /*produtoBean.create(1,"Coca-cola","Refrigerante","Coca-cola", 1, "L", 1.79f,"","Forn1");
        encomendaBean.update(1,"Roger10","Marco","pousos", "Entregue",new Date(),"leiria", 1);
        embalagemProdutoBean.create("caixa","carregar",new Date(),"madeira", 10,20,null);
        Produto produto = produtoBean.create("Coca-cola","Refrigerante","Coca-cola", 1, "L", 1.79f,"");
        embalagemProdutoBean.create("caixa","carregar",new Date(),"madeira", 10,20, produto);
        produtoBean.create(1,"Coca-cola","Refrigerante","Coca-cola", 1, "L", 1.79f,"","Forn1");
        produtoBean.create(2,"Coca-cola","Refrigerante","Coca-cola", 330, "mL", 1.00f,"","Forn1");
        produtoBean.create(3,"Coca-cola","Refrigerante","Coca-cola", 500, "mL", 1.19f,"","Forn1");
        produtoBean.create(4,"Pepsi","Refrigerante","Pepsi", 1, "L", 1.79f,"","Forn2");
        produtoBean.create(5,"Pepsi","Refrigerante","Pepsi", 330, "mL", 1.00f,"","Forn2");
        produtoBean.create(6,"Pepsi","Refrigerante","Pepsi", 500, "mL", 1.19f,"","Forn2");
        produtoBean.create(4,"Fanta","Refrigerante","Fanta", 1, "L", 1.79f,"","Forn3");
        produtoBean.create(5,"Fanta","Refrigerante","Fanta", 330, "mL", 1.00f,"","Forn3");
        produtoBean.create(6,"Fanta","Refrigerante","Fanta", 500, "mL", 1.19f,"","Forn3");*/
    }
}
