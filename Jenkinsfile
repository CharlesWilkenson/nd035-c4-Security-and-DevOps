pipeline {
    agent any
    tools{
    maven "maven-3.6.3"
    }
    environment{
        registry = '469677591456.dkr.ecr.us-west-2.amazonaws.com/udapeapeople'
        registryCredential = 'jenkins-ecr-login-credentials'
        dockerimage = ''
    }

   stages {
        stage('clone repo'){
            steps{
           git branch: 'master', url: 'https://github.com/CharlesWilkenson/nd035-c4-Security-and-DevOps.git'
              }
        }

        stage('Build Artifact') {
            steps {
               sh "mvn clean package -DskipTests=true"
               archive 'target/*.jar'
            }
        }

        stage('Test Maven -JUnit'){
        steps{
           sh "mvn test"
        }

        }

    stage('SonarQube analysis') {
        steps{
            script{
           withSonarQubeEnv(credentialsId: 'Jenkins-sonar-token', installationName: 'Sonar-server') {
    //  sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
        sh 'mvn clean package sonar:sonar'
               sh 'mvn sonar:sonar'
           }
/*                       steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                   // waitForQualityGate abortPipeline: true
                   def qg = waitForQualityGate()
                   if(qg.status != 'OK'){
                       error "Pipeline aborted due to quality gate faillure: ${qg.status}"
                   }
                }
    }*/
        }  }
    }


        stage('Building the image'){
            steps{
                 script{
                // dockerImage = docker.build registry + ":$BUILD_NUMBER"
             //  dockerImage = docker.build registry + "/udapeapeople:latest"
              dockerImage = docker.build "udapeapeople:latest"
              }
            }
        }

        stage('Push the image to Amazon ECR'){
            steps{
                script{
                    docker.withRegistry("http://" + registry,  "ecr:us-west-2:" + registryCredential){
                    dockerImage.push()
                }
                }
            }
        }

        stage('Execute playbook to configure server'){
            steps{
            ansiblePlaybook credentialsId: 'private-key', disableHostKeyChecking: true, installation: 'Ansible', inventory: '/etc/ansible/hosts', playbook: '/opt/configure-server.yml', vaultTmpPath: ''
            }
        }

        stage('Execute playbook pull ans and run container'){
            steps{
              ansiblePlaybook credentialsId: 'private-key', disableHostKeyChecking: true, installation: 'Ansible', inventory: '/etc/ansible/hosts', playbook: '/opt/build-push-image.yaml', vaultTmpPath: ''
           }
        }

    }
}