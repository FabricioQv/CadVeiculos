package web2.ofertaveiculos.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriasRecordDto(@NotBlank String nome) {

}
