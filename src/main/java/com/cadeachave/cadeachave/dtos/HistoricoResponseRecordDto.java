package com.cadeachave.cadeachave.dtos;

import java.sql.Timestamp;

import com.cadeachave.cadeachave.models.SalaModel;

public record HistoricoResponseRecordDto(Long id, ProfessorWithoutSalasRecordDto professor, SalaModel sala, Timestamp horario, boolean abriu) {

}
