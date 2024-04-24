package web2.ofertaveiculos.dto;

import jakarta.validation.constraints.NotBlank;

public record VeiculosRecordDto(@NotBlank String placa, @NotBlank String cor, @NotBlank String modelo, @NotBlank String marca, @NotBlank double valor, String imagem) {

}
