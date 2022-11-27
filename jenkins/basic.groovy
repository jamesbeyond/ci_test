pipeline {
    agent any
    stages {
        stage('basic') {
            steps {
                echo "This is the basic"
                sh label: "LIST FILE AND SHOW ENV", script: """
                    ls -al
                    pwd
                    printenv
                """
            }
        }
        stage('End') {
            steps {
                echo "This is the End"
            }
        }
    }
}