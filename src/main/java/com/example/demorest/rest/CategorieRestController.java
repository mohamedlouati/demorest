package com.example.demorest.rest;


import com.example.demorest.model.Categorie;
import com.example.demorest.model.Produit;
import com.example.demorest.repo.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategorieRestController {
    @Autowired
    CategorieRepository categorieRepository;

    @GetMapping("listcategorie")
    public List<Categorie> getAllCategorie() {
        return categorieRepository.findAll();
    }


}
