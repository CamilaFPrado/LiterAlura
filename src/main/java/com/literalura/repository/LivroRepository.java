package com.literalura.repository;

import com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdiomasIgnoreCase(String idiomas);

    Optional<Livro> findByTituloIgnoreCase(String titulo);

    List<Livro> findTop10ByOrderByNumeroDeDownloadsDesc();
}