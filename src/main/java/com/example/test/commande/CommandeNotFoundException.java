package com.example.test.commande;

public class CommandeNotFoundException extends RuntimeException { // Hérite de RuntimeException pour mieux gérer les erreurs
    public CommandeNotFoundException(String message) {
        super(message);
    }
}
