# 🚀 Guide de Développement Automatique

## Workflow Simple
```bash
# 1. Modifier votre code
nano src/main/resources/templates/commande.html

# 2. Commit et push
git add .
git commit -m "feat: Ma modification"
git push origin main

# 3. Attendre 5-6 minutes ☕
# ✅ Déploiement automatique terminé!
```

## Timeline Automatique
- **0:00** - git push
- **0:10** - GitHub Actions démarre
- **3:00** - Build et push Docker image
- **3:30** - Update Git (deployment.yaml)
- **4:00** - ArgoCD détecte changement
- **5:00** - Déploiement Kubernetes
- **6:00** - ✅ Production updated!

## Exemples Pratiques

### Modifier une page
```bash
nano src/main/resources/templates/users.html
git add src/main/resources/templates/users.html
git commit -m "feat: Amélioration page utilisateurs"
git push origin main
# ✅ Auto-déployé en 5-6 min
```

### Ajouter un controller
```bash
nano src/main/java/com/example/controller/NouveauController.java
git add src/main/java/com/example/controller/
git commit -m "feat: Nouveau endpoint"
git push origin main
# ✅ Auto-déployé
```

## Surveillance
- GitHub Actions: https://github.com/HelmiRhouma/SpringK8S/actions
- ArgoCD: http://20.75.210.13
- Site: http://4.255.31.61

## Rollback si nécessaire
```bash
kubectl rollout undo deployment/springboot-app -n springboot-app
```
