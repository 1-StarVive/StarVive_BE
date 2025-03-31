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
        sh '''
            docker run -d --name springboot-container \
            -e DB_USERNAME=${DB_CREDS_USR} \
            -e DB_PASSWORD=${DB_CREDS_PSW} \
            -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/${DB_NAME} \
            -e SERVER_PORT=8081 \
            --network=host \
            springboot-app:latest
        '''
    }
}
       }
   }