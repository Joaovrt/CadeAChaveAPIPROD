package com.cadeachave.cadeachave.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cadeachave.cadeachave.dtos.ProfessorRecordDto;
import com.cadeachave.cadeachave.exceptions.ResourceConflictException;
import com.cadeachave.cadeachave.exceptions.ResourceNotFoundException;
import com.cadeachave.cadeachave.models.ProfessorModel;
import com.cadeachave.cadeachave.models.SalaModel;
import com.cadeachave.cadeachave.repositories.ProfessorRepository;
import com.cadeachave.cadeachave.repositories.SalaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    SalaRepository salaRepository;

    public ResponseEntity<ProfessorModel> findById(Long id){
        ProfessorModel professor = professorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhum professor encontrado com esse id: "+id));
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    public ResponseEntity<ProfessorModel> findByCpf(String cpf){
        ProfessorModel professor = professorRepository.findByCpf(cpf);
        if(professor==null)
            throw new ResourceNotFoundException("Nenhum professor encontrado com esse CPF:" + cpf);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    public ResponseEntity<List<ProfessorModel>> findByNome(String nome){
        List<ProfessorModel> professorList = professorRepository.findByNome(nome);
        if(professorList.isEmpty())
            throw new ResourceNotFoundException("Nenhum professor encontrado com esse nome: " + nome);
        return ResponseEntity.status(HttpStatus.OK).body(professorList);
    }

    public Page<ProfessorModel> findByCpfOrNomeContaining(String termo, Pageable pageable) {
        var professorList = professorRepository.findByCpfContainingIgnoreCaseOrNomeContainingIgnoreCase(termo, termo, pageable);
        if (professorList.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum professor encontrado com o CPF ou nome contendo: " + termo);
        }
        return professorList;
    }

    public Page<ProfessorModel> findAll(Pageable pageable){
        return professorRepository.findAll(pageable);
    }
    
    public ResponseEntity<ProfessorModel> create(ProfessorRecordDto professorDto) {
        try {
            ProfessorModel professor = new ProfessorModel();
            professor.setNome(professorDto.nome());
            professor.setCpf(professorDto.cpf());
            if (!professorDto.salas().isEmpty()) {
                List<SalaModel> salaList = new ArrayList<>();
                for (Long i : professorDto.salas()) {
                    SalaModel sala = salaRepository.findById(i)
                            .orElseThrow(() -> new RuntimeException("Sala não encontrada para o ID: " + i));
                    salaList.add(sala);
                }
                professor.setSalas(salaList);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(professorRepository.save(professor));
        } 
        catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Outro usuário já está cadastrado com esse CPF: " + professorDto.cpf());
        }
    }


    public ResponseEntity<ProfessorModel> update (ProfessorRecordDto professorDto, Long id){
        try {
            var entity = professorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhum professor encontrado com esse id."));
            entity.setNome(professorDto.nome());
            entity.setCpf(professorDto.cpf());
            if (!professorDto.salas().isEmpty()) {
                List<SalaModel> salaList = new ArrayList<>();
                for (Long i : professorDto.salas()) {
                    SalaModel sala = salaRepository.findById(i)
                            .orElseThrow(() -> new ResourceNotFoundException("Sala não encontrada para o ID: " + i));
                    salaList.add(sala);
                }
                entity.setSalas(salaList);
            }
            return ResponseEntity.status(HttpStatus.OK).body(professorRepository.save(entity));
        }
        catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("Outro usuário já está cadastrado com esse CPF: " + professorDto.cpf());
        }
    }

    public ResponseEntity<Object> delete (Long id){
        var entity = professorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhum professor encontrado com esse id."));
        professorRepository.delete(entity);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}