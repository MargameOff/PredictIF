package fr.insalyon.dasi.dasi.predictif.dao;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO pour la gestion des entités Employe.
 */
public class EmployeDAO {
    
    public void create(Employe employe) {
        JpaUtil.obtenirContextePersistance().persist(employe);
    }
    
    public Employe update(Employe employe) {
        return JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    public Employe findById(Long empId) {
        return JpaUtil.obtenirContextePersistance().find(Employe.class, empId);
    }

    public List<Employe> findAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e", Employe.class);
        return query.getResultList();
    }
    
    public List<Employe> findAllOrderedByCons() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employe> query = em.createQuery("SELECT e FROM Employe e JOIN Consultation c ON c.employe", Employe.class);
        return query.getResultList();
    }

    public void remove(Employe employe) {
        JpaUtil.obtenirContextePersistance().remove(employe);
    }
}
