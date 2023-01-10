package br.com.wbaamaral.algafoodapi.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wbaamaral.algafoodapi.domain.model.FotoProduto;
import br.com.wbaamaral.algafoodapi.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {

   @Autowired
   private ProdutoRepository produtoRepository;

   @Transactional
   public FotoProduto salvar(FotoProduto foto) {

      Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getIdRestaurante(),
            foto.getIdProduto());

      if (fotoExistente.isPresent()) {
         // excluir foto
         produtoRepository.delete(fotoExistente.get());
      }

      return produtoRepository.save(foto);
   }

}
