package com.literalura.app.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.app.model.Autor;
import com.literalura.app.model.Idioma;
import com.literalura.app.model.Livro;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GutendexClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Livro> buscarLivros(String titulo) {
        List<Livro> livros = new ArrayList<>();
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        String resposta = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(resposta);
            JsonNode results = root.get("results");

            if (results.isArray()) {
                for (JsonNode node : results) {
                    String idioma = node.get("languages").get(0).asText();
                    String tituloLivro = node.get("title").asText();
                    int downloads = node.get("download_count").asInt();

                    JsonNode autorNode = node.get("authors").get(0);
                    String nomeAutor = autorNode.get("name").asText();
                    Integer nascimento = autorNode.get("birth_year").isNull() ? null : autorNode.get("birth_year").asInt();
                    Integer falecimento = autorNode.get("death_year").isNull() ? null : autorNode.get("death_year").asInt();

                    Autor autor = new Autor();
                    autor.setNome(nomeAutor);
                    autor.setAnoNascimento(nascimento);
                    autor.setAnoFalecimento(falecimento);

                    Livro livro = new Livro();
                    livro.setTitulo(tituloLivro);
                    livro.setIdioma(Idioma.valueOf(idioma.toUpperCase()));
                    livro.setNumeroDownloads(downloads);
                    livro.setAutor(autor);

                    livros.add(livro);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar resposta da API externa", e);
        }

        return livros;
    }
}
