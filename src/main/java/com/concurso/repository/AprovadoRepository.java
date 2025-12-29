package com.concurso.repository;

import com.concurso.model.Aprovado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprovadoRepository extends JpaRepository<Aprovado, Long> {
    
    List<Aprovado> findAllByOrderByDataCadastroDesc();
    
    boolean existsByEmail(String email);
}
