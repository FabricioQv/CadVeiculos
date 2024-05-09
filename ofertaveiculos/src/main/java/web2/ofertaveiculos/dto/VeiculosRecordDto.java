package web2.ofertaveiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import web2.ofertaveiculos.model.CategoriasModel;

public record VeiculosRecordDto(@NotBlank String placa, @NotBlank String cor, @NotBlank String modelo, @NotBlank String marca, @NotNull double valor, String imagem, Integer categoria_id) {

    public Integer getCategoria_id() {
        return this.categoria_id;
    }
    
}
