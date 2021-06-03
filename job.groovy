def dkr =load "common.groovy"

def first= dkr.first
def second= dkr.second

pipeline {
    agent {
        label 'master'
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
                    echo " Fist is ${first}, second is ${second}"
                }
            }
        }

    }
}
