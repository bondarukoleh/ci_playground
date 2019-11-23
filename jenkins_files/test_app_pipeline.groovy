node {
   def currentCommitId
   stage('Preparation') {
     checkout scm
     sh "git rev-parse --short HEAD > .git/currentCommitId"                        
     currentCommitId = readFile('.git/currentCommitId').trim()
   }

   stage('Test') {
     nodejs(nodeJSInstallationName: 'nodejs') {
       sh 'npm install'
       sh 'npm test'
     }
   }

   stage('Docker build/push') {
     /*https://index.docker.io/v1/ - It"s a dockerhub, 'dockerhub' - cred id that we have in jenkins*/
     docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
       docker.build("bondarukoleh/docker_test_app:${currentCommitId}", '.').push() /*. path where Docker file is*/
     }
   }
}