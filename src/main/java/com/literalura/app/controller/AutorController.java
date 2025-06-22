package com.literalura.app.controller;

import com.literalura.app.dto.AutorDTO;
import com.literalura.app.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        List<AutorDTO> dtos = autorService.listarAutores();
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AutorDTO>> buscarPorNome(@RequestParam String nome) {
        List<AutorDTO> dtos = autorService.buscarPorNome(nome);
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/vivos")
    public ResponseEntity<List<AutorDTO>> autoresVivosEntre(@RequestParam int anoInicio, @RequestParam int anoFim) {
        List<AutorDTO> dtos = autorService.autoresVivosEntre(anoInicio, anoFim);
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/nacionalidade/{nacionalidade}")
    public ResponseEntity<List<AutorDTO>> listarPorNacionalidade(@PathVariable String nacionalidade) {
        List<AutorDTO> dtos = autorService.listarPorNacionalidade(nacionalidade);
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }
}
