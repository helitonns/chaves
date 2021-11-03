package br.leg.alrr.chaves.controller;

import br.leg.alrr.chaves.model.CategoriaChave;
import br.leg.alrr.chaves.persistence.CategoriaChaveDAO;
import br.leg.alrr.common.util.FacesUtils;
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
public class CategoriaChaveMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CategoriaChaveDAO categoriaChaveDAO;

    @Getter
    private List<CategoriaChave> categorias;

    @Getter
    @Setter
    private CategoriaChave categoriaChave;

    @Getter
    @Setter
    private boolean removerCategoriaChave;

    //==========================================================================
    public String salvar() {
        try {
            if (categoriaChave.getId() != null) {
                categoriaChaveDAO.atualizar(categoriaChave);
                FacesUtils.addInfoMessageFlashScoped("CategoriaChave atualizada com sucesso!");
            } else {
                categoriaChaveDAO.salvar(categoriaChave);
                FacesUtils.addInfoMessageFlashScoped("CategoriaChave salva com sucesso!");
            }
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao salvar categoriaChave!");
        }
        return "categoria-chave.xhtml" + "?faces-redirect=true";
    }

    public String remover() {
        try {
            categoriaChaveDAO.remover(categoriaChave);
            FacesUtils.addInfoMessageFlashScoped("CategoriaChave removida com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao remover categoriaChave!");
        }
        return "categoria-chave.xhtml" + "?faces-redirect=true";
    }

    public String cancelar() {
        return "categoria-chave.xhtml" + "?faces-redirect=true";
    }
    //==========================================================================
    
    //==========================================================================
    @PostConstruct
    private void iniciar() {
        categorias = new ArrayList<>();
        categoriaChave = new CategoriaChave();
        removerCategoriaChave = false;
        listarTodos();
    }
    
    private void listarTodos() {
        try {
            categorias = categoriaChaveDAO.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            FacesUtils.addErrorMessageFlashScoped("Erro ao listar categorias!");
        }
    }
    //==========================================================================

}
