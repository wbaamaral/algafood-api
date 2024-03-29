package br.com.wbaamaral.algafoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.wbaamaral.algafoodapi.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

   @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
   List<Pedido> findAll();

   Optional<Pedido> findByCodigo(String codigoPedido);
}