pipeline {
    agent any

    stages {  // this stages and stage sections is requred to start a parallel pipeline
        stage('Simple parallel') {
            parallel {
                stage('Parallel 1') {
                    steps {
                        echo "This is simple parallel 1"
                    }
                }

                stage('Parallel 2') {
                    steps {
                        echo "This is simple parallel 2"
                    }
                }

                stage('Parallel 3') {
                    steps {
                        echo "This is simple parallel 3"
                    }
                }
            }
        }
    }
}