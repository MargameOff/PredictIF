package fr.insalyon.dasi.dasi.predictif.metier.service;

import fr.insalyon.dasi.dasi.predictif.dao.ClientDAO;
import fr.insalyon.dasi.dasi.predictif.dao.ConsultationDAO;
import fr.insalyon.dasi.dasi.predictif.dao.EmployeDAO;
import fr.insalyon.dasi.dasi.predictif.dao.MediumDAO;
import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Consultation;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceStats {

    private ClientDAO clientDAO;
    private ConsultationDAO consultationDAO;
    private MediumDAO mediumDAO;
    private EmployeDAO employeDAO;

    public ServiceStats() {
        clientDAO = new ClientDAO();
        consultationDAO = new ConsultationDAO();
        mediumDAO = new MediumDAO();
        employeDAO = new EmployeDAO();
    }

    public List<String> obtenirCoordonneesClients() {
        JpaUtil.creerContextePersistance();
        List<String> coordonnees = null;

        try {
            JpaUtil.ouvrirTransaction();
            List<Client> clients = clientDAO.findAll();
            coordonnees = clients.stream()
                                 .map(client -> client.getLatitude() + ", " + client.getLongitude())
                                 .collect(Collectors.toList());
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return coordonnees;
    }

    public Map<Medium, Long> obtenirConsultationsParMedium() {
        JpaUtil.creerContextePersistance();
        Map<Medium, Long> consultationsParMedium = new HashMap<>();

        try {
            JpaUtil.ouvrirTransaction();
            List<Medium> mediums = mediumDAO.findAll();
            for (Medium medium : mediums) {
                Long count = consultationDAO.countByMedium(medium);
                consultationsParMedium.put(medium, count);
            }
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consultationsParMedium;
    }

    public List<Medium> obtenirTop5Mediums() {
        JpaUtil.creerContextePersistance();
        List<Medium> top5Mediums = null;

        try {
            JpaUtil.ouvrirTransaction();
            top5Mediums = mediumDAO.findTop5ByConsultations();
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return top5Mediums;
    }

    public Map<Employe, Long> obtenirRepartitionClientsParEmploye() {
        JpaUtil.creerContextePersistance();
        Map<Employe, Long> repartitionParEmploye = new HashMap<>();

        try {
            JpaUtil.ouvrirTransaction();
            List<Employe> employes = employeDAO.findAll();
            for (Employe employe : employes) {
                Long count = consultationDAO.countClientsByEmploye(employe);
                repartitionParEmploye.put(employe, count);
            }
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return repartitionParEmploye;
    }
}