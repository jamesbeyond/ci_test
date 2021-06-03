



pipeline {
    agent {
        label 'master'
    }

    stages {
        stage('Start') {
            steps {
                script {
                    dkr = load "common.groovy"
                    def first= dkr.first()
                    def second= dkr.second()
                    echo "This is the start"
                    sh script: """
                        ls -al
                        pwd
                        printenv
                    """, label: "LIST FILE AND SHOW ENV"
                    echo " First is ${first}, second is ${second}"
                }
            }
        }
        stage('another') {
            steps {
                script {
                    echo "Another First is ${first}, second is ${second}"
                }
            }
        }
    }
}