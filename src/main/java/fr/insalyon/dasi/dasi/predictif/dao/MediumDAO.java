/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.dao;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Astrologue;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Cartomancien;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Spirite;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author qsaillard
 */
public class MediumDAO {
    
    public void create(Medium medium) {
       JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    public Medium update(Medium medium) {
        return JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
    public List<Medium> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Medium> query = em.createQuery("SELECT me FROM Medium me", Medium.class);
        return query.getResultList();
    }

    public Medium findById(Long idMedium) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, idMedium);
    }
    
    public List<Medium> findTop5ByConsultations() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Medium> query = em.createQuery("SELECT m FROM Medium m ORDER BY (SELECT COUNT(c) FROM Consultation c WHERE c.medium = m)", Medium.class);
        query.setMaxResults(5);
        return query.getResultList();
    }
}
