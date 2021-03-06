package br.leg.alrr.chaves.controller;

import br.leg.alrr.chaves.model.CategoriaChave;
import br.leg.alrr.common.util.FacesUtils;
import br.leg.alrr.chaves.model.Chave;
import br.leg.alrr.chaves.model.Itinerario;
import br.leg.alrr.chaves.persistence.CategoriaChaveDAO;
import br.leg.alrr.chaves.persistence.ChaveDAO;
import br.leg.alrr.chaves.persistence.ItinerarioDAO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
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
    private CategoriaChaveDAO categoriaChaveDAO;
    @EJB
    private ChaveDAO chaveDAO;
    @EJB
    private ItinerarioDAO itinerarioDAO;

    @Getter
    private List<Chave> chaves;
    @Getter
    private List<CategoriaChave> categorias;

    @Getter
    @Setter
    private Chave chave;
    @Getter
    @Setter
    private Itinerario itinerario;
    @Getter
    @Setter
    private Long idCategoria;
    @Getter
    @Setter
    private boolean exibirChaveSelecionada;

    //==========================================================================
    public String voltar() {
        return "index.xhtml" + "?faces-redirect=true";
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

    public void pesquisarChavesDaCategoria(ValueChangeEvent event) {
        try {
            idCategoria = Long.parseLong(event.getNewValue().toString());
            listarChaves();
        } catch (NumberFormatException e) {
            FacesUtils.addInfoMessage(e.getMessage());
        }
    }

    public void selecionarChave() {
        buscarItinerarioPorChave();
        exibirChaveSelecionada = true;
    }

    //==========================================================================
    //==========================================================================
    @PostConstruct
    private void iniciar() {
        listarCategoriasChavesAtivas();
        chaves = new ArrayList<>();
        itinerario = new Itinerario();
        exibirChaveSelecionada = false;
    }

    private void listarChaves() {
        try {
            chaves = chaveDAO.listarChavesAtivasPorCategoria(new CategoriaChave(idCategoria));
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao listar chaves!");
        }
    }

    private void listarCategoriasChavesAtivas() {
        try {
            categorias = categoriaChaveDAO.listarTodasAtivas();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao listar categorias chaves!");
        }
    }

    private void buscarItinerarioPorChave() {
        try {
            itinerario = itinerarioDAO.buscarItinararioAtivoPorChave(chave);
            if (itinerario == null) {
                itinerario = new Itinerario();
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
        }
    }
    //==========================================================================
}
