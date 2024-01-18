package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private ClienteBean clienteBean;

    @EJB
    private OperadorBean operadorBean;

    @EJB
    private FornecedorBean fornecedorBean;

    @EJB
    private ProdutoBean produtoBean;

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");


    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException, MyEntityExistsException {
        fornecedorBean.create("Forn1", "123", "Coca-Cola", "coca_cola@sapo.pt");
        fornecedorBean.create("Forn2", "123", "Pepsi", "pepsi@sapo.pt");
        fornecedorBean.create("Forn3", "123", "Fanta", "fanta@sapo.pt");
        operadorBean.create("Roger10", "123", "Rogerio Sousa", "roger10@sapo.pt");
        operadorBean.create("Pinto12", "123", "Anibal Pinto", "pinto@sapo.pt");
        operadorBean.create("Mario12", "123", "Mario Miguel", "mario1@sapo.pt");
        clienteBean.create("Marco", "123", "Marco Miguel", "marco@sapo.pt");
        clienteBean.create("Ricardo", "123", "Ricardo Miguel", "ricardo@sapo.pt");
        clienteBean.create("Rui", "123", "Rui Miguel", "rui@sapo.pt");
        /*produtoBean.create(1,"Coca-cola","Refrigerante","Coca-cola", 1, "L", 1.79f,"","Forn1");
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
