@Library('shared-library') _

import com.ejemplo.MiClase

pipeline {
    agent any
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
