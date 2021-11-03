package br.leg.alrr.chaves.persistence;

import br.leg.alrr.chaves.model.Chave;
import br.leg.alrr.common.util.DAOException;
import br.leg.alrr.chaves.model.Itinerario;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Heliton Nascimento
 */
@Stateless
public class ItinerarioDAO {

    @PersistenceContext
    protected EntityManager em;

    public void salvar(Itinerario o) throws DAOException {
        try {
            em.persist(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao salvar itinerário.", e);
        }
    }

    public void atualizar(Itinerario o) throws DAOException {
        try {
            em.merge(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao atualizar itinerário.", e);
        }
    }

    public void remover(Itinerario o) throws DAOException {
        try {
            o = em.merge(o);
            em.remove(o);
        } catch (Exception e) {
            throw new DAOException("Erro ao remover itinerário.", e);
        }
    }

    public List listarTodos() throws DAOException {
        try {
            return em.createQuery("select o from Itinerario o order by o.chave.categoria.nome, o.chave.numero").getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }

    public List listarOsUltimosRegistros(int quantidade) throws DAOException {
        try {
            return em.createQuery("select o from Itinerario o order by o.id DESC")
                    .setMaxResults(quantidade)
                    .getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }

    public Itinerario buscarItinararioAtivoPorChave(Chave chave) throws DAOException {
        try {
            return (Itinerario) em.createQuery("select o from Itinerario o where o.chave.id =: idChave and o.status = true")
                    .setParameter("idChave", chave.getId())
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }
    public List pesquisarItinerarios(LocalDateTime dataInicio, LocalDateTime dataFim) throws DAOException {
        try {
            return em.createQuery("select o from Itinerario o where o.dataRetiradaDaChave >= :dataInicio and o.dataRetiradaDaChave <= :dataFim order by o.chave.categoria.nome, o.chave.numero")
                    .setParameter("dataInicio", dataInicio)
                    .setParameter("dataFim", dataFim)
                    .getResultList();
        } catch (Exception e) {
            throw new DAOException("Erro ao listar usuários.", e);
        }
    }

}
