package com.amsoftware.financeiro.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amsoftware.financeiro.api.model.Lancamento;
import com.amsoftware.financeiro.api.repository.dto.LancamentoDto;
import com.amsoftware.financeiro.api.repository.lancamento.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> pesquisar(LancamentoDto lancamentoDto, Pageable pageable);

	Page<ResumoLancamento> resumir(LancamentoDto lancamentoDto, Pageable pageable);
	
}
