#!/bin/bash
set -e  # fail fast on errors

echo "Building the Spring Boot app (mvn clean test install package)..."
./mvnw clean test install package

echo "Building Docker image..."
docker build -t springboot-app:local .

echo "Loading image into kind cluster..."
kind load docker-image springboot-app:local

echo "Restarting Kubernetes deployment..."
kubectl rollout restart deployment/springboot-app-deployment

echo "Waiting for deployment rollout to complete..."
kubectl wait --for=condition=available --timeout=120s deployment/springboot-app-deployment

echo "Deployment done! Your app is updated and ready."
