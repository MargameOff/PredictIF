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
import util.Message;

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

    public long creerConsultation(Long idMedium, Long idClient) {
        JpaUtil.creerContextePersistance();
        Long idConsult = null;
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

            // Trouver un employe qui est disponible
            Employe employe = employeDAO.findAvailableEmployeWithLeastConsultations(medium.getGenre());
            if (employe == null) {
                throw new IllegalArgumentException("Aucun employé disponible.");
            }
            consultation.setEmploye(employe);

            // Persistez la consultation
            consultationDAO.create(consultation);
            
            idConsult = employe.getId();

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
            return idConsult;
        }
    }

    public void ajouterCommentaireConsultation(Long idConsultation, String commentaire) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();

            // Récupérer la consultation par son ID
            Consultation consultation = consultationDAO.findById(idConsultation);
            if (consultation == null) {
                throw new IllegalArgumentException("Consultation avec ID " + idConsultation + " non trouvée.");
            }

            // Ajouter le commentaire à la consultation
            consultation.setCommentaire(commentaire);

            // Persister la consultation mise à jour
            consultationDAO.update(consultation);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void confirmerRdv(Long idConsultation) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();

            // Récupérer la consultation par son ID
            Consultation consultation = consultationDAO.findById(idConsultation);
            if (consultation == null) {
                throw new IllegalArgumentException("Consultation avec ID " + idConsultation + " non trouvée.");
            }

            // Récupérer le client et le medium associés
            Client client = consultation.getClient();
            Medium medium = consultation.getMedium();
            Employe employe = consultation.getEmploye();
            // Marquer l'employé comme non disponible
            employe.setEstDisponible(false);
            employeDAO.update(employe);
            // Construire le message de confirmation
            String message = String.format("Bonjour %s,\n\nVotre rendez-vous avec le médium %s est confirmé.\nVous pouvez contacter le médium à l'adresse suivante : %s.\n\nCordialement,\nL'équipe Predict'IF",
                    client.getPrenom(), medium.getNomMedium(), employe.getTelephone());

            // Envoyer l'email au client
            Message.envoyerMail("noreply@predictif.com", client.getMail(), "Confirmation de votre rendez-vous", message);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
     public List<Consultation> obtenirHistoriqueConsultations(Long idClient) {
        JpaUtil.creerContextePersistance();
        List<Consultation> consultations = null;

        try {
            JpaUtil.ouvrirTransaction();

            // Récupérer le client par son ID
            Client client = clientDAO.findById(idClient);
            if (client == null) {
                throw new IllegalArgumentException("Client avec ID " + idClient + " non trouvé.");
            }

            // Récupérer toutes les consultations associées à ce client
            consultations = consultationDAO.findByClient(client);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consultations;
    }
     
      public void terminerConsultation(Long idConsultation) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();

            // Récupérer la consultation par son ID
            Consultation consultation = consultationDAO.findById(idConsultation);
            if (consultation == null) {
                throw new IllegalArgumentException("Consultation avec ID " + idConsultation + " non trouvée.");
            }

            // Marquer la consultation comme terminée
            consultation.setTerminee(true);
            consultationDAO.update(consultation);

            // Rendre l'employé disponible
            Employe employe = consultation.getEmploye();
            employe.setEstDisponible(true);
            employeDAO.update(employe);

            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
}
