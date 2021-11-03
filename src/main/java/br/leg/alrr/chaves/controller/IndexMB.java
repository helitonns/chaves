package br.leg.alrr.chaves.controller;

import br.leg.alrr.common.util.FacesUtils;
import br.leg.alrr.chaves.model.Chave;
import br.leg.alrr.chaves.model.Itinerario;
import br.leg.alrr.chaves.persistence.ChaveDAO;
import br.leg.alrr.chaves.persistence.ItinerarioDAO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class IndexMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ChaveDAO chaveDAO;

    @EJB
    private ItinerarioDAO itinerarioDAO;

    @Getter
    private List<Chave> chaves;

    @Getter
    private List<Itinerario> ultimosRegistros;

    @Getter
    @Setter
    private Chave chave;

    @Getter
    @Setter
    private Itinerario itinerario;

    //==========================================================================
    public String voltar() {
        return "index.xhtml" + "?faces-redirect=true";
    }

    public String irParaItinerario() {
        try {
            FacesUtils.setBean("chave", chave);

            itinerario = itinerarioDAO.buscarItinararioAtivoPorChave(chave);

            if (itinerario == null) {
                itinerario = new Itinerario();
            }

            FacesUtils.setBean("itinerario", itinerario);
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
        }
        return "itinerario.xhtml" + "?faces-redirect=true";
    }

    public String movimentarChave() {
        try {
            if (itinerario.getId() != null) {
                itinerario.setStatus(false);
                itinerario.setDataDevolucaoDaChave(LocalDateTime.now());
                itinerarioDAO.atualizar(itinerario);
                FacesUtils.addInfoMessageFlashScoped("Chave devolvida com sucesso!");
            } else {
                itinerario.setChave(chave);
                itinerario.setDataRetiradaDaChave(LocalDateTime.now());
                itinerario.setStatus(true);
                itinerarioDAO.salvar(itinerario);
                FacesUtils.addInfoMessageFlashScoped("Chave retirada com sucesso!");
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao movimentar chave!");
        }
        return "index.xhtml" + "?faces-redirect=true";
    }
    //==========================================================================

    //==========================================================================
    @PostConstruct
    private void iniciar() {
        chaves = new ArrayList<>();

        if (!FacesUtils.getURL().contains("itinerario")) {
            listarChaves();
            listarUltimosRegistros();
        }

        try {
            Chave c = (Chave) FacesUtils.getBean("chave");
            if (c != null) {
                chave = c;
                FacesUtils.removeBean("chave");
            } else {
                chave = new Chave();
            }

            Itinerario i = (Itinerario) FacesUtils.getBean("itinerario");
            if (i != null) {
                itinerario = i;
                FacesUtils.removeBean("itinerario");
            } else {
                itinerario = new Itinerario();
            }
        } catch (Exception e) {
        }
    }

    private void listarChaves() {
        try {
            chaves = chaveDAO.listarChavesDeCatagoriasAtivas();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao listar chaves!");
        }
    }

    private void listarUltimosRegistros() {
        try {
            ultimosRegistros = itinerarioDAO.listarOsUltimosRegistros(10);
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao listar últimos registros!");
        }
    }
    //==========================================================================
}
