import groovy.json.JsonOutput
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
    stage("${sname} step1") {
            if ( sname == "stage 1" ) {
                echo "I am in ${sname} step 1"
            }
    }
    stage("${sname} step2") {
            if ( sname == "stage 2" )  {
                echo "I am in ${sname} step 2"
            }
    }
    stage("${sname} step3") {
        echo "I am in ${sname} step 3"
    }
}

def createSriptStage(sname) {
    return {

        stage("${sname} SS1") {
            echo "I am in ${sname} step 1"
        }


        stage("${sname} SS2") {
            echo "I am in ${sname} step 2"
        }


        stage("${sname} SS3") {
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
                script {
                    echo "This is the start"
                    sh script: """
                        ls -al
                        pwd
                        printenv
                    """, label: "LIST FILE AND SHOW ENV"
                    def aValueMap = [:]
                    aValueMap["Key1"] = "Value1"
                    aValueMap["Key2"] = "Value2"
                    aValueMap["Key3"] = "Value3"
                    println(aValueMap.getClass())
                    println("value map is ${aValueMap}")
                    print(JsonOutput.prettyPrint(JsonOutput.toJson(aValueMap)))
                    
                    
                }
            }
        }
        stage('parallel stage') {
            parallel {
                stage('stage 1') {
                    steps {
                        script {
                                createStage("stage 1")
                        }
                    }
                }

                stage('stage 2') {
                    steps {
                        script {
                                createStage("stage 2")
                        }
                    }
                }

                stage('stage 3') {
                    steps {
                        script {
                                createStage("stage 3")
                        }
                    }
                }
            }
        }

        stage('2 parallel stage') {
            steps {
                script {
                    def pbuild = [:]
                    ['stage S1','stage S2','stage S3'].each { st ->
                        pbuild["$st"] = createSriptStage(st)
                    }
                    parallel pbuild
                }
            }
        }
    }
}
