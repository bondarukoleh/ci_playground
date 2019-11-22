node { /*in this example - any node, but here you should configure a slave, agent where you want to run*/
  def mvnHome

  stage('Preparation') { /* Like steps of SDLC e.g. clean build test publish */
    git ''
    mvnHome = tool 'M3'
  }

  stage('Build') {
    if(isUnix()){
      sh "'${mvnHome}/bin/mvn'" -DSome.package
    } else {
      bst(/"${mvnHome}\bin\mvn" -DSome.package/)
    }
  }

  stage('Result') {
    junit '**/target/TEST=*.xml'
    archive 'target/*.jar'
  }
}