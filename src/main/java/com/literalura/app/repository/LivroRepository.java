package com.literalura.app.repository;

import com.literalura.app.model.Livro;
import com.literalura.app.model.Idioma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    List<Livro> findByIdioma(Idioma idioma);

    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}
