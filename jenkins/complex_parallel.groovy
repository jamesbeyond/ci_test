pipeline {
    agent any
    
    stages {
        stage('nested parallel') {
            parallel {
                stage('Parallel 1') {
                    steps { echo "This is simple parallel 1" }
                }

                stage('Parallel 2') {
                    stages{
                        stage('sub 2-1'){
                            steps { echo "This is sub parallel 2-1" }
                        }
                        stage('sub 2-2'){
                            parallel {
                                stage('sub 2-2-1'){
                                    steps { echo "This is sub parallel 2-2-1" }
                                }
                                stage('sub 2-2-2'){
                                    steps { echo "This is sub parallel 2-2-2" }
                                }
                                stage('sub 2-2-3'){
                                    steps { echo "This is sub parallel 2-2-3" }
                                }
                            }
                        }
                        stage('sub 2-3'){
                            parallel {
                                stage('sub 2-3-1'){
                                    steps { echo "This is sub parallel 2-3-1" }
                                }
                                stage('sub 2-3-2'){
                                    steps { echo "This is sub parallel 2-3-2" }
                                }
                                stage('sub 2-3-3'){
                                    steps { echo "This is sub parallel 2-3-3" }
                                }
                            }
                        }
                        stage('sub 2-4'){
                            steps { echo "This is sub parallel 2-4" }
                        }
                    }
                }

                stage('Parallel 3') {
                    stages{
                        stage('sub 3-1'){
                            steps { echo "This is sub parallel 3-1" }
                        }
                        stage('sub 3-2'){
                            steps { echo "This is sub parallel 3-2" }
                        }
                        stage('sub 3-3'){
                            steps { echo "This is sub parallel 3-3" }
                        }
                    }
                }
            }
        }
    }

}