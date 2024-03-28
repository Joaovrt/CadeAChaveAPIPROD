package com.cadeachave.cadeachave.dtos;

import com.cadeachave.cadeachave.models.UserRoleEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRecordDto(@NotBlank String login, @NotBlank String password, @NotNull UserRoleEnum role, Long professor_id) {

}
