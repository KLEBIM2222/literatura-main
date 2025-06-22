package com.literalura.app.dto;

import com.literalura.app.model.Idioma;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class LivroDTO {

    private Long id;
    private String titulo;
    private String nomeAutor;
    private Idioma idioma;
    private Integer numeroDownloads;

}
