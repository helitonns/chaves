package br.leg.alrr.chaves.model;

import br.leg.alrr.common.util.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Heliton
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Chave implements Serializable, BaseEntity{
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @EqualsAndHashCode.Include
   private Long id;
  
   @ManyToOne
   private CategoriaChave categoria;
   
   private int numero;
   private String sala;
    
}
