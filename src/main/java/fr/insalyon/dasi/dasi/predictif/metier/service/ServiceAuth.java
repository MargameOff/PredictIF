package fr.insalyon.dasi.dasi.predictif.metier.service;

import fr.insalyon.dasi.dasi.predictif.dao.ClientDAO;
import fr.insalyon.dasi.dasi.predictif.dao.EmployeDAO;
import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import fr.insalyon.dasi.dasi.predictif.dao.MediumDAO;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Employe;
import fr.insalyon.dasi.dasi.predictif.metier.modele.Medium;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import util.AstroNetApi;
import util.Message;

/**
 *
 * @author qsaillard
 */
public class ServiceAuth {

    public static final String EXPEDITEUR = "admin@predictif.fr";
    private final ClientDAO clientDAO;
    private final MediumDAO mediumDAO;
    private final EmployeDAO employeDAO;

    public ServiceAuth() {
        clientDAO = new ClientDAO();
        mediumDAO = new MediumDAO();
        employeDAO = new EmployeDAO();
    }

    // Méthode pour obtenir un client par son identifiant
    public Client obtenirClient(Long id) {
        JpaUtil.creerContextePersistance();
        Client client = null;
        try {
            client = clientDAO.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return client;
    }

    public void inscrireMedium(Medium medium) {
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            mediumDAO.create(medium);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public void inscrireEmploye(Employe employe) {
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            employeDAO.create(employe);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Medium> listerTousLesMediums() {
        JpaUtil.creerContextePersistance();
        List<Medium> mediums = null;
        try {
            mediums = mediumDAO.findAll();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return mediums;
    }

    // Méthode pour obtenir tous les clients
    public List<Client> obtenirTousClients() {
        JpaUtil.creerContextePersistance();
        List<Client> clients = new ArrayList<>();  // Correction de l'initialisation de la liste

        try {
            clients = clientDAO.findAll();  // Utilisation correcte de la méthode findAll
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();  // Assurer la fermeture du contexte de persistance
        }

        return clients;  // Retour de la liste des clients après la fermeture du contexte
    }

    // Méthode pour vérifier si un mail est déjà utilisé par un autre client
    public boolean mailExisteDeja(String mail) {
        JpaUtil.creerContextePersistance();
        boolean existe = false;
        try {
            existe = clientDAO.findByMail(mail) != null;
        } catch (Exception e) {
            System.out.println("Trace : échec Verification mail existe");
            e.printStackTrace();
        }
        JpaUtil.fermerContextePersistance();
        return existe;
    }

    // Méthode pour créer un nouveau client
    public boolean inscriptionClient(Client client) throws IOException {
        JpaUtil.creerContextePersistance();
        boolean returnedValue = false;
        try {
            JpaUtil.ouvrirTransaction();
            //Créqtion profil astral
            AstroNetApi astroApi = new AstroNetApi();
            List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDateNaissance());
            client.setSigneZodiaque(profil.get(0));
            client.setSigneAstroChinois(profil.get(1));
            client.setCouleurPorteBonheur(profil.get(2));
            client.setAnimalTotem(profil.get(3));
            clientDAO.create(client);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès création employé : " + client);
            Message.envoyerMail(EXPEDITEUR, client.getMail(), "Création Compte Predict'IF", "Félicitation, votre compte est bien créé");
            returnedValue = true;
        } catch (Exception ex) {
            System.out.println("Trace : échec création employé");
            ex.printStackTrace(); // on fait toujours afficher les exceptions !
            //Envoyer un Mail
            Message.envoyerMail(EXPEDITEUR, client.getMail(), "Erreur Dans la Création Compte Predict'IF", "votre compte est bien Corrompu dsl");
            JpaUtil.annulerTransaction();
        } finally {
            //Envoyer un Mail
            JpaUtil.fermerContextePersistance();
        }
        return returnedValue;
    }

    public Client authentifierClient(String mail, String password) {
        JpaUtil.creerContextePersistance();
        Client client = null;

        try {
            // Recherche du client par son mail
            client = clientDAO.findByMail(mail);

            // Si le client n'existe pas ou si le mot de passe est incorrect, retourner null
            if (client == null || !client.getPassword().equals(password)) {
                client = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return client;
    }

// Méthode pour mettre à jour les informations d'un client
    public void mettreAJourClient(Client client) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();
            clientDAO.update(client);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

// Méthode pour supprimer un client
    public void supprimerClient(Client client) {
        JpaUtil.creerContextePersistance();

        try {
            JpaUtil.ouvrirTransaction();
            clientDAO.remove(client);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

}
