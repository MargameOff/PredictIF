/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.metier.modele;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author qsaillard
 */
@Entity
public class Astrologue extends Medium {
    @Column(nullable = false)
    private String formation;
    @Column(nullable = false)
    private int promotion;

    public Astrologue() {
    }

    public Astrologue(String formation, int promotion, String nomMedium, String genre, String description) {
        super(nomMedium, genre, description);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
}
