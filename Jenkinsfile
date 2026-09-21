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

    }
}