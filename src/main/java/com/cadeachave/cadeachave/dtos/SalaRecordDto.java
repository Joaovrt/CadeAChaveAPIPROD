package com.cadeachave.cadeachave.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRecordDto(@NotBlank String nome, @NotNull boolean aberta, @NotNull boolean ativo) {

}