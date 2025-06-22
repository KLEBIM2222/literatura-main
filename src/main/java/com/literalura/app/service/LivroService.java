package com.literalura.app.service;

import com.literalura.app.exception.ResourceNotFoundException;
import com.literalura.app.model.Autor;
import com.literalura.app.model.Idioma;
import com.literalura.app.model.Livro;
import com.literalura.app.repository.AutorRepository;
import com.literalura.app.repository.LivroRepository;
import com.literalura.app.util.GutendexClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GutendexClient gutendexClient;

    public LivroService(LivroRepository livroRepository,
                        AutorRepository autorRepository,
                        GutendexClient gutendexClient) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexClient = gutendexClient;
    }

    // Método novo para paginação e busca por título
    public Page<Livro> listarLivros(String titulo, Pageable pageable) {
        if (titulo == null || titulo.isEmpty()) {
            return livroRepository.findAll(pageable);
        } else {
            return livroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        }
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        List<Livro> encontrados = livroRepository.findAll().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();

        if (!encontrados.isEmpty()) {
            return encontrados;
        }

        List<Livro> daApi = gutendexClient.buscarLivros(titulo);
        List<Livro> salvos = new ArrayList<>();

        for (Livro livro : daApi) {
            Autor autor = autorRepository.findByNome(livro.getAutor().getNome())
                    .orElseGet(() -> autorRepository.save(livro.getAutor()));

            boolean jaExiste = livroRepository.findAll().stream()
                    .anyMatch(l -> l.getTitulo().equalsIgnoreCase(livro.getTitulo()) &&
                            l.getAutor().getNome().equalsIgnoreCase(autor.getNome()));

            if (!jaExiste) {
                livro.setAutor(autor);
                livroRepository.save(livro);
                salvos.add(livro);
            }
        }
        return salvos;
    }

    public List<Livro> listarPorIdioma(String idiomaStr) {
        Idioma idioma;
        try {
            idioma = Idioma.valueOf(idiomaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Idioma inválido: " + idiomaStr);
        }
        return livroRepository.findByIdioma(idioma);
    }

    public List<Livro> top10MaisBaixados() {
        return livroRepository.findTop10ByOrderByNumeroDownloadsDesc();
    }

    public Livro criarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro atualizarLivro(Long id, Livro livroAtualizado) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setIdioma(livroAtualizado.getIdioma());
        livro.setNumeroDownloads(livroAtualizado.getNumeroDownloads());

        return livroRepository.save(livro);
    }

    public void deletarLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
        livroRepository.delete(livro);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
    }
}
