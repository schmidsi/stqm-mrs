#!/usr/bin/env groovy

pipeline {
    agent any

    tools{
        maven 'localMaven'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}