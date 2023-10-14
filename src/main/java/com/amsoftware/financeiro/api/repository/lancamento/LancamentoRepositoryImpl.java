package com.amsoftware.financeiro.api.repository.lancamento;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.amsoftware.financeiro.api.model.Categoria_;
import com.amsoftware.financeiro.api.model.Lancamento;
import com.amsoftware.financeiro.api.model.Lancamento_;
import com.amsoftware.financeiro.api.model.Pessoa_;
import com.amsoftware.financeiro.api.repository.dto.LancamentoDto;
import com.amsoftware.financeiro.api.repository.lancamento.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> pesquisar(LancamentoDto lancamentoDto, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoDto, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Lancamento> query = this.manager.createQuery(criteriaQuery);
		this.adicionarRestricoesDePaginacao(pageable, query);

		return new PageImpl<>(query.getResultList(), pageable, this.total(lancamentoDto));
	}

	private Predicate[] criarRestricoes(LancamentoDto lancamentoDto, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (lancamentoDto != null) {

			if (lancamentoDto.getDescricao() != null && isNotEmpty(lancamentoDto.getDescricao())) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Lancamento_.descricao)),
						"%" + lancamentoDto.getDescricao().toUpperCase() + "%"));
			}

			if (lancamentoDto.getDataVencimento() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento),
						lancamentoDto.getDataVencimento()));
			}

			if (lancamentoDto.getDataPagamento() != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataPagamento),
						lancamentoDto.getDataPagamento()));
			}

		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long total(LancamentoDto lancamentoDto) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoDto, criteriaBuilder, root);

		criteriaQuery.where(predicates);

		criteriaQuery.select(criteriaBuilder.count(root));
		return manager.createQuery(criteriaQuery).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(Pageable pageable, TypedQuery<Lancamento> query) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoDto lancamentoDto, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class,
				root.get(Lancamento_.id),
				root.get(Lancamento_.descricao),
				root.get(Lancamento_.dataVencimento),
				root.get(Lancamento_.dataPagamento),
				root.get(Lancamento_.valor),
				root.get(Lancamento_.tipoLancamento),
				root.get(Lancamento_.categoria).get(Categoria_.descricao),
				root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(lancamentoDto, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoDto));
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
}
