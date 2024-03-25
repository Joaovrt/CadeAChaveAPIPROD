package com.cadeachave.cadeachave.dtos;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;

public record ProfessorRecordDto(@NotBlank String nome, @NotBlank @CPF String cpf, List<Long> salas) {

}
