def performDeploymentStages(String node, String app) {
    stage("build") {
        echo "Building the app [${app}] on node [${node}]"
    };
    stage("deploy") {
        echo "Deploying the app ${app}] on node [${node}]"
    };
    stage("test") {
        echo "Testing the app [${app}] on node [${node}]"
    }
}

def createStage(sname) {
    stages{
        stage("${sname} step1") {
                if ( sname == "stage 1" ) {
                steps {
                    echo "I am in ${sname} step 1"
                }
                }

        }
        stage("${sname} step2") {
            
                if ( sname == "stage 2" )  {
                steps {
                    echo "I am in ${sname} step 2"
                }
                }

        }
        stage("${sname} step3") {
            echo "I am in ${sname} step 3"
        }
    }
}


pipeline {
    agent {
        label 'master'
    }
    parameters {
        string(name: 'NODES', defaultValue: '1 2 3', description: 'Nodes to build, deploy and test')
        choice(name: 'ENV', choices: 'qa', description: 'Environment')
        string(name: 'APPS', defaultValue: 'app1 app2', description: 'App names')
    }

    stages {
        stage('Start') {
            steps {
                echo "This is the start"
            }
        }
        stage('parallel stage') {
            parallel {
                stage('P1 Test') {
                    steps {
                        script {
                            createStage("stage 1")
                        }
                    }
                }

                stage('P2 Test') {
                    steps {
                        script {
                            createStage("stage 1")
                        }
                    }
                }

                stage('P3 Test') {
                    steps {
                        script {
                            createStage("stage 1")
                        }
                    }
                }
            }
        }
    }
}
