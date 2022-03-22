package com.example.demorest.rest;


import com.example.demorest.model.AppResponse;
import com.example.demorest.model.Produit;
import com.example.demorest.repo.ProduitRepository;
import com.example.demorest.service.FileStorageService;
import com.example.demorest.service.ProduitService;
import com.example.demorest.utils.AppConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ProduitRestController {
    @Autowired
    ProduitRepository produitRepository;
    ProduitService produitService;
    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping(value = AppConstants.PRODUIT_URI, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AppResponse createProduit(
            @RequestParam(value = AppConstants.PRODUIT_PARAM_NOM, required = true) String nomproduit,
            @RequestParam(value = AppConstants.PRODUIT_PARAM_PRIX, required = true) String prixproduit,
            @RequestParam(value = AppConstants.PRODUIT_PARAM_CATEGORIE, required = true) String categorieproduit,
            @RequestParam(value = AppConstants.PRODUIT_PARAM_DESCRIPTION, required = true) String descriptionproduit,
            @RequestParam(value = AppConstants.PRODUIT_PARAM_ETAT, required = true) String etatproduit,
            @RequestParam(required = true, value = AppConstants.PRODUIT_FILE_PARAM) MultipartFile file)
            throws JsonParseException, JsonMappingException, IOException {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
                .path(fileName).toUriString();

        Produit produit = new Produit();
        produit.setImageproduit(fileDownloadUri);
        produit.setNomproduit(nomproduit);
        produit.setPrixproduit(prixproduit);
        produit.setCategorieproduit(categorieproduit);
        produit.setDescriptionproduit(descriptionproduit);
        produit.setEtatproduit(etatproduit);

        //produitService.addProduit(produit);
        produitRepository.save(produit);

        return new AppResponse(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MSG);
    }

   // @RequestMapping(value = AppConstants.PRODUIT_URI, method = RequestMethod.GET)
  //  @GetMapping("produit")
    @GetMapping("produit/categorie/Tous")
    public List<Produit> getAllProduits() {
       // return produitService.getAllProduit();
        return produitRepository.findAll();
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //   logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

/*
    @GetMapping("/produit/categorie/{categorieproduit}")

    public Optional<Produit> getAllProduits(@PathVariable("categorieproduit") String categorieproduit) {

        return produitRepository.findByCategorieproduit(categorieproduit  );
    }

    @GetMapping(value ="/produit/{idproduit}")
    public Optional<Produit> findById(@PathVariable("idproduit") long idproduit) {

        return produitRepository.findById(idproduit);
    }*/
   @RequestMapping(value = "produit/categorie/{categorieproduit}", method = RequestMethod.GET)
	public List<Produit> findOneByTitre(@PathVariable("categorieproduit") String categorieproduit) {
        return produitRepository.findOneByCategorieproduit(categorieproduit);
		//return produitService.findOneByTitre(categorieproduit);
	}

    //suprimer produit
    @DeleteMapping("/produit/delete/{idproduit}")

    public void deleteProduit(@PathVariable long idproduit) {
       produitRepository.deleteById(idproduit);

    }

    //modifier produit
    @PutMapping("/modifierproduit/{id}")
    Produit updateProduit(@RequestBody Produit newProduit, @PathVariable Long id) {

        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setCategorieproduit(newProduit.getCategorieproduit());
                    produit.setNomproduit(newProduit.getNomproduit());
                    produit.setPrixproduit(newProduit.getPrixproduit());
                    produit.setDescriptionproduit(newProduit.getDescriptionproduit());
                    produit.setEtatproduit(newProduit.getEtatproduit());
                    return produitRepository.save(produit);
                })
                .orElseGet(() -> {
                    newProduit.setIdproduit(id);
                    return produitRepository.save(newProduit);
                });
    }
}
