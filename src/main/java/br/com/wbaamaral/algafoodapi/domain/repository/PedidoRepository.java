package br.com.wbaamaral.algafoodapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.wbaamaral.algafoodapi.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
}