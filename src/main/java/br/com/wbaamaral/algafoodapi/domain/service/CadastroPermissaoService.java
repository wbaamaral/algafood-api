package br.com.wbaamaral.algafoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wbaamaral.algafoodapi.domain.exception.PermissaoNaoEncontradaException;
import br.com.wbaamaral.algafoodapi.domain.model.Permissao;
import br.com.wbaamaral.algafoodapi.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}