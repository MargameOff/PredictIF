/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.dao;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Consultation;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author qsaillard
 */
public class ConsultationDAO {
    public void create(Consultation consult) {
       JpaUtil.obtenirContextePersistance().persist(consult);
    }
    
       public Consultation update(Consultation cons) {
        return JpaUtil.obtenirContextePersistance().merge(cons);
    }
    
    public Consultation findById(Long consId) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, consId);
    }
    
    public List<Consultation> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM CONSULTATION c", Consultation.class);
        return query.getResultList();
    }
    
    public List<Consultation> findByClient(Client client) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c WHERE c.client = :client", Consultation.class);
        query.setParameter("client", client);
        return query.getResultList();
    }
    
        public Long countByMedium(Medium medium) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Consultation c WHERE c.medium = :medium", Long.class);
        query.setParameter("medium", medium);
        return query.getSingleResult();
    }

    public Long countClientsByEmploye(Employe employe) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(DISTINCT c.client) FROM Consultation c WHERE c.employe = :employe", Long.class);
        query.setParameter("employe", employe);
        return query.getSingleResult();
    }
}
