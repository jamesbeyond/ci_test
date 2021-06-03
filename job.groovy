
pipeline {
    agent {
        label 'master'
    }

    environment {
        dkr = load "common.groovy"
        FIRST = dkr.first()
        SECOND = dkr.second()
    }
    stages {
        stage('Start') {
            steps {
                script {
                    
                    echo "This is the start"
                    sh script: """
                        ls -al
                        pwd
                        printenv
                    """, label: "LIST FILE AND SHOW ENV"
                    echo " First is ${FIRST}, second is ${SECOND}"
                    echo dkr
                    
                }
            }
        }
        stage('another') {
            steps {
                script {
                    echo "Another First is ${FIRST}, second is ${SECOND}"
                    "${dkr}".func()
                }
            }
        }
    }
}
