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
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String commentaire;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRdv;

    @ManyToOne
    @Column(nullable = false)
    private Client client;

   
    private Employe employe;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Medium medium;

    public Consultation() {
    }
    
    public Consultation(Date dateRdv, Medium med, Client cli) {
        this.dateRdv = dateRdv;
        this.medium = med;
        this.client = cli;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateRdv() {
        return dateRdv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public void setDateRdv(Date dateRdv) {
        if (dateRdv == null || dateRdv.before(new Date())) {
            throw new IllegalArgumentException("La date de rendez-vous doit être valide et future.");
        }
        this.dateRdv = dateRdv;
    }
}
