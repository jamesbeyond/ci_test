def simplejob(String app) {
    stage("build simple") {
        echo "Building the app [${app}]"
    }
    stage("deploy simple") {
        echo "Deploying the app ${app}]"
    }
    stage("test simple") {
        echo "Testing the app [${app}]"
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

        stage('Skipped Check') {
            when { expression { return false } }
            steps {
                echo "This is the Skipped Check"
            }
        }

        stage('Simple parallel') {
            parallel {
                stage('P1 Test') {
                    steps {
                        script {
                            echo "This is simple parallel 1"
                        }
                    }
                }

                stage('P2 Test') {
                    steps {
                        script {
                            echo "This is simple parallel 2"
                        }
                    }
                }

                stage('P3 Test') {
                    steps {
                        script {
                            echo "This is simple parallel 3"
                        }
                    }
                }
            }
        }

        stage('Gap') {
            steps {
                script {
                    echo "This is Gap"
                    warnError('Script failed!') {
                        sh('false')
                    }
                }
            }
        }
        
        stage('Failed') {
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
        
        stage('Build Other Failed') {
            steps {
                script {
                build("ci_test")
                }
            }
        }

        stage('Complex parallel') {
            parallel {
            
                stage('Complex P1') {
                    steps {
                        script {
                            echo "This is Complex P1"
                        }
                    }
                }

                stage('Complex P2') {
                    steps {
                        script {
                            echo "This is Complex P2"
                        }
                    }
                }
                
                stage('Complex P3') {
                    steps {
                        script {
                            echo "This is Complex P3"
                        }
                    }
                }
                
                stage('Complex P Skip') {
                    when { expression { return false } }
                    steps {
                        script {
                            echo "Complex P Skip"
                        }
                    }
                }
                stage('CompX2') {
                    //agent { label 'docker' }
                    stages {
                        stage('CompX2 P1') {
                            steps {
                                script {
                                    echo "This is CompX2 P1"
                                }
                            }
                        }
                        stage('CompX2 P2') {
                            steps {
                                script {
                                    echo "This is CompX2 P2"
                                }
                            }
                        }
                    }
                }
                stage('CompX3') {
                    when { expression { return true } }
                    stages {
                        stage('CompX3 P1'){
                            steps {
                                script {
                                    echo "This is CompX3 P1"
                                }
                            }
                        }
                        stage('CompX3 P2'){
                            steps {
                                script {
                                    def parallelSteps = [:]
                                    parallelSteps['PS1'] = {
                                            simplejob("AAA")   
                                    }

                                    ['PS2', 'PS3'].each{ toolchain ->
                                        parallelSteps["${toolchain}"] = {
                                            simplejob("BBB")                                        
                                        }
                                    }

                                    parallel parallelSteps
                                }
                            }
                        }
                        stage('CompX3 P3'){
                            steps {
                                script {
                                    echo "This is CompX3 P3"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
