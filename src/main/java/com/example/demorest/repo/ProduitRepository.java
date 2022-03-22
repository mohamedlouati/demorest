package com.example.demorest.repo;

import com.example.demorest.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

 //   public Optional<Produit> findByCategorieproduit(String categorieproduit);
  //  Optional<Produit> findById(ID var1);
 //   public Optional<Produit> findByIdproduit(long idproduit);
  List<Produit> findOneByCategorieproduit(String categorieproduit);
}
