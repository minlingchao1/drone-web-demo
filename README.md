### CICD WEB-TEST DEMO

[![Build Status](http://10.2.16.8:30707/api/badges/hor/webdemo/status.svg)](http://10.2.16.8:30707/hor/webdemo)

#### PreRequest

1. Standard Java WEB Project
2. Standard Maven Project
3. Project Docker file Configuration - deploy on `docker hub | docker registry `
4. Project Deployment Configuration - deploy on `kubernetes`

#### WebHook

1. Push
2. Pull Request
3. Commit
4. Tag
5. Deploy

#### CICD Configuration

```
workspace:
  base: /drone
  path: src/
pipeline:
  build:
    image: neunnsy/maven3-jdk-7:v1.0.0
    commands:
      - mvn package
      - mvn site
      - mvn cobertura:cobertura
  publish-master:
    image: neunnsy/docker
    registry: index.neunn.com
    username: admin
    password: <your password here>
    repo: index.neunn.com/library/webdemo
    tag: 0.1.${DRONE_BUILD_NUMBER}
    file: Dockerfile
    insecure: false
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    privileged: true
    trusted: true
    when:
      branch: master
      status: [ success ]
  publish-dev:
    image: neunnsy/docker
    registry: index.neunn.com
    username: admin
    password: WWW.163.com
    repo: index.neunn.com/library/webdemo
    tag: 0.1.${DRONE_BUILD_NUMBER}
    file: Dockerfile
    insecure: false
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    privileged: true
    trusted: true
    when:
      branch: dev
      status: [ success ]
  kubernetes-master:
    image: vallard/drone-kube
    template: deployment.yml
    namespace: default
    secrets: [ KUBE_CA, KUBE_TOKEN, KUBE_SERVER ]
    when:
      branch: master
      status: [ success ]
  kubernetes-dev:
    image: vallard/drone-kube
    template: deployment.yml
    namespace: default
    secrets: [ KUBE_CA, KUBE_TOKEN, KUBE_SERVER ]
    when:
      branch: dev
      status: [ success ]
```

#### Deployment Configuration

```
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: cicd-webdemo
  labels:
    app: cicd-webdemo
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: cicd-webdemo
    spec:
      containers:
      - name: cicd-webdemo
        image: index.neunn.com/library/webdemo:0.1.{{ build.number }}
        ports:
        - containerPort: 8080
```

#### Drone Secret

This is used to store kubernetes cluster `secret` & `token`

1. Drone Secret Name

```
KUBE_SERVER: kubernetes server

KUBE_TOKEN: kubernetes token (need decode by base64)

KUBE_CA: kubernetes ca

```

#### How to obtain kubernetes secret & token

1. After deployment inspect you pod for name of (k8s) secret with **token** and **ca.crt**
```
kubectl describe po/[ your pod name ] | grep SecretName | grep token
```
(When you use **default service account**)

2. Get data from you (k8s) secret
```
kubectl get secret [ your default secret name ] -o yaml | egrep 'ca.crt:|token:'
```
3. Copy-paste contents of ca.crt into your drone's **KUBERNETES_CERT** secret
4. Decode base64 encoded token
```
echo [ your k8s base64 encoded token ] | base64 -d && echo''
```
5. Copy-paste decoded token into your drone's **KUBERNETES_TOKEN** secret

6. Add cluster-role-binding
```
kubectl create clusterrolebinding default --clusterrole cluster-admin --serviceaccount=default:default
```

#### Test Report & Code-Cover Report

Test Report: visit `http://10.2.16.8:30126/site/surefire-report.html`

Code-Cover Report: visit `http://10.2.16.8:30126/site/cobertura/project-reports.html`

Check-Style Report: visit `http://10.2.16.8:30126/site/checkstyle.html`

#### WebApp

Web App: visit `http://10.2.16.8:30126/webdemo`
