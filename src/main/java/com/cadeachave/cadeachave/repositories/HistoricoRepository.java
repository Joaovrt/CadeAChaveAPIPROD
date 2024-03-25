package com.cadeachave.cadeachave.repositories;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cadeachave.cadeachave.models.HistoricoModel;
import com.cadeachave.cadeachave.models.SalaModel;


@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoModel, Long>{
    List<HistoricoModel> findBySalaAndAbriu(SalaModel sala, boolean abriu);
    @Query("SELECT h FROM HistoricoModel h " +
            "WHERE h.horario BETWEEN :dataInicial AND :dataFinal " +
            "AND (:professorId IS NULL OR h.professor.id = :professorId) " +
            "AND (:salaId IS NULL OR h.sala.id = :salaId) " +
            "AND (:abriu IS NULL OR h.abriu = :abriu)")
    Page<HistoricoModel> buscarHistoricoComFiltro(
            Timestamp dataInicial,
            Timestamp dataFinal,
            Long professorId,
            Long salaId,
            Boolean abriu,
            Pageable pageable
    );
}