package com.cadeachave.cadeachave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cadeachave.cadeachave.dtos.HistoricoResponseRecordDto;
import com.cadeachave.cadeachave.dtos.SalaRecordDto;
import com.cadeachave.cadeachave.models.SalaModel;
import com.cadeachave.cadeachave.services.SalaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sala")
public class SalaController {

    @Autowired
    SalaService salaService;

    @PostMapping()
    public ResponseEntity<SalaModel> create(@RequestBody @Valid SalaRecordDto salaRecordDto){
        return salaService.create(salaRecordDto);
    }

    @GetMapping()
    public ResponseEntity<Page<SalaModel>> findAll(
         @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
        return ResponseEntity.ok(salaService.findAll(pageable));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<SalaModel> findById(@PathVariable(value = "id") Long id){
        return salaService.findById(id);
    }

    @GetMapping(value="/nome/{nome}")
    public ResponseEntity<SalaModel> findByNome(@PathVariable(value = "nome") String nome){
        return salaService.findByNome(nome);
    }

    @GetMapping(value="/nomeCom/{termo}")
    public ResponseEntity<Page<SalaModel>> findByNomeContaining(@PathVariable(value = "termo") String termo,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
        return ResponseEntity.ok(salaService.findByNomeContaining(termo, pageable));
    }

    @GetMapping(value="/aberta/{aberta}")
    public ResponseEntity<Page<SalaModel>> findByAberta(@PathVariable(value = "aberta") boolean aberta,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
        return ResponseEntity.ok(salaService.findByAberta(aberta,pageable));
    }

    @GetMapping(value="/nomeComEAberta/{nome}/{aberta}")
    public ResponseEntity<Page<SalaModel>> findByNomeContainingAndAberta(@PathVariable(value = "nome") String nome, @PathVariable(value = "aberta") boolean aberta,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
        return ResponseEntity.ok(salaService.findByNomeContainingAndAberta(nome,aberta,pageable));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<SalaModel> update(@PathVariable(value = "id") Long id, @RequestBody @Valid SalaRecordDto salaRecordDto){
        return salaService.update(salaRecordDto, id);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id){
        return salaService.delete(id);
    }

    @GetMapping("/abrir/{nome}/{cpf}")
    public ResponseEntity<HistoricoResponseRecordDto> abrir(@PathVariable(value = "cpf") String cpf, @PathVariable(value = "nome") String nome) {
        return salaService.Abrir(cpf, nome);
    }

    @GetMapping("/fechar/{nome}/{cpf}")
    public ResponseEntity<HistoricoResponseRecordDto> fechar(@PathVariable(value = "cpf") String cpf, @PathVariable(value = "nome") String nome) {
        return salaService.Fechar(cpf, nome);
    }
}
