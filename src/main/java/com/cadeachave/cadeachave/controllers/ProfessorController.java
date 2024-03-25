package com.cadeachave.cadeachave.controllers;

import java.util.List;

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

import com.cadeachave.cadeachave.dtos.ProfessorRecordDto;
import com.cadeachave.cadeachave.models.ProfessorModel;
import com.cadeachave.cadeachave.services.ProfessorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @PostMapping()
    public ResponseEntity<ProfessorModel> create(@RequestBody @Valid ProfessorRecordDto professorRecordDto){
        return professorService.create(professorRecordDto);
    }

    @GetMapping()
    public ResponseEntity<Page<ProfessorModel>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
        return ResponseEntity.ok(professorService.findAll(pageable));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ProfessorModel> findById(@PathVariable(value = "id") Long id){
        return professorService.findById(id);
    }

    @GetMapping(value="/cpf/{cpf}")
    public ResponseEntity<ProfessorModel> findByCpf(@PathVariable(value = "cpf") String cpf){
        return professorService.findByCpf(cpf);
    }

    @GetMapping(value="/nome/{nome}")
    public ResponseEntity<List<ProfessorModel>> findByNome(@PathVariable(value = "nome") String nome){
        return professorService.findByNome(nome);
    }

     @GetMapping(value="/cpfOuNome/{termo}")
    public ResponseEntity<Page<ProfessorModel>> findByCpfOrNomeContaining(@PathVariable(value = "termo") String termo,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction) {
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"nome"));
            return ResponseEntity.ok(professorService.findByCpfOrNomeContaining(termo, pageable));
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ProfessorModel> update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProfessorRecordDto professorRecordDto){
        return professorService.update(professorRecordDto, id);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id){
        return professorService.delete(id);
    }
}
