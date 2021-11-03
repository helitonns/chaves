package br.leg.alrr.common.business;

import lombok.Data;

/**
 *
 * @author Heliton
 */
@Data
public class Endereco {
    
    private String cep;
    private String bairro;
    private String logradouro;
    private String numero;
   
}
