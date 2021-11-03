 package br.leg.alrr.chaves.controller;

import br.leg.alrr.chaves.model.Itinerario;
import br.leg.alrr.chaves.persistence.ItinerarioDAO;
import br.leg.alrr.common.util.FacesUtils;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Heliton
 */
@Named
@ViewScoped
public class RelatorioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ItinerarioDAO itinerarioDAO;
    
    @Getter
    private List<Itinerario> registrosItinerarios;
    
    @Getter @Setter
    private LocalDateTime dataInicio;
    
    @Getter @Setter
    private LocalDateTime dataFim;



    //==========================================================================
    public void pesquisarItinerarios(){
        try {
            dataInicio.withHour(0);
            dataInicio.withMinute(0);
            dataInicio.withSecond(0);
            
            dataFim.withHour(23);
            dataFim.withMinute(59);
            dataFim.withSecond(59);
            
            registrosItinerarios = itinerarioDAO.pesquisarItinerarios(dataInicio, dataFim);
            
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao pesquisar registros!");
        }
    }

    public String cancelar() {
        return "relatorio.xhtml" + "?faces-redirect=true";
    }
    //==========================================================================

    //==========================================================================
    @PostConstruct
    private void iniciar() {
        dataInicio = LocalDateTime.now();
        dataFim = LocalDateTime.now().plusDays(7);
    }

    //==========================================================================
}
