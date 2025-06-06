package com.literalura.service;

import com.literalura.dto.AutorDTO;
import com.literalura.dto.LivroDTO;
import com.literalura.dto.RespostaApiDTO;
import com.literalura.model.Autor;
import com.literalura.model.Livro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoApi consumoApi;
    private final JacksonConverter converter;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, ConsumoApi consumoApi, JacksonConverter converter) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.consumoApi = consumoApi;
        this.converter = converter;
    }

    private AutorDTO converterAutorParaDTO(Autor autor) {
        return new AutorDTO(autor.getNome(), autor.getAnoDeNascimento(), autor.getAnoDeFalecimento());
    }

    private LivroDTO converterLivroParaDTO(Livro livro) {
        List<AutorDTO> autoresDTO = livro.getAutores() != null ? // CORREÇÃO AQUI: Usando getAutores()
                livro.getAutores().stream() // CORREÇÃO AQUI: Usando getAutores()
                        .map(this::converterAutorParaDTO)
                        .collect(Collectors.toList()) :
                null;
        return new LivroDTO(
                livro.getTitulo(),
                autoresDTO,
                livro.getIdiomas(),
                livro.getNumeroDeDownloads()
        );
    }


    @Transactional
    public Livro buscarESalvarLivroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
        String json = consumoApi.obterDados(url);
        RespostaApiDTO resposta = converter.obterDados(json, RespostaApiDTO.class);

        if (resposta != null && !resposta.getResults().isEmpty()) {
            LivroDTO livroDTO = resposta.getResults().get(0);

            Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCase(livroDTO.getTitulo());

            if (livroExistente.isPresent()) {
                System.out.println("Livro já registrado: " + livroExistente.get().getTitulo());
                return livroExistente.get();
            } else {
                Livro novoLivro = new Livro(
                        livroDTO.getTitulo(),
                        livroDTO.getAutores().stream()
                                .map(dto -> autorRepository.findByNomeIgnoreCase(dto.getNome())
                                        .orElseGet(() -> autorRepository.save(new Autor(dto.getNome(), dto.getAnoDeNascimento(), dto.getAnoDeFalecimento()))))
                                .collect(Collectors.toList()),
                        livroDTO.getIdiomas(),
                        livroDTO.getNumeroDeDownloads()
                );
                return livroRepository.save(novoLivro);
            }
        }
        return null;
    }

    @Transactional
    public List<LivroDTO> listarLivros() {
        return livroRepository.findAll().stream()
                .map(this::converterLivroParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AutorDTO> listarAutores() {
        return autorRepository.findAll().stream()
                .map(this::converterAutorParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LivroDTO> buscarPorIdioma(String idioma) {
        return livroRepository.findByIdiomasIgnoreCase(idioma).stream()
                .map(this::converterLivroParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LivroDTO> listarTop10LivrosMaisBaixados() {
        return livroRepository.findTop10ByOrderByNumeroDeDownloadsDesc().stream()
                .map(this::converterLivroParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<Autor> buscarAutorPorNome(String nome) {
        return autorRepository.findByNomeIgnoreCase(nome);
    }

    @Transactional
    public List<AutorDTO> listarAutoresNascidosAntesDe(Integer ano) {
        return autorRepository.findByAnoDeNascimentoLessThanEqual(ano).stream()
                .map(this::converterAutorParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AutorDTO> listarAutoresVivosEmAno(Integer ano) {
        return autorRepository.findAutoresVivosEmAno(ano).stream()
                .map(this::converterAutorParaDTO)
                .collect(Collectors.toList());
    }

    public DoubleSummaryStatistics getEstatisticasDeDownloads() {
        return livroRepository.findAll().stream()
                .filter(l -> l.getNumeroDeDownloads() != null) // CORREÇÃO AQUI: Usando getNumeroDeDownloads()
                .collect(Collectors.summarizingDouble(Livro::getNumeroDeDownloads)); // CORREÇÃO AQUI: Usando getNumeroDeDownloads()
    }

    @Transactional
    public void salvarLivros(RespostaApiDTO respostaApiDTO) {
        if (respostaApiDTO != null && respostaApiDTO.getResults() != null) {
            respostaApiDTO.getResults().forEach(livroDTO -> {
                Livro livro = new Livro(
                        livroDTO.getTitulo(),
                        livroDTO.getAutores().stream()
                                .map(dto -> autorRepository.findByNomeIgnoreCase(dto.getNome())
                                        .orElseGet(() -> autorRepository.save(new Autor(dto.getNome(), dto.getAnoDeNascimento(), dto.getAnoDeFalecimento()))))
                                .collect(Collectors.toList()),
                        livroDTO.getIdiomas(),
                        livroDTO.getNumeroDeDownloads()
                );
                livroRepository.save(livro);
            });
        }
    }
}