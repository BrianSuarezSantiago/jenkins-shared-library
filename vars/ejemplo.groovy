def prepareStage() {
    cleanWs()
    sh '''
        git clone ${FRONTEND_REPOSITORY_URL}
        git clone ${BACKEND_REPOSITORY_URL}
    '''
    print("Repositories have been successfully cloned.")

    //! Establecer FOLDER_NAME dinámicamente (esto puede cambiar según el repositorio)
    //env.FOLDER_NAME = sh(script: "basename ${REPOSITORY_URL} .git", returnStdout: true).trim()
}

// Maven Projects
def mavenBuildStage() {
    // mvn clean install -DskipTests
    // mvn package -DskipTests -DoutputDirectory=$(pwd)
    sh '''
        mvn clean install
        echo Build completed on $(date)
    '''
    
    // Crear un Dockerfile dinámicamente
    writeFile file: 'Dockerfile', text: '''
        FROM openjdk:11-jre-slim
        WORKDIR /app
        COPY target/*.jar app.jar
        ENTRYPOINT ["java", "-jar", "app.jar"]
    '''
    sh 'echo "Dockerfile created successfully on $(date)"'

    // Construcción de la imagen Docker
    sh '''
        echo Building Docker image...
        docker build -t ${MVN_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .
        echo "Docker image ${MVN_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} built successfully."
    
        ls -l
        pwd
    '''
    //! Integración con herramientas SonarQube, Fortify, IQServer
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
    print("deploy stage")
    cleanWs()
}

// NPM Projects
def npmBuildStage() {
    sh '''
        npm install
        npm test
        echo Build completed on $(date)
    '''

    // Crear un Dockerfile dinámicamente
    writeFile file: 'Dockerfile', text: '''
        FROM openjdk:11-jre-slim
        WORKDIR /app
        COPY target/*.jar app.jar
        ENTRYPOINT ["java", "-jar", "app.jar"]
    '''
    sh 'echo "Dockerfile created successfully on $(date)"'

    // Construcción de la imagen Docker
    sh '''
        echo Building Docker image...
        
        echo "Docker image ${NPM_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} built successfully."
    
        ls -l
        pwd
    '''
    //docker build -t ${NPM_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .
    //! Integración con herramientas SonarQube, Fortify, IQServer
    cleanWs()
}

def npmPackageStage() {
    sh '''
        npm run build
        echo Build completed on $(date)
    '''
}

def npmDeployStage() {
    cleanWs()
}
