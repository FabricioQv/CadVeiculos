package web2.ofertaveiculos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="veiculos")
public class VeiculosModel {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id_veiculos;

    private String placa;

    private String cor;

    private String modelo;

    private String marca;

    private double valor;

    private String imagem;


    public String getImagem() {
        return this.imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @ManyToOne
    @JoinColumn(name = "categoriaID")
    private CategoriasModel categoriaID;


    public int getId_veiculos() {
        return this.id_veiculos;
    }

    public void setId_veiculos(int id_veiculos) {
        this.id_veiculos = id_veiculos;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public CategoriasModel getCategoriaID() {
        return this.categoriaID;
    }

    public void setCategoriaID(CategoriasModel categoriaID) {
        this.categoriaID = categoriaID;
    }


    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    


}
