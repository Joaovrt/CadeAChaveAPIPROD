package com.cadeachave.cadeachave.dtos;

import com.cadeachave.cadeachave.models.SalaModel;

public record HistoricoResponseRecordDto(Long id, ProfessorWithoutSalasRecordDto professor, SalaModel sala, String horario, boolean abriu) {

}
