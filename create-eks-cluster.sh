#!/bin/bash

set -e

echo "🚀 Création rapide du cluster EKS..."

# Variables
CLUSTER_NAME="springboot-eks"
REGION="us-east-1"
NODE_COUNT=2
NODE_TYPE="t3.small"
ROLE_NAME="eks-service-role"
NODE_ROLE_NAME="eks-node-role"

# 1. Créer les rôles IAM
echo "Création des rôles IAM..."

aws iam create-role \
  --role-name $ROLE_NAME \
  --assume-role-policy-document '{
    "Version": "2012-10-17",
    "Statement": [
      {
        "Action": "sts:AssumeRole",
        "Effect": "Allow",
        "Principal": {
          "Service": "eks.amazonaws.com"
        }
      }
    ]
  }' 2>/dev/null || echo "Role exists"

aws iam attach-role-policy \
  --role-name $ROLE_NAME \
  --policy-arn arn:aws:iam::aws:policy/AmazonEKSClusterPolicy 2>/dev/null || echo "Policy attached"

aws iam create-role \
  --role-name $NODE_ROLE_NAME \
  --assume-role-policy-document '{
    "Version": "2012-10-17",
    "Statement": [
      {
        "Action": "sts:AssumeRole",
        "Effect": "Allow",
        "Principal": {
          "Service": "ec2.amazonaws.com"
        }
      }
    ]
  }' 2>/dev/null || echo "Node role exists"

aws iam attach-role-policy --role-name $NODE_ROLE_NAME --policy-arn arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy 2>/dev/null || echo "Policy attached"
aws iam attach-role-policy --role-name $NODE_ROLE_NAME --policy-arn arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy 2>/dev/null || echo "Policy attached"
aws iam attach-role-policy --role-name $NODE_ROLE_NAME --policy-arn arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly 2>/dev/null || echo "Policy attached"

# 2. Créer le cluster EKS
echo "Création du cluster EKS..."
aws eks create-cluster \
  --name $CLUSTER_NAME \
  --version 1.31 \
  --role-arn arn:aws:iam::$(aws sts get-caller-identity --query Account --output text):role/$ROLE_NAME \
  --resources-vpc-config subnetIds=$(aws ec2 describe-subnets --region $REGION --query 'Subnets[0:2].SubnetId' --output text) \
  --region $REGION 2>/dev/null || echo "Cluster exists"

# 3. Attendre que le cluster soit prêt
echo "Attente de la création du cluster (5-10 min)..."
aws eks wait cluster-active \
  --name $CLUSTER_NAME \
  --region $REGION

# 4. Créer les nodes
echo "Création des nodes..."
NODE_ROLE_ARN="arn:aws:iam::$(aws sts get-caller-identity --query Account --output text):role/$NODE_ROLE_NAME"

aws eks create-nodegroup \
  --cluster-name $CLUSTER_NAME \
  --nodegroup-name springboot-nodes \
  --scaling-config minSize=1,maxSize=$NODE_COUNT,desiredSize=$NODE_COUNT \
  --subnets $(aws ec2 describe-subnets --region $REGION --query 'Subnets[0:2].SubnetId' --output text) \
  --node-role $NODE_ROLE_ARN \
  --instance-types $NODE_TYPE \
  --region $REGION 2>/dev/null || echo "Nodegroup exists"

# 5. Attendre les nodes
echo "Attente des nodes (5-10 min)..."
aws eks wait nodegroup-active \
  --cluster-name $CLUSTER_NAME \
  --nodegroup-name springboot-nodes \
  --region $REGION

# 6. Configurer kubectl
echo "Configuration de kubectl..."
aws eks update-kubeconfig \
  --name $CLUSTER_NAME \
  --region $REGION

# 7. Vérifier
echo "✅ Cluster créé!"
kubectl cluster-info
kubectl get nodes
