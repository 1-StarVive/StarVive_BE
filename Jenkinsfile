pipeline {
    agent any
    
    environment {
        GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs="-Xmx512m -XX:MaxMetaspaceSize=256m"'
        DB_USERNAME = credentials('DB_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
        DB_NAME = 'starvive'
        SERVER_PORT = '8081'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Clean Workspace') {
            steps {
                sh 'rm -rf ~/.gradle/caches/ || true'
            }
        }
        
        stage('Build') {
            steps {
                sh './gradlew clean build -x test --no-daemon'
            }
        }
        
        stage('Docker Build') {
            steps {
                sh 'docker build -t springboot-app:latest .'
            }
        }
        
        stage('Deploy') {
            steps {
                sh '''
                    # 기존 컨테이너 중지 및 제거
                    docker stop springboot-container || true
                    docker rm springboot-container || true
                    
                    # 새로운 컨테이너 실행
                    docker run -d --name springboot-container \
                    -e DB_USERNAME=${DB_USERNAME} \
                    -e DB_PASSWORD=${DB_PASSWORD} \
                    -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/${DB_NAME} \
                    -e SERVER_PORT=${SERVER_PORT} \
                    --network=host \
                    springboot-app:latest
                    
                    # 컨테이너가 제대로 시작되었는지 확인
                    sleep 30
                    if ! docker ps | grep -q springboot-container; then
                        echo "Container failed to start"
                        docker logs springboot-container
                        exit 1
                    fi
                    
                    # 애플리케이션이 응답하는지 확인
                    for i in {1..30}; do
                        if curl -s http://localhost:${SERVER_PORT}/actuator/health > /dev/null; then
                            echo "Application is up and running"
                            exit 0
                        fi
                        echo "Waiting for application to start... (attempt $i/30)"
                        sleep 2
                    done
                    
                    echo "Application failed to start"
                    docker logs springboot-container
                    exit 1
                '''
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}