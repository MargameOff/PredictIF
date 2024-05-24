package fr.insalyon.dasi.dasi.predictif.metier.service;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.dao.ClientDAO;
import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;
import java.io.IOException;
import java.util.List;
import util.AstroNetApi;

public class ServiceAstral {

    private ClientDAO clientDAO;
    private AstroNetApi astroApi;
    public ServiceAstral() {
        astroApi = new AstroNetApi();
        clientDAO = new ClientDAO();
    }

    // Méthode pour consulter le profil astral d'un client
    public Client consulterProfilAstral(Long clientId) {
        JpaUtil.creerContextePersistance();
        Client client = null;
        String profilAstral = "";

        try {
            client = clientDAO.findById(clientId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return client;
    }
     public List<String> obtenirPredictions(Client client, int niveauAmour, int niveauSante, int niveauTravail) throws IOException {
        // Récupérer les informations astrologiques du client
        String couleur = client.getCouleurPorteBonheur();
        String animal = client.getAnimalTotem();
       
        // Obtenir les prédictions de l'API d'astrologie
        List<String> predictions = astroApi.getPredictions(couleur, animal, niveauAmour, niveauSante, niveauTravail);

        return predictions;
    }

    public void afficherPredictions(Client client, int niveauAmour, int niveauSante, int niveauTravail) throws IOException {
        List<String> predictions = astroApi.getPredictions(client.getCouleurPorteBonheur(), client.getAnimalTotem(), niveauAmour, niveauSante, niveauTravail);

        String predictionAmour = predictions.get(0);
        String predictionSante = predictions.get(1);
        String predictionTravail = predictions.get(2);

        System.out.println("Prédictions pour " + client.getPrenom() + " " + client.getNom() + ":");
        System.out.println("Amour: " + predictionAmour);
        System.out.println("Santé: " + predictionSante);
        System.out.println("Travail: " + predictionTravail);
    }
}
