node {
   def currentCommitId
   stage('Preparation') {
     checkout scm
     sh "git rev-parse --short HEAD > .git/currentCommitId"
     currentCommitId = readFile('.git/currentCommitId').trim()
   }

   stage('Testing') {
     def myTestContainer = docker.image('node:10.17') /*easy way to manage versions*/
     myTestContainer.pull() /*If there any cashed image, we say that we wannt latest*/
     myTestContainer.inside {
       sh 'npm i --no-audit'
       sh 'npm t'
     }
		 /*asfter commands executed - container destroyed, and next stage goes*/
   }

   stage('Test with a DB') {
     def mysql = docker.image('mysql').run("-e MYSQL_ALLOW_EMPTY_PASSWORD=yes")
     def myTestContainer = docker.image('node:10.17')
     myTestContainer.pull()
     myTestContainer.inside("--link ${mysql.id}:mysql") { // using linking, mysql will be available at host: mysql, port: 3306
          sh 'npm i --no-audit'
          sh 'npm t'
     }
     mysql.stop()
   }

	/* stage('Use custom image') { //To run your custom image
		 	checkout scm
  	   docker.withRegistry('https://registry.example.com') {
  	       docker.image('my-custom-image').inside {
  	           sh 'make test'
  	       }
  	   }
		 } */

   stage('docker build/push') {
     docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
       def app = docker.build("bondarukoleh/docker_test_app:${currentCommitId}", '.').push()
     }
   }
}
