package br.com.wbaamaral.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wbaamaral.algafoodapi.domain.model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
