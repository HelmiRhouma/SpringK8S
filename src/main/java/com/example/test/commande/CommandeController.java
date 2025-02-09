package com.example.test.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeService service;

    @GetMapping
    public String listCommandes(Model model) {
        List<Commande> listCommandes = service.listAll();
        model.addAttribute("commandes", listCommandes);
        return "commande"; // Renvoie la page liste des commandes
    }

    @GetMapping("/new")
    public String showNewCommandeForm(Model model) {
        model.addAttribute("commande", new Commande());
        return "commande_form"; // Formulaire de création de commande
    }

    @PostMapping("/save")
    public String saveCommande(@ModelAttribute("commande") Commande commande) {
        service.save(commande);
        return "redirect:/commande"; // Redirection vers la liste des commandes après enregistrement
    }

    @GetMapping("/delete/{id}")
    public String deleteCommande(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Commande supprimée avec succès.");
        } catch (CommandeNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/commande";  // Redirige vers la liste des commandes après suppression
    }

    @GetMapping("/commandes")
    public String getCommandes(@RequestParam(required = false) String keyword, Model model) {
        List<Commande> commandes;

        // Si le mot-clé de recherche est fourni, rechercher par le nom du client
        if (keyword != null && !keyword.isEmpty()) {
            commandes = service.findByClientNameContaining(keyword);  // Recherche par nom de client
        } else {
            commandes = service.listAll();  // Sinon, afficher toutes les commandes
        }

        model.addAttribute("commandes", commandes);
        model.addAttribute("keyword", keyword);
        return "commande";  // Nom de la vue Thymeleaf
    }

}
