package fr.insalyon.dasi.dasi.predictif.vue;

import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Astrologue;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceAstral;
import fr.insalyon.dasi.dasi.predictif.metier.service.ServiceAuth;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author qsaillard
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        // Creation de la fabrique
        JpaUtil.creerFabriquePersistance();
        
        // Création d'un client
        Client nouveauClient = new Client("Doe", "John", "john.doe@example.com", "0123456789", new Date(80, 0, 1), "motDePasse");
        
        // Création du service d'authentification
        ServiceAuth serviceAuth = new ServiceAuth();
        
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
        
        Employe emp = new Employe("M", "Doe", "John", "john.doe@example.com", "0123456789", new Date(80, 0, 1));
        serviceAuth.inscrireEmploye(emp);
       
        
        Astrologue astrologue = new Astrologue("Diplôme d'astrologie karmique", 2010, "Madame Irma", "Féminin", "Spécialiste en astrologie karmique");
        Date dateRdv = new Date();
        serviceAuth.inscrireMedium(astrologue);
        System.out.println("LeTESTTESTETSEQFSDFSCVCSDV.");
        
        
        JpaUtil.fermerFabriquePersistance();
    }
}
