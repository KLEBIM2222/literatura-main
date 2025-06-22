package com.literalura.app.service;

import com.literalura.app.dto.AutorDTO;

import java.util.List;

public interface AutorService {

    List<AutorDTO> listarAutores();

    List<AutorDTO> buscarPorNome(String nome);

    List<AutorDTO> autoresVivosEntre(int anoInicio, int anoFim);

    List<AutorDTO> listarPorNacionalidade(String nacionalidade);
}
