package fr.insalyon.dasi.dasi.predictif.vue;

import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Astrologue;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Consultation;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceAstral;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceAuth;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceConsultation;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceStats;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qsaillard
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // Creation de la fabrique
        JpaUtil.creerFabriquePersistance();

        // Création d'un client
        Client nouveauClient = new Client("Doe", "John", "john.doe@example.com", "0123456789", new Date(80, 0, 1), "motDePasse", "7 Avenue Jean Capelle Ouest, Villeurbanne");

        // Création du service d'authentification
        ServiceAuth serviceAuth = new ServiceAuth();
        ServiceConsultation serviceConsult = new ServiceConsultation();

        // Création du client dans la base de données
        serviceAuth.inscriptionClient(nouveauClient);
        System.out.println("Nouveau client créé : " + nouveauClient);

        // Authentification du client avec ses informations
        Client clientAuthentifie = serviceAuth.authentifierClient("john.doe@example.com", "motDePasse");
        if (clientAuthentifie != null) {
            System.out.println("Client authentifié : " + clientAuthentifie);
        } else {
            System.out.println("Échec de l'authentification. Vérifiez les informations.");
        }

        // Obtention de tous les clients
        System.out.println("Tous les clients :");
        serviceAuth.obtenirTousClients().forEach(System.out::println);

        // Vérification si un mail est déjà utilisé
        String mailExistant = "john.doe@example.com";
        if (serviceAuth.mailExisteDeja(mailExistant)) {
            System.out.println("Le mail '" + mailExistant + "' est déjà utilisé.");
        } else {
            System.out.println("Le mail '" + mailExistant + "' n'est pas encore utilisé.");
        }

        Employe emp = new Employe("Féminin", "Doe", "John", "johnee.doe@example.com", "0123456789", new Date(80, 0, 1));
        serviceAuth.inscrireEmploye(emp);

        Astrologue astrologue = new Astrologue("Diplôme d'astrologie karmique", 2010, "Madame Irma", "Féminin", "Spécialiste en astrologie karmique");
        System.out.println();
        Date dateRdv = new Date();
        serviceAuth.inscrireMedium(astrologue);
        Long idConv = serviceConsult.creerConsultation(astrologue.getId(), nouveauClient.getId());
        System.out.println("Consultation créée pour le client " + nouveauClient.getId() + " avec le médium " + astrologue.getId());
        serviceConsult.confirmerRdv(idConv);

        serviceConsult.ajouterCommentaireConsultation(idConv, "Un vrai pigeon lui");

        // Terminer la consultation
        serviceConsult.terminerConsultation(idConv);
        System.out.println("Consultation terminée avec ID : " + idConv);

        // Obtention de l'historique des consultations
        List<Consultation> historiqueConsultations = serviceConsult.obtenirHistoriqueConsultations(clientAuthentifie.getId());
        for (Consultation consultation : historiqueConsultations) {
            System.out.println(consultation);
        }

        ServiceStats serviceStats = new ServiceStats();

        // Obtenir et afficher les coordonnées des clients
        System.out.println("Coordonnées des clients:");
        List<String> coordonneesClients = serviceStats.obtenirCoordonneesClients();
        for (String coord : coordonneesClients) {
            System.out.println(coord);
        }

        // Obtenir et afficher le nombre de consultations par médium
        System.out.println("\nConsultations par médium:");
        Map<Medium, Long> consultationsParMedium = serviceStats.obtenirConsultationsParMedium();
        for (Map.Entry<Medium, Long> entry : consultationsParMedium.entrySet()) {
            System.out.println(entry.getKey().getNomMedium() + ": " + entry.getValue() + " consultations");
        }

        // Obtenir et afficher le top 5 des médiums avec le plus de RDV
        System.out.println("\nTop 5 des médiums avec le plus de RDV:");
        List<Medium> top5Mediums = serviceStats.obtenirTop5Mediums();
        for (Medium medium : top5Mediums) {
            System.out.println(medium.getNomMedium());
        }

        // Obtenir et afficher la répartition des clients par employé
        System.out.println("\nRépartition des clients par employé:");
        Map<Employe, Long> repartitionParEmploye = serviceStats.obtenirRepartitionClientsParEmploye();
        for (Map.Entry<Employe, Long> entry : repartitionParEmploye.entrySet()) {
            System.out.println(entry.getKey().getPrenom() + " " + entry.getKey().getNom() + ": " + entry.getValue() + " clients");
        }
        
        ServiceAstral serviceAstral = new ServiceAstral();
        Client client2 = new Client("Doe", "John", "Kevin@example.com", "0eeeer789", new Date(80, 0, 1), "motDePasse","8 rue du marais, PARIS");
        serviceAuth.inscriptionClient(client2);
        Client client2Connecte = serviceAuth.authentifierClient("Kevin@example.com", "motDePasse");
        if (client2Connecte != null) {
            System.out.println("Client authentifié : " + client2Connecte);
        } else {
            System.out.println("Échec de l'authentification. Vérifiez les informations.");
        }

        
        serviceAstral.afficherPredictions(client2Connecte, 3, 4, 5);
        //serviceAstral.obtenirPredictions(client2Connecte, 2, 2, 3);
        JpaUtil.fermerFabriquePersistance();
    }
}
