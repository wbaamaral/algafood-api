package br.com.wbaamaral.algafoodapi.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wbaamaral.algafoodapi.api.assembler.RestauranteInputDisassembler;
import br.com.wbaamaral.algafoodapi.api.assembler.RestauranteModelAssembler;
import br.com.wbaamaral.algafoodapi.api.exceptionhandler.ValidacaoException;
import br.com.wbaamaral.algafoodapi.api.model.RestauranteModel;
import br.com.wbaamaral.algafoodapi.api.model.input.RestauranteInput;
import br.com.wbaamaral.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.exception.NegocioException;
import br.com.wbaamaral.algafoodapi.domain.model.Restaurante;
import br.com.wbaamaral.algafoodapi.domain.repository.RestauranteRepository;
import br.com.wbaamaral.algafoodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

   @Autowired
   private RestauranteRepository restauranteRepository;

   @Autowired
   private CadastroRestauranteService cadastroRestaurante;

   @Autowired
   private SmartValidator validator;

   @Autowired
   private RestauranteModelAssembler restauranteModelAssembler;

   @Autowired
   private RestauranteInputDisassembler restauranteInputDisassembler;

   @GetMapping
   public List<RestauranteModel> listar() {

      return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
   }

   @GetMapping("/{restauranteId}")
   public RestauranteModel buscar(@PathVariable Long restauranteId) {

      var restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

      return restauranteModelAssembler.toModel(restaurante);
   }

   @PostMapping
   @ResponseStatus(code = HttpStatus.CREATED)
   @Transactional
   public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

      try {

         Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

         RestauranteModel restauranteModel = restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));

         restauranteRepository.flush();

         return restauranteModel;
      } catch (CozinhaNaoEncontradaException e) {
         throw new NegocioException(e.getLocalizedMessage());
      }
   }

   @PutMapping("/{restauranteId}")
   @Transactional
   public RestauranteModel atualizar(@PathVariable Long restauranteId,
         @RequestBody @Valid RestauranteInput restauranteInput) {

      try {
         Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

         restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

         return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));

      } catch (CozinhaNaoEncontradaException e) {
         throw new NegocioException(e.getMessage());
      }

   }

   @PatchMapping("/{restauranteId}")
   @Transactional
   public RestauranteModel atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
         HttpServletRequest request) {

      RestauranteInput restaurante;

      Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

      merge(campos, restauranteAtual, request);

      validarRestaurante(restauranteAtual, "restaurante");

      restaurante = restauranteInputDisassembler.toInputObInput(restauranteAtual);

      RestauranteModel restauranteModel = atualizar(restauranteId, restaurante);

      return restauranteModel;
   }

   private void validarRestaurante(Restaurante restaurante, String objectName) {

      BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

      validator.validate(restaurante, bindingResult);

      if (bindingResult.hasErrors()) {
         throw new ValidacaoException(bindingResult);
      }
   }

   private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

      ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

      try {
         ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
         objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

         Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

         dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
         });

         if (restauranteDestino.getNome().isBlank()) {
            throw new NegocioException("Campo nome não pode ser vazio ou nulo.");
         }

      } catch (IllegalArgumentException e) {
         Throwable rootCause = ExceptionUtils.getRootCause(e);
         throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
      }
   }
}
