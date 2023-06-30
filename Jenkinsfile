node {
    agent any
    stages {
        stage('SonarQube analysis') {
            withSonarQubeEnv() {
              sh './gradlew sonarqube'
            }
          }
    }
}