/*def call(String name = 'Mundo') {
    echo "Hola, ${name}!"
}*/


def prepareStage() {
    cleanWs()
    sh "git clone ${FRONTEND_REPOSITORY_URL}"
    sh "git clone ${BACKEND_REPOSITORY_URL}"
    sh "ls -l"
    sh "pwd"
    print("Repositories have been successfully cloned.")
    
    //! Establecer FOLDER_NAME dinámicamente (esto puede cambiar según el repositorio)
    //env.FOLDER_NAME = sh(script: "basename ${REPOSITORY_URL} .git", returnStdout: true).trim()
}

// Maven Projects
def mavenBuildStage() {
    sh 'mvn clean install'
    //! Integración con herramientas SonarQube, Fortify, IQServer
    sh 'echo "Build completed on $(date)"'
    // ! Docker
    cleanWs()
}

/*def mavenPackageStage() {
    //! configure deployment
    // Iniciar sesión en el clúster OpenShift (usando el token de acceso o kubeconfig)
    sh "oc login --token=${OPENSHIFT_TOKEN} --server=${OPENSHIFT_SERVER}"

    // Desplegar la imagen en el clúster de OpenShift
    sh "oc new-app ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} --name=${IMAGE_NAME}"

    // Exponer el servicio si es necesario
    sh "oc expose svc/${IMAGE_NAME}"

    print("Zipping Kubernetes configuration files...")
    sh "zip -r ${zipFileName} ${configDir}/*"
    echo "Kubernetes configuration files have been successfully zipped into ${zipFileName}"
}*/

def mavenDeployStage() {
    cleanWs()
}

// NPM Projects
def npmBuildStage() {
    sh '''
        npm install
        npm test
        echo "Build completed on $(date)"
    '''
}

def npmPackageStage() {
    sh 'npm run build'
    sh 'echo "Build completed on $(date)"'
}

def npmDeployStage() {
    cleanWs()
}
