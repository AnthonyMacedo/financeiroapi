package com.amsoftware.financeiro.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amsoftware.financeiro.api.model.Lancamento;
import com.amsoftware.financeiro.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends  JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
