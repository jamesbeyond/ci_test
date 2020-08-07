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

def prettyPrintMap(description, aMap, isRecursive = false , recuPad = "" ) {
    def output = recuPad + description + "\n" + recuPad + "[\n"
    def padding = "    " + recuPad
    aMap.each{ k, v ->
        if (v instanceof LinkedHashMap) {
            output += prettyPrintMap(k.toString() + " : " , v , true , padding )
        } else {
            output += padding + k.toString() + " : " + v.toString() + "\n"
        }
    }
    output += recuPad + "]\n\n"
    if (!isRecursive) {
        print(output)
    } else {
        return output
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
                    def aValueMap = [
                        Key1 : "Value1",
                        Key2 : [ 
                            subKey1 : "some value",
                            subKey2 : "some value",
                        ],
                        Key3 : { size -> size == 1 }
                    ]
                    println(aValueMap.getClass())
                    print("value map is ${aValueMap}\n")
                    print("------------------------------------------------------------\n")
                    //print(JsonOutput.prettyPrint(JsonOutput.toJson(aValueMap)))
                    prettyPrintMap("This the contents of a map structure", aValueMap)
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
