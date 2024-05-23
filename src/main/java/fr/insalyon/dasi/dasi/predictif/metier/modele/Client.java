/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.metier.modele;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author qsaillard
 */
@Entity
public class Client extends Personne {

    @Column(nullable = false)
    private String password;
    private Double latitude;
    private Double longitude;
    private String signeZodiaque;
    private String AnimalTotem;
    private String signeAstroChinois;
    private String CouleurPorteBonheur;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public void setSigneAstroChinois(String signeAstroChinois) {
        this.signeAstroChinois = signeAstroChinois;
    }

    public void setCouleurPorteBonheur(String CouleurPorteBonheur) {
        this.CouleurPorteBonheur = CouleurPorteBonheur;
    }

    public void setAnimalTotem(String AnimalTotem) {
        this.AnimalTotem = AnimalTotem;
    }
   
    
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPassword() {
        return password;
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public String getSigneAstroChinois() {
        return signeAstroChinois;
    }

    public String getCouleurPorteBonheur() {
        return CouleurPorteBonheur;
    }

    public String getAnimalTotem() {
        return AnimalTotem;
    }
    
    public Client() {
    }

    public Client(String nom, String prenom, String mail, String telephone, Date dateNaissance, String password) {
        super(nom, prenom, mail, telephone, dateNaissance);
        this.password = password;
        this.latitude = null;
        this.longitude = null;
    }

    @Override
    public String toString() {
        return "Client{" + super.toString() + "password=" + password + ", latitude=" + latitude + ", longitude=" + longitude + ", signeZodiaque=" + signeZodiaque + ", signeAstroChinois=" + signeAstroChinois + ", CouleurPorteBonheur=" + CouleurPorteBonheur + ", AnimalTotem=" + AnimalTotem + '}';
    }

    
    
}
