package br.com.wbaamaral.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wbaamaral.algafoodapi.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
