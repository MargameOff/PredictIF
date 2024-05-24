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
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false, unique=true)
    private String mail;
    @Column(nullable = false)
    private String telephone;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", telephone=" + telephone + ", dateNaissance=" + dateNaissance + '}';
    }


    public Personne() {
    }

    public Personne(String nom, String prenom, String mail, String telephone, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }
    
    
    
    
}
