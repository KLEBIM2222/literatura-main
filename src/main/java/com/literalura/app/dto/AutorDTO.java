package com.literalura.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {

    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    private String nacionalidade;

}
