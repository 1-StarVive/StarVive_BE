pipeline {
    agent any
    
    environment {
        GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs="-Xmx512m -XX:MaxMetaspaceSize=256m"'
        GOOGLE_CLIENT_ID_CRED = credentials('google-client-id')
        GOOGLE_CLIENT_SECRET_CRED = credentials('google-client-secret')
        KAKAO_CLIENT_ID_CRED = credentials('kakao-client-id')
        KAKAO_CLIENT_SECRET_CRED = credentials('kakao-client-secret')
        JWT_SECRET_KEY_CRED = credentials('jwt-secret-key')
        AWS_ACCESS_KEY_ID_CRED = credentials('aws-access-key-id')
        AWS_SECRET_ACCESS_KEY_CRED = credentials('aws-secret-access-key')
        DB_NAME = 'starvive_dev'
        SERVER_PORT = '8082'
        CONTAINER_NAME = 'springboot-container-dev'
        IMAGE_TAG = 'dev'
        AWS_REGION = 'ap-northeast-2'
        S3_BUCKET_NAME = 'starvive-assets'
        IMAGE_NAME = "springboot-app:${IMAGE_TAG}"
        DB_HOST = 'mysql'
        DB_PORT = '3306'
        REDIS_HOST = 'redis'
        REDIS_PORT = '6379'
        SPRING_PROFILES_ACTIVE = 'prod'
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
                sh "docker build -t springboot-app:${IMAGE_TAG} ."
            }
        }
        
        stage('Deploy') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'db-credentials-dev', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                    sh '''
                        export DB_USER="${DB_USER}"
                        export DB_PASS="${DB_PASS}"

                        docker-compose -f docker-compose.yml down --remove-orphans || true 
                        
                        echo "Starting application using docker-compose..."
                        docker-compose -f docker-compose.yml up -d --remove-orphans

                        echo "Checking application status..."
                        sleep 30 
                        if ! docker ps | grep -q "${CONTAINER_NAME}"; then 
                            echo "Container ${CONTAINER_NAME} failed to start"
                            docker-compose -f docker-compose.yml logs app 
                            exit 1
                        fi
                        
                        for i in {1..30}; do
                            if curl -s http://localhost:${SERVER_PORT}/actuator/health > /dev/null; then
                                echo "Application is up and running on port ${SERVER_PORT}"
                                break
                            fi
                            echo "Waiting for application to start... (attempt $i/30)"
                            sleep 2
                        done
                        
                        if ! curl -s http://localhost:${SERVER_PORT}/actuator/health > /dev/null; then
                             echo "Application failed to start after waiting"
                             docker-compose -f docker-compose.yml logs app
                             exit 1
                        fi
                    '''
                }
            }
        }
    }
    
    post {
        success {success {
        slackSend (
            channel: '#이거보이면-자세교정',  
            color: 'good',
            message: "✅ [${env.JOB_NAME} #${env.BUILD_NUMBER}] 성공적으로 배포되었습니다! (<${env.BUILD_URL}|상세보기>)",
            tokenCredentialId: 'slack-token'  
        )
    }

    failure {
        slackSend (
            channel: '#이거보이면-자세교정',
            color: 'danger',
            message: "❌ [${env.JOB_NAME} #${env.BUILD_NUMBER}] 배포 실패! 로그 확인 요망. (<${env.BUILD_URL}|상세보기>)",
            tokenCredentialId: 'slack-token'
        )
    }


    always {
        node('') {
            sh '''
                rm -rf ~/.gradle/caches/ || true
                docker system prune -a -f || true 
                find . -name "*@tmp" -type d -exec rm -rf {} \\; 2>/dev/null || true
                echo "Current workspace size:"
                du -sh . || true
            '''
            
            cleanWs()
        }
    }
}
}