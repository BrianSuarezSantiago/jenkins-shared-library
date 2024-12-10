// Clone and configure step
def prepareStage() {
    cleanWs()
    sh '''
        git clone ${FRONTEND_REPOSITORY_URL}
        git clone ${BACKEND_REPOSITORY_URL}
        echo "Repositories have been successfully cloned"
    '''
}

def checkS3BucketExists(bucketName) {
    try {
        def result = sh(script: "aws s3api head-bucket --bucket ${bucketName} 2>/dev/null", returnStatus: true)

        if (result == 0) {
            echo "El bucket '${bucketName}' existe."
            return true
        } else {
            echo "El bucket '${bucketName}' no existe."
            return false
        }
    } catch (Exception exception) {
        echo "Error al verificar el bucket: ${exception.message}"
        return false
    }
}

// Build, testing, code quality and containerization
def mavenBuildStage() {
    //! mvn package -DskipTests -DoutputDirectory=$(pwd)
    sh '''
        mvn clean install
        echo "Build completed on $(date)"
    '''

    //! Integración con herramientas SonarQube, Fortify, IQServer

    writeFile file: 'Dockerfile', text: '''
        FROM openjdk:11-jre-slim
        WORKDIR /app
        COPY target/*.jar app.jar
        ENTRYPOINT ["java", "-jar", "app.jar"]

        echo "Dockerfile created successfully on $(date)"
        echo "Docker image ${MVN_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} built successfully."
    '''
    //sh 'docker build -t ${MVN_DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} .'

    // ! Pushear imagen

    // ! Subida de la imagen a Nexus
    //cleanWs()
}

// Configure deployment destination and save workspace configuration
def mavenPackageStage() {
    // Iniciar sesión en el clúster OpenShift (usando el token de acceso o kubeconfig)
    //sh "oc login --token=${OPENSHIFT_TOKEN} --server=${OPENSHIFT_SERVER}"

    // Desplegar la imagen en el clúster de OpenShift
    //sh "oc new-app ${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} --name=${IMAGE_NAME}"

    // Exponer el servicio si es necesario
    //sh "oc expose svc/${IMAGE_NAME}"

    //print("Zipping Kubernetes configuration files...")
    //sh "zip -r ${zipFileName} ${configDir}/*"
    //echo "Kubernetes configuration files have been successfully zipped into ${zipFileName}"

    // Check if the S3 bucket exists
    checkS3BucketExists("${BUCKET_NAME}")

    // Prepare output to upload to ROSA
    sh '''
        mkdir -p maven_output
        cp target/*.jar Dockerfile maven_output
    '''
}

// Upload frontend and backend
def mavenDeployStage() {
    //! check --delete flag option for aws sync command
    sh '''
        aws s3 sync maven_output/ s3://${BUCKET_NAME}/
        echo "Successfully loaded into ${BUCKET_NAME} on $(date)"
    '''
    cleanWs()

    sh 'ls -l'
}

/*def npmBuildStage() {
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
*/