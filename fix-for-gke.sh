#!/bin/bash

echo "🔧 Adaptation des fichiers pour GKE..."

# 1. Corriger le storageClassName dans PVC
sed -i 's/storageClassName: default/storageClassName: standard-rwo/g' k8s/mysql/pvc.yaml

# 2. Créer un deployment.yaml temporaire pour GKE
cat > k8s/app/deployment-gke.yaml << 'EOF'
apiVersion: apps/v1
kind: Deployment
metadata:
  name: springboot-app
  namespace: springboot-app
  labels:
    app: springboot-app
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 0
  selector:
    matchLabels:
      app: springboot-app
  template:
    metadata:
      labels:
        app: springboot-app
    spec:
      containers:
      - name: springboot-app
        image: gcr.io/PROJECT_ID/springboot-app:latest
        imagePullPolicy: Always
        ports:
        - containerPort: 8080
          name: http
        envFrom:
        - configMapRef:
            name: app-config
        - secretRef:
            name: app-secret
        resources:
          requests:
            cpu: 100m
            memory: 256Mi
          limits:
            cpu: 500m
            memory: 512Mi
        livenessProbe:
          httpGet:
            path: /
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
EOF

echo "✅ Fichiers adaptés pour GKE!"
echo ""
echo "📝 Dans le lab GKE, vous devrez:"
echo "1. Remplacer PROJECT_ID par votre vrai Project ID"
echo "2. Utiliser deployment-gke.yaml au lieu de deployment.yaml"
echo ""
echo "Commande dans le lab:"
echo "export PROJECT_ID=\$(gcloud config get-value project)"
echo "sed -i \"s/PROJECT_ID/\$PROJECT_ID/g\" k8s/app/deployment-gke.yaml"
