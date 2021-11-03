package br.leg.alrr.chaves.persistence;

import br.leg.alrr.common.util.DAOException;
import br.leg.alrr.chaves.model.CategoriaChave;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Heliton Nascimento
 */
@Stateless
public class CategoriaChaveDAO {

    @PersistenceContext
    protected EntityManager em;

    public void salvar(CategoriaChave o) throws DAOException {
        try {
            em.persist(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar categoria chave.", e);
        }
    }

    public void atualizar(CategoriaChave o) throws DAOException {
        try {
            em.merge(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar categoria chave.", e);
        }
    }

    public void remover(CategoriaChave o) throws DAOException {
        try {
            o = em.merge(o);
            em.remove(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao remover categoria chave.", e);
        }
    }

    public List listarTodos() throws DAOException {
        try {
            return em.createQuery("select o from CategoriaChave o order by o.nome").getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }
    
    public List listarTodasAtivas() throws DAOException {
        try {
            return em.createQuery("select o from CategoriaChave o where o.status = true order by o.nome").getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }

}
