#!/bin/bash
set -e  # fail fast on errors

APP_NAME="springboot-app"
IMAGE_NAME="springboot-app"
KIND_CLUSTER="kind"  # adjust if you have multiple kind clusters or custom names

# Generate unique tag based on timestamp
TAG=$(date +%Y-%m-%d-%H-%M-%S)
FULL_IMAGE_TAG="${IMAGE_NAME}:${TAG}"

echo "Building the Spring Boot app (mvn clean test install package)..."
./mvnw clean test install package

echo "Building Docker image with tag $FULL_IMAGE_TAG..."
docker build -t $FULL_IMAGE_TAG .

echo "Loading image into kind cluster..."
kind load docker-image $FULL_IMAGE_TAG --name $KIND_CLUSTER

echo "Upgrading Helm release with new image tag..."
helm upgrade $APP_NAME ./springboot-app-chart --set image.tag=$TAG

echo "Waiting for rollout to complete..."
kubectl rollout status deployment/$APP_NAME-deployment --timeout=120s

echo "Deployment done! Your app is updated and running with image tag $TAG"
