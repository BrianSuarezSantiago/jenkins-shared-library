//!package vars

// Define here all the global variables
class variables{
    // General configurations   
    static final String FRONTEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/simple-nodejs-app.git'
    static final String BACKEND_REPOSITORY_URL = 'https://github.com/BrianSuarezSantiago/java-app.git'
    static final String FRONT_FOLDER_NAME = 'java-app' //! '' Placeholder, will be completed dynamically
    static final String BACK_FOLDER_NAME = 'simple-nodejs-app'

    // Maven specific configurations
    static final String MVN_DOCKER_IMAGE_NAME = 'spring'

    // NPM specific configurations
    static final String NPM_DOCKER_IMAGE_NAME = 'node'

    // Docker specific configurations
    static final String DOCKER_IMAGE_TAG = 'latest'

    // AWS specific configurations
    static final String BUCKET_NAME = 'bucket-for-cicd-pipeline'
    static final String AWS_ACCESS_KEY_ID = credentials('aws-credentials')  // Name of the global credentials created on Jenkins
    static final String AWS_SECRET_ACCESS_KEY = credentials('aws-credentials')  // Name of the global credentials created on Jenkins
}
