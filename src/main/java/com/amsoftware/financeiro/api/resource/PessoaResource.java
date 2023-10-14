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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amsoftware.financeiro.api.event.RecursoCriadoEvent;
import com.amsoftware.financeiro.api.model.Pessoa;
import com.amsoftware.financeiro.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	static Logger logger = LoggerFactory.getLogger(CategoriaResource.class);

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher; // publicado de eventos da aplicação

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
	public ResponseEntity<?> buscarTodos() {
		List<Pessoa> listaPessoas = this.pessoaService.buscarTodos();
		return !listaPessoas.isEmpty() ? ResponseEntity.ok(listaPessoas) : ResponseEntity.noContent().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = this.pessoaService.salvar(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@PutMapping("/{id}") //atualização do objeto inteiro
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = this.pessoaService.atualizar(id, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{id}/ativo") //atualização parcial
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public void atualizarPropriedadeAtivo(@PathVariable("id") Long id, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(id, ativo);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
	public ResponseEntity<Pessoa> buscarPorId(@PathVariable("id") Long id) {
		Pessoa pessoa = this.pessoaService.buscarPorId(id);

		if (pessoa == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(pessoa);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public void remover(@PathVariable("id") Long id) {

		this.pessoaService.remover(id);
	}

}
