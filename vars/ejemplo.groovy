/*def call(String name = 'Mundo') {
    echo "Hola, ${name}!"
}*/


def prepareStage() {
    //sh "git clone ${FRONTEND_REPOSITORY_URL}"
    //sh "git clone ${BACKEND_REPOSITORY_URL}"
    print("Repositories have been successfully cloned.")
    
    //! Establecer FOLDER_NAME dinámicamente (esto puede cambiar según el repositorio)
    //env.FOLDER_NAME = sh(script: "basename ${REPOSITORY_URL} .git", returnStdout: true).trim()
    
    // Determine the type of project (Maven or NPM) based on the presence of the key files
    def projectType = detectProjectType()

    if (projectType == 'maven') {
        dir("${FOLDER_NAME}") { // !
            mavenBuildStage()
            mavenPackageStage()
            mavenDeployStage()
        }
    } else if (projectType == 'npm') {
        dir("${FOLDER_NAME}") {
            npmBuildStage()
            npmPackageStage()
            npmDeployStage()
        }
    }   
}

// Detects project type (Maven or NPM) based on key files
def detectProjectType() {
    if(fileExists("pom.xml")) {
        return 'maven'
    } else if(fileExists("package.json")) {
        return 'npm'
    } else {
        error "No project type detected. Make sure the repository contains a pom.xml or package.json."
    }
}

// Maven Projects
def mavenBuildStage() {
    sh 'mvn clean install'
    sh "mvn package"
    //! Integración con herramientas SonarQube, Fortify, IQServer
    sh 'echo "Build completed on $(date)"'
    cleanWs()
    sh "ls -l"
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
    sh "ls -l"
}

// NPM Projects
def npmBuildStage() {
    sh 'npm install'
    sh 'npm test'
    sh 'echo "Build completed on $(date)"'
}

def npmPackageStage() {
    sh 'npm run build'
    sh 'echo "Build completed on $(date)"'
}

def npmDeployStage() {
    cleanWs()
    sh "ls -l"
}
