package com.cadeachave.cadeachave.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadeachave.cadeachave.models.ProfessorModel;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long>{
    ProfessorModel findByCpf(String cpf);
    List<ProfessorModel> findByNome(String nome);
    Page<ProfessorModel> findByCpfContainingIgnoreCaseOrNomeContainingIgnoreCase(String cpf, String nome, Pageable pageable);
}
