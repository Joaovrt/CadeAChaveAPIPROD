package com.cadeachave.cadeachave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cadeachave.cadeachave.dtos.HistoricoResponseRecordDto;
import com.cadeachave.cadeachave.services.HistoricoService;

@RestController
@RequestMapping("/api/historico")
public class HistoricoController {
    
    @Autowired
    HistoricoService historicoService;

     @GetMapping()
    public ResponseEntity<Page<HistoricoResponseRecordDto>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"horario"));
        return ResponseEntity.ok(historicoService.findAll(pageable));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<HistoricoResponseRecordDto> findById(@PathVariable(value = "id") Long id){
        return historicoService.findById(id);
    }

    @GetMapping("/filtro/{dataInicial}/{dataFinal}")
    public ResponseEntity<Page<HistoricoResponseRecordDto>> buscarHistoricoPorIntervaloDeDatas(
            @PathVariable(value = "dataInicial") String dataInicial,
            @PathVariable(value = "dataFinal") String dataFinal,
            @RequestParam(value = "professorId", required = false) Long professorId,
            @RequestParam(value = "salaId", required = false) Long salaId,
            @RequestParam(value = "abriu", required = false) Boolean abriu,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "15") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"horario"));
            return ResponseEntity.ok(historicoService.buscarHistoricoComFiltro(dataInicial, dataFinal, professorId, salaId, abriu, pageable));
}
}