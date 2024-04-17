package web2.ofertaveiculos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import web2.ofertaveiculos.dto.CategoriasRecordDto;
import web2.ofertaveiculos.model.CategoriasModel;
import web2.ofertaveiculos.repositories.CategoriasRepository;

@RestController
public class CategoriasController {

    @Autowired
    CategoriasRepository categoriasRepository;

    @PostMapping("/categorias")
    public ResponseEntity<CategoriasModel>salvarCategoria(@RequestBody @Valid CategoriasRecordDto categoriasDto) {

        var categoriasModel = new CategoriasModel();
        BeanUtils.copyProperties(categoriasDto, categoriasModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriasRepository.save(categoriasModel));

    }

     @GetMapping("/categorias")
    public ResponseEntity<List<CategoriasModel>> listarCategorias(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriasRepository.findAll());

    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Object> listarUmaCategoria(@PathVariable(value="id") int id){
        Optional<CategoriasModel> categoria = categoriasRepository.findById(id);
        if(categoria.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoria.get());
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Object> deletarVeiculo(@PathVariable(value="id") int id){
        Optional<CategoriasModel> categoria = categoriasRepository.findById(id);
        if(categoria.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
        }
        categoriasRepository.delete(categoria.get());
        return ResponseEntity.status(HttpStatus.OK).body("Categoria deletada");
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Object> editarCategoria(@RequestBody @Valid CategoriasRecordDto categoriaDto, @PathVariable(value = "id") int id){
        Optional<CategoriasModel> categoria = categoriasRepository.findById(id);
        if(categoria.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada");
            var categoriaModel = categoria.get();
            BeanUtils.copyProperties(categoriaDto, categoriaModel);
            return ResponseEntity.status(HttpStatus.OK).body(categoriasRepository.save(categoriaModel));
    }


}
