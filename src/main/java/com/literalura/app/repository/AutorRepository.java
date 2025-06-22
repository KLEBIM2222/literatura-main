package com.literalura.app.repository;

import com.literalura.app.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    List<Autor> findByNacionalidadeIgnoreCase(String nacionalidade);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento >= :anoInicio AND (a.anoFalecimento IS NULL OR a.anoFalecimento <= :anoFim)")
    List<Autor> findAutoresVivosEntreAnos(@Param("anoInicio") int anoInicio, @Param("anoFim") int anoFim);
}
