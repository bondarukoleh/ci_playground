node {
	def to = emailextrecipients([
    [$class: 'CulpritsRecipientProvider'], // manager that need to be notified
    [$class: 'DevelopersRecipientProvider'], // developers that made commit
    [$class: 'RequesterRecipientProvider'] // if job was run manually - who press the button
  ])

  def currentCommitId
  stage('Preparation') {
    checkout scm /*only if pipeline script pulled from vsc or using multi bramnes*/
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
	 /*after commands executed - container destroyed, and next stage goes*/
  }

  stage('Test with a DB') {
	 /*run - does return a container that can be stopped*/
    def mysql = docker.image('mysql').run("-e MYSQL_ALLOW_EMPTY_PASSWORD=yes")
    def myTestContainer = docker.image('node:10.17')
    myTestContainer.pull()
	 /*Inside - executes commands inside a container, and in workspace of agent
	 means it volumes a jenkins job workspace and run them in this workspace
	 kills a container automatically, so you don't need to bother about it*/
    myTestContainer.inside("--link ${mysql.id}:mysql") { // using linking, mysql will be available at host: mysql, port: 3306
         sh 'npm i --no-audit'
         sh 'npm t'
    }
    mysql.stop()
  }
	try {
		stage('Use custom image') { //To run your custom image
    	docker.withRegistry('https://registry.example.com') {
        docker.image('my-custom-image').inside {
            sh 'make test'
        }
    	}
		}
	} catch(e) {
		// mark build as failed
    // currentBuild.result = "FAILURE";
    // set variables
    def subject = "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} ${currentBuild.result}"
    def content = '${JELLY_SCRIPT,template="html"}' /*JELLY_SCRIPT - from extention*/

    // send email
    if(to != null && !to.isEmpty()) {
      // emailext(body: content, mimeType: 'text/html',
      //    replyTo: '$DEFAULT_REPLYTO', subject: subject, /*Reply To List in Jenkins config*/
      //    to: to, attachLog: true )
    }

    // mark current build as a failure and throw the error
    // throw e; /*or we can make this stage optional and not failing it*/
	}


  stage('Docker build/push') {
    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
      def app = docker.build("bondarukoleh/docker_test_app:${currentCommitId}", '.').push()
    }
  }
}
