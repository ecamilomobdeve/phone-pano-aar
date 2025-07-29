pipeline {
    agent any

    environment {
        // Artifactory credentials injected via Jenkins Credentials plugin
        ARTIFACTORY_USER     = credentials('ECAMILO.MOBDEVE@GMAIL.COM')
        ARTIFACTORY_PASSWORD = credentials('YrgUeREWcqjhYdMXKgEkUqtIxBAKQgRdYNzHNCWOHMwsTZVgWgeoBfvDwsOCayWhNSraHWgjhnVBgYsEoYTfSavYlZNGTxPMsFwFgOzuVvAAClQxlwhuGIcvpcLWMELpTsgWFeuoqqnkvwfhIKmRofepbuPaEdpejiuNwlatZINrCBQKOPcHtZUzsIlgnhmwLofjWYtPXrZrRJLZqdvfoQYNocjJAEzpHNvFmgSwkRXdspPQDXYiHTavqcBtgXFDswkGGyFaibzdibGtRPaYwxTlvRQYnWSyTpZIwwbyJZvImYQnlxQOlzfAeZpbvxuwkfxzkLoOcewwJRzTJLKDolfCqmSATlbPFsNquPZYhvrLPufaGzqJKwaxSelhAwjbFsfEcntelLeOGGzbbwCTjNSAoCMcfYQAbPqozFLADaDSDumfoSXCjBkcKuHQPklgMUZWCbJpdREFwdhKRrrpHKOYLJEwZxSMgGiSCErXTakrQcwVMTitNuyIceAwUYBSUmjgqVIydgrDLubMXKNqDHhHHFmQkDAEHNhbcVakHiJetrmPqpLaoSjTWtWKQhphZldJreAoOpKHcChRohgsXjNBxnzZpNqHEFLGRxgESQWvhjKRxDNvwlaeMBxSImJWnOiHFFEOXcCFepHZJjLdvdoxXdKBJQEjEVhBCieLUBfzGtvlFKJoKQOleNgB')
        ARTIFACTORY_URL      = 'https://demo.jfrog.io/artifactory'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Publish to JFrog') {
            steps {
                script {
                    def artifactoryRepo = (env.CHANGE_ID) ? "libs-snapshot-local" : "libs-release-local"
                    def versionSuffix = (env.CHANGE_ID) ? "-beta-${env.BUILD_NUMBER}" : ""

                    sh """
                    ./gradlew :mylibrary:publishReleasePublicationToMavenRepository \\
                        -PartifactoryRepo=${artifactoryRepo} \\
                        -PversionSuffix=${versionSuffix} \\
                        -Partifactory_contextUrl=${ARTIFACTORY_URL} \\
                        -Partifactory_user=${ARTIFACTORY_USER} \\
                        -Partifactory_password=${ARTIFACTORY_PASSWORD}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ AAR published to JFrog Artifactory"
        }
        failure {
            echo "❌ Failed to publish AAR"
        }
    }
}
