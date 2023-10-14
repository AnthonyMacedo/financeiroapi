package com.amsoftware.financeiro.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amsoftware.financeiro.api.event.RecursoCriadoEvent;
import com.amsoftware.financeiro.api.model.Categoria;
import com.amsoftware.financeiro.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	static Logger logger = LoggerFactory.getLogger(CategoriaResource.class);

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISA_CATEGORIA') and hasAuthority('SCOPE_read')" )
	public ResponseEntity<?> buscarTodos() {
		List<Categoria> categorias = this.categoriaService.buscarTodos();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
	}

	@PostMapping // @ResponseStatus(value = HttpStatus.CREATED) opção se for void
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = this.categoriaService.salvar(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and hasAuthority('SCOPE_read')")
	public ResponseEntity<Categoria> buscarPeloId(@PathVariable("id") Long id) {
		Categoria categoria = this.categoriaService.buscarPorId(id);

		if (categoria == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(categoria);
		}
	}

}
