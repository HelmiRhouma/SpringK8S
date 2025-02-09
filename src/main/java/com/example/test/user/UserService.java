package com.example.test.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // Retourne tous les utilisateurs si aucun mot-clé n'est fourni
    public List<User> listAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return repo.findByFirstNameContainingIgnoreCase(keyword);
        }
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public void delete(Integer id) throws UserNotFoundException {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new UserNotFoundException("Could not find any user with id " + id);
        }
    }
    public long getNombreUtilisateurs() {
        // Remplacer par votre logique pour récupérer le nombre d'utilisateurs depuis la base de données
        return 100; // Exemples de données statiques
    }
}
