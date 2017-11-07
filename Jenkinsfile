#!/usr/bin/env groovy

pipeline {
    agent any

    tools{
        maven 'localMaven'
    }

    stages {
        stage('Initialize') {
            steps {
                echo 'Initializing...'
            }
        }
        stage('Compile & Test') {
            steps {
                echo 'running Maven package...'
                sh 'mvn package'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
            post {
                success {
            	    echo 'Maven package done.'
            	}
            }
        }
        stage('System Tests') {
            agent {
                dockerfile { dir 'testdb' }
            }
        	steps {
        	    echo 'running Docker'
        		}
        	}
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}