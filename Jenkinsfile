pipeline {
    agent any

    tools {
        jdk 'JDK 25'          // Configure JDK in Jenkins global tools
        maven 'Maven 3'       // Configure Maven in Jenkins global tools
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
            		url: 'https://github.com/ayan37/herokuappfinal.git'

            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng.xml -Dheadless=true'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'index.html',
                    reportName: 'Extent Report',
                    keepAll: true,                     // Keep reports for all builds
                    alwaysLinkToLastBuild: true,       // Link to latest build report
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            publishHTML([
            reportDir: 'reports',
            reportFiles: 'index.html',
            reportName: 'Extent Report',
            keepAll: true,
            alwaysLinkToLastBuild: true,
            allowMissing: true   // allow publishing even if missing
        ])
        }
    }
}