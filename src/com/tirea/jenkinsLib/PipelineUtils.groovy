package com.tirea.jenkinsLib

import vars.Variables

class PipelineUtils {

    // Clone and configure step
    def prepareStage() {
        cleanWs()
        sh """
            git clone ${Variables.FRONTEND_REPOSITORY_URL}
            git clone ${Variables.BACKEND_REPOSITORY_URL}
            echo "Repositories have been successfully cloned"
        """
    }

    // Checks if the specified S3 bucket exists or not
    def checkS3BucketExists(bucketName) {
        try {
            def result = sh(script: "aws s3api head-bucket --bucket ${bucketName} 2>/dev/null", returnStatus: true)

            if (result == 0) {
                echo "The bucket '${bucketName}' exists."
                return true
            } else {
                echo "The bucket '${bucketName}' does not exist."
                return false
            }
        } catch (Exception exception) {
            echo "Error while checking the bucket: ${exception.message}"
            return false
        }
    }

    // Build, testing, code quality and containerization for Maven projects
    def mavenBuildStage() {
        sh """
            mvn clean install
            echo "Build completed on $(date)"
        """

        writeFile file: 'Dockerfile', text: """
            FROM openjdk:11-jre-slim
            WORKDIR /app
            COPY target/*.jar app.jar
            ENTRYPOINT ["java", "-jar", "app.jar"]
        """
        sh """
            docker build -t ${Variables.MVN_DOCKER_IMAGE_NAME}:${Variables.DOCKER_IMAGE_TAG} .
            echo "Docker image ${Variables.MVN_DOCKER_IMAGE_NAME}:${Variables.DOCKER_IMAGE_TAG} built successfully"
        """
    }

    // Configure deployment destination and save workspace configuration
    def mavenPackageStage() {
        sh """
            mkdir -p maven_output
            cp target/*.jar Dockerfile maven_output
        """
    }

    // Upload backend to ROSA cluster
    def mavenDeployStage() {
        sh """
            aws s3 sync maven_output/ s3://${Variables.BUCKET_NAME}/
            echo "Successfully uploaded to ${Variables.BUCKET_NAME} on $(date)"
        """
        cleanWs()
    }

    // Build, testing, code quality, and containerization for NPM projects
    def npmBuildStage() {
        sh """
            npm install
            echo "Build completed on $(date)"
        """

        writeFile file: 'Dockerfile', text: """
            FROM node:18-alpine
            WORKDIR /app
            COPY . .
            RUN npm install --production
            CMD ["npm", "start"]
        """
        sh """
            docker build -t ${Variables.NPM_DOCKER_IMAGE_NAME}:${Variables.DOCKER_IMAGE_TAG} .
            echo "Docker image ${Variables.NPM_DOCKER_IMAGE_NAME}:${Variables.DOCKER_IMAGE_TAG} built successfully"
        """
    }

    // Configure deployment destination and save workspace configuration
    def npmPackageStage() {
        sh """
            mkdir -p npm_output
            cp -r dist/ package.json Dockerfile npm_output
        """

        // Check if the S3 bucket exists
        checkS3BucketExists("${Variables.BUCKET_NAME}")
    }

    // Upload frontend to S3 bucket
    def npmDeployStage() {
        sh """
            aws s3 sync npm_output/ s3://${Variables.BUCKET_NAME}/
            echo "Successfully uploaded to ${Variables.BUCKET_NAME} on $(date)"
        """
        cleanWs()
    }
}
