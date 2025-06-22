package com.literalura.app.service;

import com.literalura.app.dto.AutorDTO;
import com.literalura.app.model.Autor;
import com.literalura.app.repository.AutorRepository;
import com.literalura.app.service.AutorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;

    public AutorServiceImpl(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public List<AutorDTO> listarAutores() {
        return autorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorDTO> buscarPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorDTO> autoresVivosEntre(int anoInicio, int anoFim) {
        return autorRepository.findAutoresVivosEntreAnos(anoInicio, anoFim).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutorDTO> listarPorNacionalidade(String nacionalidade) {
        return autorRepository.findByNacionalidadeIgnoreCase(nacionalidade).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(
                autor.getId(),
                autor.getNome(),
                autor.getAnoNascimento(),
                autor.getAnoFalecimento(),
                autor.getNacionalidade()
        );
    }
}