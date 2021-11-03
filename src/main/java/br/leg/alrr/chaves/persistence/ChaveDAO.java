package br.leg.alrr.chaves.persistence;

import br.leg.alrr.common.util.DAOException;
import br.leg.alrr.chaves.model.Chave;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Heliton Nascimento
 */
@Stateless
public class ChaveDAO {

    @PersistenceContext
    protected EntityManager em;

    public void salvar(Chave o) throws DAOException {
        try {
            em.persist(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar chave.", e);
        }
    }

    public void atualizar(Chave o) throws DAOException {
        try {
            em.merge(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar chave.", e);
        }
    }

    public void remover(Chave o) throws DAOException {
        try {
            o = em.merge(o);
            em.remove(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao remover chave.", e);
        }
    }

    public List listarTodos() throws DAOException {
        try {
            return em.createQuery("select o from Chave o order by o.categoria.nome, o.numero").getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }
    
    public List listarChavesDeCatagoriasAtivas() throws DAOException {
        try {
            return em.createQuery("select o from Chave o where o.categoria.status = true order by o.categoria.nome, o.numero")
                    .getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }
    
    public boolean verificarSeChaveJaExiste(Chave chave) throws DAOException {
        try {
            List l = em.createQuery("select o from Chave o where o.categoria.id = :idCategoria and o.numero = :numeroChave")
                    .setParameter("idCategoria", chave.getCategoria().getId())
                    .setParameter("numeroChave", chave.getNumero())
                    .getResultList();
            return l.size() > 0;
        } catch (Exception e) {
            throw new DAOException("Erro ao verificar se chave já existe.", e);
        }
    }

    
}
