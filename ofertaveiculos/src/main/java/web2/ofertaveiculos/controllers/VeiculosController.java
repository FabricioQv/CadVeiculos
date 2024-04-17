package web2.ofertaveiculos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import web2.ofertaveiculos.dto.VeiculosRecordDto;
import web2.ofertaveiculos.model.VeiculosModel;
import web2.ofertaveiculos.repositories.VeiculosRepository;

public class VeiculosController {

    VeiculosRepository veiculosRepository;

    @PostMapping("/veiculos")
    public ResponseEntity<VeiculosModel>salvarVeiculo(@RequestBody @Valid VeiculosRecordDto veiculosDto) {

        var VeiculosModel = new VeiculosModel();
        BeanUtils.copyProperties(veiculosDto, VeiculosModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculosRepository.save(VeiculosModel));

    }

    @GetMapping("/veiculos")
    public ResponseEntity<List<VeiculosModel>> listarVeiculos(){
        return ResponseEntity.status(HttpStatus.OK).body(veiculosRepository.findAll());

    }

    @GetMapping("/veiculos/{id}")
    public ResponseEntity<Object> listarUmVeiculo(@PathVariable(value="id") int id){
        Optional<VeiculosModel> veiculo = veiculosRepository.findById(id);
        if(veiculo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veiculo não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(veiculo.get());
    }

    @DeleteMapping("/veiculos/{id}")
    public ResponseEntity<Object> deletarVeiculo(@PathVariable(value="id") int id){
        Optional<VeiculosModel> veiculo = veiculosRepository.findById(id);
        if(veiculo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veiculo não encontrado");
        }
        veiculosRepository.delete(veiculo.get());
        return ResponseEntity.status(HttpStatus.OK).body("Veiculo deletado");
    }

    @PutMapping("/veiculos/{id}")
    public ResponseEntity<Object> editarVeiculo(@RequestBody @Valid VeiculosRecordDto veiculoDto, @PathVariable(value = "id") int id){
        Optional<VeiculosModel> veiculo = veiculosRepository.findById(id);
        if(veiculo.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veiculo não encontrado");
            var veiculoModel = veiculo.get();
            BeanUtils.copyProperties(veiculoDto, veiculoModel);
            return ResponseEntity.status(HttpStatus.OK).body(veiculosRepository.save(veiculoModel));
    }
}
