package br.leg.alrr.chaves.controller;

import br.leg.alrr.chaves.model.CategoriaChave;
import br.leg.alrr.common.util.FacesUtils;
import br.leg.alrr.chaves.model.Chave;
import br.leg.alrr.chaves.persistence.CategoriaChaveDAO;
import br.leg.alrr.chaves.persistence.ChaveDAO;
import java.io.Serializable;
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
public class ChaveMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ChaveDAO chaveDAO;
    @EJB
    private CategoriaChaveDAO categoriaChaveDAO;

    @Getter
    private List<Chave> chaves;
    @Getter
    private List<CategoriaChave> categorias;

    @Getter
    @Setter
    private Chave chave;
    @Getter
    @Setter
    private CategoriaChave categoriaChave;

    @Getter
    @Setter
    private boolean removerChave;

    //==========================================================================
    public String salvar() {
        try {
            if (chave.getId() != null) {
                chave.setCategoria(categoriaChave);
                chaveDAO.atualizar(chave);
                FacesUtils.addInfoMessageFlashScoped("Chave atualizada com sucesso!");
            } else {
                
                chave.setCategoria(categoriaChave);
                
                if (chaveDAO.verificarSeChaveJaExiste(chave)) {
                    FacesUtils.addWarnMessage("Já existe uma chave com esse número!");
                    return null;
                }else {
                    chaveDAO.salvar(chave);
                    FacesUtils.addInfoMessageFlashScoped("Chave salva com sucesso!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao salvar chave!");
        }
        return "chave.xhtml" + "?faces-redirect=true";
    }

    public String remover() {
        try {
            chaveDAO.remover(chave);
            FacesUtils.addInfoMessageFlashScoped("Chave removida com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao remover chave!");
        }
        return "chave.xhtml" + "?faces-redirect=true";
    }

    public String cancelar() {
        return "chave.xhtml" + "?faces-redirect=true";
    }
    //==========================================================================

    //==========================================================================
    @PostConstruct
    private void iniciar() {
        chaves = new ArrayList<>();
        chave = new Chave();
        chave.setStatus(true);
        removerChave = false;
        listarTodos();
        listarCategoriasChavesAtivas();
    }

    private void listarTodos() {
        try {
            chaves = chaveDAO.listarTodos();
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
    //==========================================================================
}
