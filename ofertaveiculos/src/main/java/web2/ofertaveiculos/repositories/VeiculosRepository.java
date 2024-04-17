package web2.ofertaveiculos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web2.ofertaveiculos.model.VeiculosModel;

@Repository
public interface VeiculosRepository extends JpaRepository <VeiculosModel, Integer> {

}
