pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Building Saving Service...'
                sh 'chmod +x mvnw && ./mvnw clean package -DskipTests'
            }
        }

    }
}