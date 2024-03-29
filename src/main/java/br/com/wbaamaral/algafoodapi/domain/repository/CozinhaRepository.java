package br.com.wbaamaral.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.wbaamaral.algafoodapi.domain.model.Cozinha;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

	List<Cozinha> findTodasByNomeContaining(String nome);

	Optional<Cozinha> findByNome(String nome);

	boolean existsByNome(String nome);

}
