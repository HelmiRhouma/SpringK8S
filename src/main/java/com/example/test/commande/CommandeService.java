package com.example.test.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepositroy repo;  // Un seul repository

    // Liste de toutes les commandes
    public List<Commande> listAll() {
        return repo.findAll();
    }
    // Recherche des commandes par nom de client
    public List<Commande> findByClientNameContaining(String keyword) {
        return repo.findByClientNameContaining(keyword);  // Recherche par nom de client
    }
    // Sauvegarde d'une commande
    public void save(Commande commande) {
        repo.save(commande);
    }
    // Récupération d'une commande par son ID
    public Commande get(Integer id) throws CommandeNotFoundException {
        return repo.findById(id).orElseThrow(() -> new CommandeNotFoundException("Commande introuvable avec ID " + id));
    }
    // Suppression d'une commande par son ID
    public void delete(Integer id) throws CommandeNotFoundException {
        Commande commande = repo.findById(id).orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID " + id));
        repo.delete(commande);
    }
}
