node {
   def currentCommitId
   stage('Preparation') {
     checkout scm
     sh "git rev-parse --short HEAD > .git/currentCommitId"
     currentCommitId = readFile('.git/currentCommitId').trim()
   }

try{
	stage('Test') {
     nodejs(nodeJSInstallationName: 'nodejs') {
       sh 'npm install'
       sh 'npm test'
     }
		 /*Send notification to slack*/
		slackSend (color: '#057d0b', message: "Test stage passed: '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
   }
} catch(e) {
		def name = env.JOB_NAME
		def num = env.BUILD_NUMBER
		def url = env.BUILD_URL
		slackSend (color: '#FF0000', message: "Failed Custom stage: '${name} [${num}]' (${url}) \n Error: ${e}")
}

  //  stage('Docker build/push') {
  //    /*https://index.docker.io/v1/ - It"s a dockerhub, 'dockerhub' - cred id that we have in jenkins*/
  //    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
  //      docker.build("bondarukoleh/docker_test_app:${currentCommitId}", '.').push() /*. path where Docker file is*/
  //    }
  //  }
}
