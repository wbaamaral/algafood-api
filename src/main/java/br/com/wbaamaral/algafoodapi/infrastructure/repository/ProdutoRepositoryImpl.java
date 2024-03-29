package br.com.wbaamaral.algafoodapi.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

   @PersistenceContext
   private EntityManager manager;

   @Transactional
   @Override
   public FotoProduto save(FotoProduto foto) {

      return manager.merge(foto);
   }

   @Transactional
   @Override
   public void delete(FotoProduto foto) {

      manager.remove(foto);
      
   }

  

}
