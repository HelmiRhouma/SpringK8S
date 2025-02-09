package com.example.test.commande;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepositroy extends JpaRepository<Commande, Integer> {
    List<Commande> findByClientNameContaining(String clientName);
}
