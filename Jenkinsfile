pipeline {
    agent any
    
    environment {
        GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.jvmargs="-Xmx512m -XX:MaxMetaspaceSize=256m"'
        DB_CREDS = credentials('db-credentials-dev')
        GOOGLE_CLIENT_ID_CRED = credentials('google-client-id')
        GOOGLE_CLIENT_SECRET_CRED = credentials('google-client-secret')
        KAKAO_CLIENT_ID_CRED = credentials('kakao-client-id')
        KAKAO_CLIENT_SECRET_CRED = credentials('kakao-client-secret')
        JWT_SECRET_KEY_CRED = credentials('jwt-secret-key')
        DB_NAME = 'starvive_dev'
        SERVER_PORT = '8082'
        CONTAINER_NAME = 'springboot-container-dev'
        IMAGE_TAG = 'dev'
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
                sh '''
                    # 기존 컨테이너 중지 및 제거
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    
                    # 새로운 컨테이너 실행 - 환경 변수 주입
                    docker run -d --name ${CONTAINER_NAME} \
                    -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/${DB_NAME} \
                    -e SPRING_DATASOURCE_USERNAME=${DB_CREDS_USR} \
                    -e SPRING_DATASOURCE_PASSWORD=${DB_CREDS_PSW} \
                    -e GOOGLE_CLIENT_ID=${GOOGLE_CLIENT_ID_CRED} \
                    -e GOOGLE_CLIENT_SECRET=${GOOGLE_CLIENT_SECRET_CRED} \
                    -e KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID_CRED} \
                    -e KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET_CRED} \
                    -e JWT_SECRET_KEY=${JWT_SECRET_KEY_CRED} \
                    -e SERVER_PORT=${SERVER_PORT} \
                    --network=host \
                    springboot-app:${IMAGE_TAG}
                    
                    # 컨테이너가 제대로 시작되었는지 확인
                    sleep 30
                    if ! docker ps | grep -q ${CONTAINER_NAME}; then
                        echo "Container failed to start"
                        docker logs ${CONTAINER_NAME}
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
                    docker logs ${CONTAINER_NAME}
                    exit 1
                '''
            }
        }
    }
    
    post {
    always {
        sh '''
            # Gradle 캐시 정리
            rm -rf ~/.gradle/caches/ || true
            
            # 미사용 Docker 리소스 정리
            docker system prune -f || true
            
            # 빌드 임시 파일 정리
            find . -name "*@tmp" -type d -exec rm -rf {} \\; 2>/dev/null || true
            
            # 오래된 Docker 이미지 정리 (7일 이상 된 것)
            docker images -q --filter "dangling=true" | xargs docker rmi -f || true
            
            # 워크스페이스 크기 확인 (로그 목적)
            echo "Current workspace size:"
            du -sh . || true
        '''
        
        // 워크스페이스 정리 (성공, 실패 모두 정리)
        cleanWs()
    }
}
}