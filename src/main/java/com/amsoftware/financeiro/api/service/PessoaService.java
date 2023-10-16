package com.amsoftware.financeiro.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.amsoftware.financeiro.api.model.Pessoa;
import com.amsoftware.financeiro.api.repository.PessoaRepository;

@Service
public class PessoaService extends AbstractServiceBase {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa salvar(Pessoa pessoa) {
		return this.pessoaRepository.save(pessoa);
	}

	public Pessoa buscarPorId(Long id) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElse(null);

		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}

	public void remover(Long id) {
		this.pessoaRepository.deleteById(id);
	}

	public List<Pessoa> buscarTodos() {
		return this.pessoaRepository.findAll();
	}

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa pessoaSalva = this.buscarPorId(id);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return this.pessoaRepository.save(pessoaSalva);
	}

	public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa pessoaSalva = this.buscarPorId(id);

		pessoaSalva.setAtivo(ativo);
		this.pessoaRepository.save(pessoaSalva);
	}

//	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable) {
//		return pessoaRepository.findByNomeContaining(nome, pageable);
//	}
}
