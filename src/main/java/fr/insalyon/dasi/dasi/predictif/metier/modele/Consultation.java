package fr.insalyon.dasi.dasi.predictif.metier.modele;

import java.util.Date;
import javax.persistence.*;

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
    @JoinColumn(nullable = false)
    private Client client;

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", commentaire=" + commentaire + ", dateRdv=" + dateRdv + ", client=" + client + ", employe=" + employe + ", medium=" + medium + '}';
    }

    @ManyToOne
    private Employe employe;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Medium medium;
    
    private boolean terminee;

    public Consultation() {
        this.terminee = false;
    }

    public Consultation(Date dateRdv, Medium med, Client cli) {
        this.dateRdv = dateRdv;
        this.medium = med;
        this.client = cli;
        this.terminee = false;
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

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
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
