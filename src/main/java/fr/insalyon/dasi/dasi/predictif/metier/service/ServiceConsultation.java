/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.metier.service;

import fr.insalyon.dasi.dasi.predictif.dao.ClientDAO;
import fr.insalyon.dasi.dasi.predictif.dao.EmployeDAO;
import fr.insalyon.dasi.dasi.predictif.dao.ConsultationDAO;
import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.dasi.predictif.dao.MediumDAO;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Consultation;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import java.util.Date;
import java.util.List;

/**
 *
 * @author qsaillard
 */
public class ServiceConsultation {
    private ConsultationDAO consultationDAO;
    private MediumDAO mediumDAO;
    private ClientDAO clientDAO;
    private EmployeDAO employeDAO;
    public ServiceConsultation() {
        consultationDAO = new ConsultationDAO();
        mediumDAO = new MediumDAO();
        clientDAO = new ClientDAO();
        employeDAO = new EmployeDAO();
    }
    
    public void creerConsultation(Long idMedium, Long idClient) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();

            // Récupérer le médium par son ID
            Medium medium = mediumDAO.findById(idMedium);
            if (medium == null) {
                throw new IllegalArgumentException("Medium avec ID " + idMedium + " non trouvé.");
            }

            // Récupérer le client par son ID
            Client client = clientDAO.findById(idClient);
            if (client == null) {
                throw new IllegalArgumentException("Client avec ID " + idClient + " non trouvé.");
            }

            // Créer une nouvelle consultation
            Consultation consultation = new Consultation(new Date(), medium, client);

            
            // Persistez la consultation
            consultationDAO.create(consultation);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

}
