@Library('shared-library') _

//import com.ejemplo.MiClase

pipeline {
    agent any

    environment {
        FRONTEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/node-hello.git'
        BACKEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
        FRONT_FOLDER_NAME = 'java-app' //! '' Placeholder, will be completed dynamically
        BACK_FOLDER_NAME = 'node-hello'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    ejemplo.prepareStage()
                    dir("${FRONT_FOLDER_NAME}") {
                        ejemplo.mavenBuildStage()
                        ejemplo.mavenDeployStage()
                    }

                    dir("${BACK_FOLDER_NAME}") {
                        ejemplo.npmBuildStage()
                        ejemplo.npmDeployStage()                    
                    }
                }
            }
        }
    }
}
