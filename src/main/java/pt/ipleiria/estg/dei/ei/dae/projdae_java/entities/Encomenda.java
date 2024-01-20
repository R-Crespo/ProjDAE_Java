package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "encomendas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "Select e From Encomenda e Order By e.dataEntrega"
        ),
        @NamedQuery(
                name = "getAllEncomendasNaoAtribuidas",
                query = "Select e From Encomenda e WHERE e.operador = null Order By e.dataEntrega"
        ),
        @NamedQuery(
                name = "getEncomendasOperador",
                query = "Select e From Encomenda e WHERE e.operador.username LIKE :operadorUsername Order By e.dataEntrega"
        ),
        @NamedQuery(
                name = "getEncomendasOperadorEntreges",
                query = "Select e From Encomenda e WHERE e.operador.username LIKE :operadorUsername AND UPPER(e.estado) LIKE 'ENTREGE' ORDER By e.dataEntrega"
        ),
        @NamedQuery(
                name = "getEncomendasCliente",
                query = "Select e From Encomenda e WHERE e.cliente.username LIKE :clienteUsername Order By e.dataEntrega"
        )
}
)
public class Encomenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "operador_username")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "cliente_username")
    @NotNull
    private Cliente cliente;
    @NotNull
    private String morada;
    @NotNull
    private String estado;
    private Date dataEntrega;
    @NotNull
    private String armazem;
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EncomendaProduto> encomendaProdutos;

    @ManyToOne
    @JoinColumn(name = "embalagemTransporte_id")
    @NotNull
    private EmbalagemTransporte embalagemTransporte;

    @OneToMany(mappedBy = "encomenda" ,cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Sensor> sensores;

    @Version
    private int version;

    public Encomenda() {
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.sensores = new ArrayList<Sensor>();
    }

    public Encomenda(Cliente cliente, String morada, String estado, String armazem, EmbalagemTransporte embalagemTransporte) {
        this.cliente = cliente;
        this.morada = morada;
        this.estado = estado;
        this.armazem = armazem;
        this.dataEntrega = null;
        this.embalagemTransporte = embalagemTransporte;
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.sensores = new ArrayList<Sensor>();
        this.operador = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getArmazem() {
        return armazem;
    }

    public void setArmazem(String armazem) {
        this.armazem = armazem;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<EncomendaProduto> getEncomendaProdutos() {
        return new ArrayList<>(encomendaProdutos);
    }

    public void setProdutos(List<EncomendaProduto> encomendaProdutos) {
        this.encomendaProdutos = encomendaProdutos;
    }


    public void addEncomendaProduto(EncomendaProduto encomendaProduto){
        if(encomendaProduto == null || encomendaProdutos.contains(encomendaProduto)){
            return;
        }

        encomendaProdutos.add(encomendaProduto);
    }

    public void removeEncomendaProduto(EncomendaProduto encomendaProduto){
        if(encomendaProduto == null || !(encomendaProdutos.contains(encomendaProduto))){
            return;
        }

        encomendaProdutos.remove(encomendaProduto);
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    public void removeSensor(Sensor sensor){
        if(sensor == null || !sensores.contains(sensor)) {
            return;
        }
        sensores.remove(sensor);
    }

    public void addSensor(Sensor sensor){
        if(sensor == null || sensores.contains(sensor)) {
            return;
        }
        sensores.remove(sensor);
    }

    public EmbalagemTransporte getEmbalagemTransporte() {
        return embalagemTransporte;
    }

    public void setEmbalagemTransporte(EmbalagemTransporte embalagemTransporte) {
        this.embalagemTransporte = embalagemTransporte;
    }
}
