@Library('shared-library') _

pipeline {
    agent any
    stages {
        stage('Prueba') {
            steps {
                // ! solo para uso de funciones globales
                //ejemplo('carlos')
                // ejemplo.call()

                script {
                    ejemplo.prepareStage()
                    ejemplo.mavenBuildStage()
                }
                
            }
        }
    }
}
