def performDeploymentStages(String node, String app) {
    stage("build") {
        echo "Building the app [${app}] on node [${node}]"
    }
    stage("deploy") {
        echo "Deploying the app ${app}] on node [${node}]"
    }
    stage("test") {
        echo "Testing the app [${app}] on node [${node}]"
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
        stage('parallel stage') {
                        //def apps = [:]
                        //for (app in params.APPS.tokenize()) {
                        //    apps[ "${app}" ] = performDeploymentStages("test", app)
                        //}
            parallel {

                    stage("stage 1") {
                        stage('stage1 step1') {
                                echo "I am in stage 1 step 1"
                        }
                        stage('stage1 step2') {
                            echo "I am in stage 1 step 2"
                        }
                        stage('stage1 step3') {
                            echo "I am in stage 1 step 3"
                        }
                    }
                    stage("stage 2") {
                        stage('stage2 step1') {
                            echo "I am in stage 2 step 1"
                        };
                        stage('stage2 step2') {
                            echo "I am in stage 2 step 2"
                        };
                        stage('stage2 step3') {
                            echo "I am in stage 2 step 3"
                        }
                    }
            }
        }
    }
}