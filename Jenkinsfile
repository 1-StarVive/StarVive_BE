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
                sh '''
                    # (선택 사항) 기존 서비스 중지 및 관련 리소스 정리 (볼륨 제외)
                    docker-compose -f docker-compose.yml down --remove-orphans || true 
                    
                    echo "Starting application using docker-compose..."
                    # Docker Compose 실행 (-f 로 파일 지정, up -d 로 백그라운드 실행)
                    # Jenkins 환경 변수들이 자동으로 전달된다고 가정
                    # 이미지 빌드를 Jenkins에서 이미 했으므로 --build 옵션 불필요
                    docker-compose -f docker-compose.yml up -d --remove-orphans

                    # 애플리케이션 상태 확인 (기존 로직 활용)
                    echo "Checking application status..."
                    sleep 30 
                    # 컨테이너 이름으로 확인 (docker-compose.yml의 container_name과 일치해야 함)
                    if ! docker ps | grep -q "${CONTAINER_NAME}"; then 
                        echo "Container ${CONTAINER_NAME} failed to start"
                        # docker-compose logs <서비스이름> 으로 로그 확인 가능
                        docker-compose -f docker-compose.yml logs app 
                        exit 1
                    fi
                    
                    # health check 로직은 유지 (포트 번호는 Jenkins 변수 사용)
                    for i in {1..30}; do
                        # curl 대상 주소는 localhost 유지 (Compose가 호스트 포트에 매핑)
                        if curl -s http://localhost:${SERVER_PORT}/actuator/health > /dev/null; then
                            echo "Application is up and running on port ${SERVER_PORT}"
                            # 성공 시 Jenkins 빌드 상태를 성공으로 간주 (exit 0 불필요할 수 있음)
                            break # 확인되면 루프 종료
                        fi
                        echo "Waiting for application to start... (attempt $i/30)"
                        sleep 2
                    done
                    
                    # 최종 확인 후 실패 처리
                    if ! curl -s http://localhost:${SERVER_PORT}/actuator/health > /dev/null; then
                         echo "Application failed to start after waiting"
                         docker-compose -f docker-compose.yml logs app
                         exit 1
                    fi
                '''
            }
        }
    }
    
    post {
    always {
        node('') {
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
}