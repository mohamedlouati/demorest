package com.example.demorest.service;

import com.example.demorest.model.Produit;

import java.util.List;

public interface ProduitService {

    Produit addProduit(Produit produit);

    List<Produit> getAllProduit();

    void delete(Long idproduit);

    /*Produit findOneByTitre(String categorieproduit);*/
}
