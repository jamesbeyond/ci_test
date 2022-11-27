pipeline {
    agent any
    stages {

        stage('Start') {
            steps {
                echo "This is the start"
            }
        }

        stage('Skipped Stage') {
            when { expression { return false } }
            steps {
                echo "This is the Skipped Stage"
            }
        }

        stage('WarnError-Pass') {
            steps {
                echo "This is Warn Error"
                warnError('Script failed!') {
                    sh "true"
                }
            }
        }

        stage('WarnError-Fail') {
            steps {
                echo "This is Warn Error"
                warnError('Script failed!') {
                    sh "false"
                }
            }
        }

        stage('Unstable') {
            steps {
                unstable('Script failed!')
            }
        }

        stage('Try-Cache-Pass') {
            steps {
                script {
                    try {
                      sh('true')
                    } catch (ex) {
                      unstable('Script failed!')
                    }
                }
            }
        }

        stage('Try-Cache-Fail') {
            steps {
                script {
                    try {
                      sh('false')
                    } catch (ex) {
                      unstable('Script failed!')
                    }
                }
            }
        }

        stage('End') {
            steps {
                echo "This is the End"
            }
        }

    }
}