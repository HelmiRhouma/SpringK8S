#!/bin/bash
echo "🔧 Adaptation des fichiers pour AWS EKS..."
sed -i 's/storageClassName: .*/storageClassName: gp2/g' k8s/mysql/pvc.yaml
sed -i 's|image: .*springboot-app.*|image: 041327037753.dkr.ecr.us-east-1.amazonaws.com/springboot-app:latest|g' k8s/app/deployment.yaml
sed -i '/imagePullSecrets:/,+2d' k8s/app/deployment.yaml
echo "✅ Fichiers adaptés!"
grep "storageClassName" k8s/mysql/pvc.yaml
grep "image:" k8s/app/deployment.yaml | grep springboot
