package br.leg.alrr.chaves.model;

import br.leg.alrr.common.util.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 *
 * @author Heliton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Itinerario implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    private String responsavelRetiradaChave;
    private String responsavelDevolucaoChave;
    
    private LocalDateTime dataRetiradaDaChave;
    private LocalDateTime dataDevolucaoDaChave;
    
    private boolean status;
    
    @ManyToOne
    private Chave chave;
    
}
