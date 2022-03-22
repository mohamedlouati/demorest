package com.example.demorest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Produit {
    @Id
    @GeneratedValue
    private Long idproduit;
    private String nomproduit;
    private String categorieproduit;
    private String prixproduit;
    private String descriptionproduit;
    private String etatproduit;

    private String imageproduit;

    public Produit() {
    }

    public Produit(Long idproduit, String nomproduit, String categorieproduit, String prixproduit, String descriptionproduit, String etatproduit, String imageproduit) {
        this.idproduit = idproduit;
        this.nomproduit = nomproduit;
        this.categorieproduit = categorieproduit;
        this.prixproduit = prixproduit;
        this.descriptionproduit = descriptionproduit;
        this.etatproduit = etatproduit;
        this.imageproduit = imageproduit;
    }

    public Long getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(Long idproduit) {
        this.idproduit = idproduit;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public String getCategorieproduit() {
        return categorieproduit;
    }

    public void setCategorieproduit(String categorieproduit) {
        this.categorieproduit = categorieproduit;
    }

    public String getPrixproduit() {
        return prixproduit;
    }

    public void setPrixproduit(String prixproduit) {
        this.prixproduit = prixproduit;
    }

    public String getDescriptionproduit() {
        return descriptionproduit;
    }

    public void setDescriptionproduit(String descriptionproduit) {
        this.descriptionproduit = descriptionproduit;
    }

    public String getEtatproduit() {
        return etatproduit;
    }

    public void setEtatproduit(String etatproduit) {
        this.etatproduit = etatproduit;
    }

    public String getImageproduit() {
        return imageproduit;
    }

    public void setImageproduit(String imageproduit) {
        this.imageproduit = imageproduit;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idproduit=" + idproduit +
                ", nomproduit='" + nomproduit + '\'' +
                ", categorieproduit='" + categorieproduit + '\'' +
                ", prixproduit='" + prixproduit + '\'' +
                ", descriptionproduit='" + descriptionproduit + '\'' +
                ", etatproduit='" + etatproduit + '\'' +
                ", imageproduit='" + imageproduit + '\'' +
                '}';
    }
}