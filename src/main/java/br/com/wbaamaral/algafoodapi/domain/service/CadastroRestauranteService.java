package br.com.wbaamaral.algafoodapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.RestauranteNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.algafoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

   @Autowired
   private RestauranteRepository restauranteRepository;

   @Autowired
   private CadastroCozinhaService cadastroCozinha;

   @Autowired
   private CadastroCidadeService cadastroCidade;

   public Restaurante salvar(Restaurante restaurante) {

      restaurante.setCozinha(cadastroCozinha.buscarOuFalhar(restaurante.getCozinha().getId()));

      restaurante.getEndereco().setCidade(cadastroCidade.buscarOuFalhar(restaurante.getEndereco().getCidade().getId()));

      return restauranteRepository.save(restaurante);
   }

   @Transactional
   public void ativar(Long restauranteId) {

      Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
      restauranteAtual.ativar();

   }

   @Transactional
   public void inativar(Long restauranteId) {

      Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
      restauranteAtual.inativar();

   }

   public Restaurante buscarOuFalhar(Long restauranteId) {

      return restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
   }
}
