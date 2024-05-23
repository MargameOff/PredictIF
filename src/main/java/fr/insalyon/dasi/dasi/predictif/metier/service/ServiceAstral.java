package fr.insalyon.dasi.dasi.predictif.metier.service;

import fr.insalyon.dasi.dasi.predictif.metier.modele.Client;
import fr.insalyon.dasi.dasi.predictif.dao.ClientDAO;
import fr.insalyon.dasi.dasi.predictif.dao.JpaUtil;

public class ServiceAstral {

    private ClientDAO clientDAO;
    public ServiceAstral() {
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
}
