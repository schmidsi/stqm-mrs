#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'Maven 3.0.0'
        jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn package'
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