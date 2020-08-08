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

def prettyPrintMap(description, aMap, isRecursive = false, recuPad = "" ) {
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
                        Key1 : [
                            subKey1 : "some value",
                            subKey2 : [
                                complexKey3 : [ "some value", "other value"],
                                complexKey4 : { size -> size == 1 },
                            ],
                        ],
                        Key2 : [ 
                            subKey3 : "some value",
                            subKey4 : "some value",
                        ],
                        Key3 : { size -> size == 1 }
                    ]
                    prettyPrintMap("This the contents of a map structure", aValueMap)
                    print("------------------------------------------------------------\n")
                    def newitem = [{mbed-os-ci_unittests-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_unittests-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=unittests}, {$class=BooleanParameterValue, name=s3_upload, value=true}, {$class=StringParameterValue, name=s3_upload_name, value=unittests}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=BooleanParameterValue, name=windows, value=false}, {$class=BooleanParameterValue, name=linux, value=true}, {$class=BooleanParameterValue, name=mac, value=false}]}}, mbed-os-ci_build-GCC_ARM-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_build-GCC_ARM-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=build-GCC_ARM}, {$class=BooleanParameterValue, name=s3_upload, value=true}, {$class=StringParameterValue, name=s3_upload_name, value=build-GCC_ARM}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=StringParameterValue, name=toolchain, value=GCC_ARM}, {$class=StringParameterValue, name=targets, value=K64F CY8CKIT_064B0S2_4343W DISCO_L475VG_IOT01A NUCLEO_F429ZI-BAREMETAL LPC1114-BAREMETAL NUCLEO_F429ZI}]}}}, {mbed-os-ci_cloud-client-pytest-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_cloud-client-pytest-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=cloud-client-pytest}, {$class=BooleanParameterValue, name=s3_upload, value=false}, {$class=StringParameterValue, name=s3_upload_name, value=cloud-client-pytest}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=BooleanParameterValue, name=opentmi_upload, value=false}, {$class=StringParameterValue, name=targets_toolchains_build, value=['K64F':['GCC_ARM'], 'K66F':['GCC_ARM']]}, {$class=StringParameterValue, name=targets_toolchains_test, value=['K66F':['GCC_ARM']]}, {$class=StringParameterValue, name=tags, value=['default_target':'mbed-os-test,CLIENT', 'K64F':'mbed-os-test,HAS_SD_CARD,HAS_ETH_CONNECTION,CLIENT', 'K66F':'mbed-os-test,HAS_SD_CARD,HAS_ETH_CONNECTION,CLIENT', 'DISCO_L475VG_IOT01A':'mbed-os-test,RF-BOX']}]}}, mbed-os-ci_greentea-test-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_greentea-test-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=greentea-test}, {$class=BooleanParameterValue, name=s3_upload, value=true}, {$class=StringParameterValue, name=s3_upload_name, value=greentea-test}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=StringParameterValue, name=targets_toolchains, value=['K64F':['GCC_ARM'], 'DISCO_L475VG_IOT01A':['GCC_ARM'], 'NUCLEO_F429ZI-BAREMETAL':['GCC_ARM']]}, {$class=StringParameterValue, name=tests, value=['default_target':'tests-mbed_drivers-c_strings', 'K64F':'tests-mbed_drivers-c_strings']}, {$class=StringParameterValue, name=tests_skip, value=[]}, {$class=StringParameterValue, name=tags, value=['default_target':'mbed-os-test', 'DISCO_L475VG_IOT01A':'mbed-os-test,RF-BOX']}, {$class=BooleanParameterValue, name=opentmi_upload, value=true}, {$class=BooleanParameterValue, name=skip_passed_tests, value=true}]}}, mbed-os-ci_dynamic-memory-usage-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_dynamic-memory-usage-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=dynamic-memory-usage}, {$class=BooleanParameterValue, name=s3_upload, value=true}, {$class=StringParameterValue, name=s3_upload_name, value=dynamic-memory-usage}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=StringParameterValue, name=raas, value=https://ronja.mbedcloudtesting.com:443}, {$class=BooleanParameterValue, name=db_upload, value=true}]}}, mbed-os-ci_wisun-mesh-test-dev={build_parameters={propagate=true, wait=true, job=mbed-os-ci_wisun-mesh-test-dev, parameters=[{$class=StringParameterValue, name=upstream_build_number, value=1981}, {$class=StringParameterValue, name=subBuildsPostfix, value=-dev}, {$class=BooleanParameterValue, name=github_notify, value=true}, {$class=StringParameterValue, name=mbed_os_fork, value=ARMmbed/mbed-os}, {$class=StringParameterValue, name=mbed_os_base, value=}, {$class=StringParameterValue, name=mbed_os_topic, value=master}, {$class=StringParameterValue, name=pr_head_sha, value=}, {$class=StringParameterValue, name=github_title, value=wisun-mesh-test}, {$class=BooleanParameterValue, name=s3_upload, value=false}, {$class=StringParameterValue, name=s3_upload_name, value=wisun-mesh-test}, {$class=StringParameterValue, name=s3_upload_bucket, value=mbed-os-ci-internal}, {$class=StringParameterValue, name=s3_base_path, value=jenkins-ci/ARMmbed/mbed-os/mbed-os-ci-dev_branch}, {$class=StringParameterValue, name=apps_to_build, value=[[app: 'mbed-os-example-mesh-minimal', target: 'NUCLEO_F429ZI', toolchain: 'GCC_ARM'], [app: 'nanostack-border-router', target: 'K64F', toolchain: 'GCC_ARM']]}, {$class=StringParameterValue, name=suites, value=wisun_smoke_suite}]}}}]
                    prettyPrintMap("This the new item", newitem)
                    print("------------------------------------------------------------\n")
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
