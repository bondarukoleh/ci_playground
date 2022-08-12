pipeline {
	agent {
		docker {
      image 'node:10.17'
      label ''
      args  ''
    }
	}
	stages {
  	stage('Testing') {
  		steps {
    		shell("npm i; \n npm t;")
  		}
			post {
				always {
					/* JAVA_HOME should be available */
					allure([
        		includeProperties: false,
        		jdk: '',
        		properties: [],
        		reportBuildPolicy: 'ALWAYS',
        		results: [[path: 'allure-results']]
      		])
				}
			}
	  }
	}
}
