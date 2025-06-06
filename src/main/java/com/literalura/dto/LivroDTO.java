package com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {

    @JsonAlias({"title", "titulo"})
    private String titulo;

    @JsonAlias("authors")
    private List<AutorDTO> autores; // Usar AutorDTO aqui

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias({"download_count", "numeroDeDownloads"})
    private Integer numeroDeDownloads;

    public LivroDTO() {}

    public LivroDTO(String titulo, List<AutorDTO> autores, List<String> idiomas, Integer numeroDeDownloads) {
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.numeroDeDownloads = numeroDeDownloads;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public List<AutorDTO> getAutores() { return autores; }
    public List<String> getIdiomas() { return idiomas; }
    public Integer getNumeroDeDownloads() { return numeroDeDownloads; }

    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutores(List<AutorDTO> autores) { this.autores = autores; }
    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }
    public void setNumeroDeDownloads(Integer numeroDeDownloads) { this.numeroDeDownloads = numeroDeDownloads; }

    @Override
    public String toString() {
        return "LivroDTO{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idiomas=" + idiomas +
                ", numeroDeDownloads=" + numeroDeDownloads +
                '}';
    }
}