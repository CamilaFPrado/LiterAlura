package com.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDTO {

    @JsonAlias({"name", "nome"})
    private String nome;

    @JsonAlias("birth_year")
    private Integer anoDeNascimento;

    @JsonAlias("death_year")
    private Integer anoDeFalecimento;

    // Construtor vazio (necessário para Jackson)
    public AutorDTO() {}

    // Construtor para facilitar a conversão
    public AutorDTO(String nome, Integer anoDeNascimento, Integer anoDeFalecimento) {
        this.nome = nome;
        this.anoDeNascimento = anoDeNascimento;
        this.anoDeFalecimento = anoDeFalecimento;
    }

    // Getters
    public String getNome() { return nome; }
    public Integer getAnoDeNascimento() { return anoDeNascimento; }
    public Integer getAnoDeFalecimento() { return anoDeFalecimento; }

    // Setters     public void setNome(String nome) { this.nome = nome; }
    public void setAnoDeNascimento(Integer anoDeNascimento) { this.anoDeNascimento = anoDeNascimento; }
    public void setAnoDeFalecimento(Integer anoDeFalecimento) { this.anoDeFalecimento = anoDeFalecimento; }

    @Override
    public String toString() {
        return "AutorDTO{" +
                "nome='" + nome + '\'' +
                ", anoDeNascimento=" + anoDeNascimento +
                ", anoDeFalecimento=" + anoDeFalecimento +
                '}';
    }
}