package com.amsoftware.financeiro.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.amsoftware.financeiro.api.model.Categoria;
import com.amsoftware.financeiro.api.repository.CategoriaRepository;

@Service
public class CategoriaService extends AbstractServiceBase {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria salvar(Categoria categoria) {
		return this.categoriaRepository.save(categoria);
	}

	public Categoria buscarPorId(Long id) {
		Categoria categoriaSalva = this.categoriaRepository.findById(id).orElse(null);

		if (categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}
	
	public List<Categoria> buscarTodos() {
		return this.categoriaRepository.findAll();
	}

	public void remover(Long id) {
		Categoria categoria = this.buscarPorId(id);

		if (categoria != null) {
			this.categoriaRepository.delete(categoria);
		}

	}

}
