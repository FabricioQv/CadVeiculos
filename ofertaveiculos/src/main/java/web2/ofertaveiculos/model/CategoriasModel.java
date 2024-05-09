package web2.ofertaveiculos.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class CategoriasModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoriaId;
    private String nome;


    public int getCategoriaId() {
        return this.categoriaId;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    

}
