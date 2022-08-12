pipeline {
	stages {
  	stage('Testing') {
  		steps {
    		shell("npm i; \n npm t;")
  		}
	  }
		post {
			allure([
        includeProperties: false,
        jdk: '',
        properties: [],
        reportBuildPolicy: 'ALWAYS',
        results: [[path: 'target/allure-results']]
      ])
		}
	}
}
