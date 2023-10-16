package com.amsoftware.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amsoftware.financeiro.api.model.Pessoa;

public interface PessoaRepository extends  JpaRepository<Pessoa, Long> {
	
//	 public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
	 
}
