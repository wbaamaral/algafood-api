package br.com.wbaamaral.algafoodapi.domain.repository;

import java.util.Optional;

import br.com.wbaamaral.algafoodapi.domain.model.Usuario;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}