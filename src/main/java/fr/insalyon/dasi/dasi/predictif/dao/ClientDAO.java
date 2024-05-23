package fr.insalyon.dasi.dasi.predictif.dao;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author qsaillard
 */
public class ClientDAO {
    
    public void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public Client update(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client findById(Long clientId) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, clientId);
    }
    
    public List<Client> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }
    
    public Client findByMail(String mail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.mail = :mail", Client.class);
        query.setParameter("mail", mail);
        List<Client> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
    public void remove(Client client) {
       JpaUtil.obtenirContextePersistance().remove(client);
    }
}
