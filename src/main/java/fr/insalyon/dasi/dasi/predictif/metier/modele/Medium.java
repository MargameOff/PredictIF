/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.metier.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author qsaillard
 */
@Entity
public abstract class Medium {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nomMedium;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String description;

    public Medium() {
    }

    public Medium(String nomMedium, String genre, String description) {
        this.nomMedium = nomMedium;
        this.genre = genre;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getNomMedium() {
        return nomMedium;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
