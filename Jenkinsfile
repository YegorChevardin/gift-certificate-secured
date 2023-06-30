pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './gradlew sonarqube'
                }
            }
        }
    }
    
    post {
        always {
            publishQualityGate()
        }
    }
}




