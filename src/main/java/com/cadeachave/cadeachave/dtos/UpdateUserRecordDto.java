package com.cadeachave.cadeachave.dtos;

import com.cadeachave.cadeachave.models.UserRoleEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRecordDto(@NotBlank String login, String password, @NotNull UserRoleEnum role, Long professor_id, @NotNull boolean ativo) {


}
