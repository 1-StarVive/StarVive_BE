   pipeline {
       agent any
       
       stages {
           stage('Checkout') {
               steps {
                   checkout scm
               }
           }
           
           stage('Build') {
               steps {
                   sh './gradlew clean build -x test'
               }
           }
           
           stage('Docker Build') {
               steps {
                   sh 'docker build -t springboot-app:latest .'
               }
           }
           
           stage('Deploy') {
               steps {
                   sh 'docker stop springboot-container || true'
                   sh 'docker rm springboot-container || true'
                   sh 'docker run -d -p 8081:8080 --name springboot-container springboot-app:latest'
               }
           }
       }
   }