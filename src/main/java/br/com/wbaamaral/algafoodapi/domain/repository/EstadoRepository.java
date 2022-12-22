package br.com.wbaamaral.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wbaamaral.algafoodapi.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
