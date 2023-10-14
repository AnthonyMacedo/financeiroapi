package com.amsoftware.financeiro.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amsoftware.financeiro.api.model.Usuario;

public interface UsuarioRepository  extends  JpaRepository<Usuario, Long>   {

	public Optional<Usuario> findByEmail(String email);
}
