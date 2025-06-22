package com.literalura.app.controller;

import com.literalura.app.dto.LivroDTO;
import com.literalura.app.model.Idioma;
import com.literalura.app.model.Livro;
import com.literalura.app.service.LivroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
@Validated
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@Valid @RequestBody Livro livro) {
        Livro novoLivro = livroService.criarLivro(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(novoLivro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        Livro atualizado = livroService.atualizarLivro(id, livro);
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> listarLivros(
            @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("titulo").ascending());
        Page<Livro> livros = livroService.listarLivros(titulo, pageable);
        Page<LivroDTO> dtos = livros.map(this::toDTO);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(toDTO(livro));
    }

    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<List<LivroDTO>> listarPorIdioma(@PathVariable String idioma) {
        Idioma idiomaEnum;
        try {
            idiomaEnum = Idioma.valueOf(idioma.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        List<Livro> livros = livroService.listarPorIdioma(idioma);
        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<LivroDTO> dtos = livros.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/mais-baixados")
    public ResponseEntity<List<LivroDTO>> listarMaisBaixados() {
        List<Livro> livros = livroService.top10MaisBaixados();
        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<LivroDTO> dtos = livros.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private LivroDTO toDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor().getNome(),
                livro.getIdioma(),
                livro.getNumeroDownloads()
        );
    }
}
