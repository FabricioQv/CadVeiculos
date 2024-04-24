package web2.ofertaveiculos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web2.ofertaveiculos.model.CategoriasModel;

@Repository
public interface CategoriasRepository extends JpaRepository<CategoriasModel, Integer> {

    List<CategoriasModel> findCategoriasByNomeLike(String nome);

}
