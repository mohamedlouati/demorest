package com.example.demorest.service;

import com.example.demorest.model.Produit;
import com.example.demorest.repo.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    ProduitRepository produitRepository;
    @Override
    public Produit addProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> getAllProduit() {
        return produitRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        produitRepository.deleteById(id);
    }

   /* @Override
    public Produit findOneByTitre(String categorieproduit) {
        return produitRepository.findOneByCategorieproduit(categorieproduit);
    }*/

}
