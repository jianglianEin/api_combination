#!/usr/bin/env bash

kubectl delete deployment peopleservice-deployment
kubectl apply -f ./kubernetes/gcp_deployment.yml
kubectl apply -f ./kubernetes/gcp_service.yml