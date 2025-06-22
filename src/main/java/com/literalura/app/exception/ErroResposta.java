package com.literalura.app.exception;

import java.time.LocalDateTime;

public class ErroResposta {
    private String titulo;
    private String detalhe;
    private LocalDateTime timestamp;

    public ErroResposta(String titulo, String detalhe, LocalDateTime timestamp) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.timestamp = timestamp;
    }

    // Getters e setters (pode usar Lombok para simplificar, se quiser)
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
