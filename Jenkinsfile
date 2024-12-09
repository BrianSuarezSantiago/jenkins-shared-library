@Library('shared-library') _

//import com.ejemplo.MiClase

pipeline {
    agent any

    environment {
        //FRONTEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/node-hello.git'
        //BACKEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
        REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
        FOLDER_NAME = 'java-app' // '' Placeholder, will be completed dynamically
    }

    stages {
        stage('Build') {
            steps {
                script {
                    ejemplo.prepareStage()
                    dir("${FOLDER_NAME}") {
                        ejemplo.mavenBuildStage()
                        ejemplo.mavenDeployStage()
                    }
                }
            }
        }
    }
}
