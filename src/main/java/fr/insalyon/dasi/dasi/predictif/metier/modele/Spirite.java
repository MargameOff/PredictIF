/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.dasi.predictif.metier.modele;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author qsaillard
*/
enum SupportEnum { BOULE_DE_CRISTAL, MARC_DE_CAFE, OREILLES_DE_LAPIN }

@Entity
public class Spirite extends Medium {
    @Column(nullable = false)
    private List<SupportEnum> support;

    public Spirite() {
    }
    
    public Spirite(String nomMedium, String genre, String description, List<SupportEnum> sup) {
        super(nomMedium, genre, description); 
        this.support = sup;
    }
    
    
}
