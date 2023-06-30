pipeline {
        agent none
        stages {
          stage("build & SonarQube analysis") {
            agent any
            steps {
              withSonarQubeEnv('My SonarQube Server') {
                sh './gradlew clean build sonarqube'
              }
            }
          }
          stage("Quality Gate") {
            steps {
              timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
              }
            }
          }
        }
}




