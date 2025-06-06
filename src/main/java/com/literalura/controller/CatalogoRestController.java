package com.literalura.controller;

import com.literalura.dto.AutorDTO;
import com.literalura.dto.LivroDTO;
import com.literalura.model.Autor;
import com.literalura.model.Livro;
import com.literalura.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CatalogoRestController {

    private final LivroService livroService;

    public CatalogoRestController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/livros/buscar")
    public ResponseEntity<LivroDTO> buscarLivroPorTitulo(@RequestParam String titulo) {
        Livro livro = livroService.buscarESalvarLivroPorTitulo(titulo);
        if (livro != null) {
            LivroDTO livroDTO = new LivroDTO(
                    livro.getTitulo(),
                    livro.getAutores().stream()
                            .map(a -> new AutorDTO(a.getNome(), a.getAnoDeNascimento(), a.getAnoDeFalecimento()))
                            .collect(Collectors.toList()),
                    livro.getIdiomas(),
                    livro.getNumeroDeDownloads()
            );
            return ResponseEntity.ok(livroDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/livros")
    public List<LivroDTO> listarTodosLivros() {
        return livroService.listarLivros();
    }

    @GetMapping("/autores")
    public List<AutorDTO> listarTodosAutores() {
        return livroService.listarAutores();
    }

    @GetMapping("/livros/idioma/{idioma}")
    public List<LivroDTO> listarLivrosPorIdioma(@PathVariable String idioma) {
        return livroService.buscarPorIdioma(idioma);
    }

    @GetMapping("/livros/top10")
    public List<LivroDTO> listarTop10LivrosMaisBaixados() {
        return livroService.listarTop10LivrosMaisBaixados();
    }

    @GetMapping("/autores/buscar")
    public ResponseEntity<AutorDTO> buscarAutorPorNome(@RequestParam String nome) {
        Optional<Autor> autor = livroService.buscarAutorPorNome(nome);
        return autor.map(a -> ResponseEntity.ok(new AutorDTO(a.getNome(), a.getAnoDeNascimento(), a.getAnoDeFalecimento())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/autores/nascidos-antes/{ano}")
    public List<AutorDTO> listarAutoresNascidosAntesDe(@PathVariable Integer ano) {
        return livroService.listarAutoresNascidosAntesDe(ano);
    }

    @GetMapping("/autores/vivos-em/{ano}")
    public List<AutorDTO> listarAutoresVivosEmAno(@PathVariable Integer ano) {
        return livroService.listarAutoresVivosEmAno(ano);
    }

    //...
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticasDownloads() {
        DoubleSummaryStatistics stats = livroService.getEstatisticasDeDownloads();
        if (stats != null) {
            return ResponseEntity.ok(Map.of(
                    "mediaDownloads", stats.getAverage(),
                    "totalLivrosComDownloads", stats.getCount(),
                    "maiorDownload", (int) stats.getMax(),
                    "menorDownload", (int) stats.getMin(),
                    "somaDownloads", (int) stats.getSum()
            ));
        }
        return ResponseEntity.notFound().build();
    }
}