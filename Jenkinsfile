pipeline {
        agent any
        stages {
          stage("build & SonarQube analysis") {
            steps {
              sh './gradlew clean build sonar'
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




