package com.cadeachave.cadeachave.services;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Comparator;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cadeachave.cadeachave.dtos.HistoricoResponseRecordDto;
import com.cadeachave.cadeachave.dtos.ProfessorWithoutSalasRecordDto;
import com.cadeachave.cadeachave.exceptions.ResourceBadRequestException;
import com.cadeachave.cadeachave.exceptions.ResourceNotFoundException;
import com.cadeachave.cadeachave.models.HistoricoModel;
import com.cadeachave.cadeachave.models.ProfessorModel;
import com.cadeachave.cadeachave.models.SalaModel;
import com.cadeachave.cadeachave.repositories.HistoricoRepository;

@Service
public class HistoricoService {

    private Logger logger = Logger.getLogger(HistoricoService.class.getName());

    @Autowired
    HistoricoRepository historicoRepository;

    public ResponseEntity<HistoricoResponseRecordDto> findById(Long id){
        HistoricoModel historico = historicoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Nenhuma historico encontrado com o id: "+id));
        HistoricoResponseRecordDto historicoResponse = convertToHistoricoResponseRecordDto(historico);
        
        return ResponseEntity.status(HttpStatus.OK).body(historicoResponse);
    }

    public Page<HistoricoResponseRecordDto> findAll(Pageable pageable){
        var historicoPage = historicoRepository.findAll(pageable);
        List<HistoricoResponseRecordDto> historicoDtoList = new ArrayList<>();
        for (int i = 0; i < historicoPage.getContent().size(); i++) {
            HistoricoResponseRecordDto dto = convertToHistoricoResponseRecordDto(historicoPage.getContent().get(i));
            historicoDtoList.add(dto);
        }
        Page<HistoricoResponseRecordDto> historicoDtoPage = new PageImpl<>(historicoDtoList, historicoPage.getPageable(), historicoPage.getTotalElements());
        return historicoDtoPage;
    }
    
    public ResponseEntity<HistoricoResponseRecordDto> create (ProfessorModel professor, SalaModel sala, boolean abriu){
        HistoricoModel historico = new HistoricoModel();
        historico.setProfessor(professor);
        historico.setSala(sala);
        historico.setAbriu(abriu);
        // Obtém o timezone para Horário de Brasília (GMT-3)
        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");

        // Cria um objeto Calendar com o timezone de Horário de Brasília
        Calendar calendar = Calendar.getInstance(timeZone);

        // Obtém o timestamp atual no timezone de Horário de Brasília
        Timestamp horarioBrasilia = new Timestamp(calendar.getTimeInMillis());
        historico.setHorario(horarioBrasilia);
        var historicoDtoCreated = convertToHistoricoResponseRecordDto(historicoRepository.save(historico));
        return ResponseEntity.status(HttpStatus.OK).body(historicoDtoCreated);
    }

    public Page<HistoricoResponseRecordDto> buscarHistoricoComFiltro(String dataInicial, String dataFinal, Long professorId, Long salaId, Boolean abriu, Pageable pageable) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp dataHoraInicial = new Timestamp(dateFormat.parse(dataInicial).getTime());
            Timestamp dataHoraFinal = new Timestamp(dateFormat.parse(dataFinal).getTime());

            Page<HistoricoModel> historicoPage = historicoRepository.buscarHistoricoComFiltro(dataHoraInicial, dataHoraFinal, professorId, salaId, abriu, pageable);

            List<HistoricoResponseRecordDto> historicoDtoList = new ArrayList<>();
            for (HistoricoModel historicoModel : historicoPage.getContent()) {
                HistoricoResponseRecordDto dto = convertToHistoricoResponseRecordDto(historicoModel);
                historicoDtoList.add(dto);
            }

            return new PageImpl<>(historicoDtoList, historicoPage.getPageable(), historicoPage.getTotalElements());
        } catch (ParseException e) {
            throw new ResourceBadRequestException("Formato de data incorreto, insira (yyyy-MM-dd HH:mm:ss)");
        }
    }
    

    public boolean validaUltimoAAbrir(ProfessorModel professor, SalaModel sala){
        List<HistoricoModel> historicoList = historicoRepository.findBySalaAndAbriu(sala, true);
        if(!historicoList.isEmpty()){
            historicoList.sort(Comparator.comparing(HistoricoModel::getHorario).reversed());
            HistoricoModel historicoMaisRecente = historicoList.get(0);
            if(historicoMaisRecente.getProfessor()==professor)
                return true;
        }
        return false;
    }
    

    private HistoricoResponseRecordDto convertToHistoricoResponseRecordDto(HistoricoModel historico) {
        ProfessorModel professor = historico.getProfessor();
    
        // Formata o timestamp para exibição com o fuso horário de Brasília
        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(timeZone);
        String horarioFormatado = sdf.format(historico.getHorario());
    
        return new HistoricoResponseRecordDto(
                historico.getId(),
                new ProfessorWithoutSalasRecordDto(
                        professor.getId(),
                        professor.getNome(),
                        professor.getCpf()
                ),
                historico.getSala(),
                horarioFormatado, // Utiliza o horário formatado
                historico.isAbriu()
        );
    }
}