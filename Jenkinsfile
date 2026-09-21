pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building Saving Service...'
                sh 'chmod +x mvnw && ./mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -f Dockerfile -t saving-service .'
            }
        }

        stage('Docker Deploy') {
            steps {
                echo 'Deploying Saving Service Container...'

                sh '''
                    docker stop saving-service-docker || true
                    docker rm saving-service-docker || true

                    docker run -d \
                      --name saving-service-docker \
                      -p 8085:8080 \
                      -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/saving_db \
                      -e SPRING_DATASOURCE_USERNAME=root \
                      -e SPRING_DATASOURCE_PASSWORD=root \
                      -e EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka \
                      saving-service
                '''
            }
        }

    }
}