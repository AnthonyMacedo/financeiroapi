package com.amsoftware.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amsoftware.financeiro.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}
