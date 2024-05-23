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
public class Employe extends Personne {
    @Column(nullable = false)
    private String Genre;
    @Column(nullable = false)
    private boolean estDisponible;

    public Employe() {
        this.estDisponible = true;
    }

    public Employe(String Genre, String nom, String prenom, String mail, String telephone, Date dateNaissance) {
        super(nom, prenom, mail, telephone, dateNaissance);
        this.Genre = Genre;
        this.estDisponible = true;
    }

    public String getGenre() {
        return Genre;
    }
    
}
