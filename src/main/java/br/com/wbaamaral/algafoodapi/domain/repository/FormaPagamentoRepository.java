package br.com.wbaamaral.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wbaamaral.algafoodapi.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
