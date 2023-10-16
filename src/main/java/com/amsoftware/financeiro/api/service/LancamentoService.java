package com.amsoftware.financeiro.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amsoftware.financeiro.api.model.Lancamento;
import com.amsoftware.financeiro.api.model.Pessoa;
import com.amsoftware.financeiro.api.repository.LancamentoRepository;
import com.amsoftware.financeiro.api.repository.PessoaRepository;
import com.amsoftware.financeiro.api.repository.dto.LancamentoDto;
import com.amsoftware.financeiro.api.repository.lancamento.projection.ResumoLancamento;
import com.amsoftware.financeiro.api.service.exception.NegocioException;

@Service
public class LancamentoService extends AbstractServiceBase {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getId()).orElse(null);
		
		if (pessoa == null || pessoa.isInativo()) {
			throw new NegocioException("Pessoa inexistente ou Inativa");
		}
		
		return this.lancamentoRepository.save(lancamento);
	}
	
	public Page<Lancamento> pesquisar(LancamentoDto lancamentoDto, Pageable pageable) {
		return this.lancamentoRepository.pesquisar(lancamentoDto, pageable);
	}

	public List<Lancamento> buscarTodos() {
		return this.lancamentoRepository.findAll();
	}

	public Lancamento buscarPorId(Long id) {
		Lancamento lancamento = this.lancamentoRepository.findById(id).orElse(null);

		if (lancamento == null) {
			logger.warn("Nenhum lançamento encontrado com este ID.");
			throw new EmptyResultDataAccessException(1);
		}

		return lancamento;
	}
	
	
	public void remover(Long codigo) {
		this.lancamentoRepository.deleteById(codigo);
	}

	public Page<ResumoLancamento> resumir(LancamentoDto lancamentoDto, Pageable pageable) {
		return this.lancamentoRepository.resumir(lancamentoDto, pageable);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		Optional<Pessoa> pessoa = null;
		if (lancamento.getPessoa().getId() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		}

		if (pessoa.isEmpty() || pessoa.get().isInativo()) {
			throw new NegocioException("Pessoa inexistente ou intativa");
		}
	}

	private Lancamento buscarLancamentoExistente(Long codigo) {
/* 		Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);
		if (lancamentoSalvo.isEmpty()) {
			throw new IllegalArgumentException();
		} */
		return lancamentoRepository.findById(codigo).orElseThrow(() -> new IllegalArgumentException());
	}	
	
}
