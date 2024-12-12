@Library('shared-library') _

pipeline {
    agent any

    /*environment {
        FRONTEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/simple-nodejs-app.git'
        BACKEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
        FRONT_FOLDER_NAME = 'java-app' //! '' Placeholder, will be completed dynamically
        BACK_FOLDER_NAME = 'simple-nodejs-app'
        MVN_DOCKER_IMAGE_NAME = 'spring'
        NPM_DOCKER_IMAGE_NAME = 'node'
        DOCKER_IMAGE_TAG = 'latest'
        BUCKET_NAME = 'bucket-for-cicd-pipeline'
        AWS_ACCESS_KEY_ID = credentials('aws-credentials')
        AWS_SECRET_ACCESS_KEY = credentials('aws-credentials')
    }*/

    //! Establecer FOLDER_NAME dinámicamente (esto puede cambiar según el repositorio)
    //env.FOLDER_NAME = sh(script: "basename ${REPOSITORY_URL} .git", returnStdout: true).trim()

    stages {
        stage('Build') {
            steps {
                script {
                    //println Variables.BUCKET_NAME
                    def pipelineUtils = new com.tirea.jenkinsLib.PipelineUtils()
                    pipelineUtils.imprimir()
                    //pipelineUtils.prepareStage()

                    /*
                    ejemplo.prepareStage()
                    dir("${FRONT_FOLDER_NAME}") {
                        ejemplo.mavenBuildStage()
                        ejemplo.mavenPackageStage()
                        ejemplo.mavenDeployStage()
                    }

                    dir("${BACK_FOLDER_NAME}") {
                        ejemplo.npmBuildStage()
                        //ejemplo.npmPackageStage()
                        //ejemplo.npmDeployStage()                    
                    }*/
                }
            }
        }
    }
}
