package com.cadeachave.cadeachave.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadeachave.cadeachave.models.SalaModel;

@Repository
public interface SalaRepository extends JpaRepository<SalaModel,Long>{
    SalaModel findByNome(String nome);
    Page<SalaModel> findByNomeContaining(String nome, Pageable pageable);
    Page<SalaModel> findByAberta(boolean aberta, Pageable pageable);
    Page<SalaModel> findByNomeContainingAndAberta(String nome, boolean aberta, Pageable pageable);
}
