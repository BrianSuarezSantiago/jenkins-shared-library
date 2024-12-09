@Library('shared-library') _

//import com.ejemplo.MiClase

pipeline {
    agent any

    environment {
        REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
        FOLDER_NAME = 'java-app' // '' Placeholder, will be completed dynamically
    }

    stages {
        stage('Prueba') {
            steps {
                script {
                    // ! solo para uso de funciones globales sin clases
                    //ejemplo('carlos')
                    // ejemplo.call()
                    ejemplo.prepareStage()
                    //ejemplo.mavenBuildStage()
                }
            }
        }
    }
}
