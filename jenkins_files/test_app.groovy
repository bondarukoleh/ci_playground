job("Node_test_app") {

  /* Software configuration management. User and email - are mandatory */
  scm {
    git("https://github.com/bondarukoleh/ci_playground") { node -> 
      node / gitConfigName("Oleh Bondaruk DSL")
      node / gitConfigEmail("bondaruk9000@gmail.com")
    }
  }

  /* Pull the scm every 5 minutes - if ther was a change - rebuild */
  triggers {
    scm("H/5 * * * *")
  }

  wrappers {
    /* this is the name of the NodeJS installation in Jenkins, Manage -> Configure Tools ->
     NodeJS installations -> Name */
    nodejs("nodejs")
  }

  steps {
    shell("echo Hello! This is a step created with dsl language.; \n cd ./src; \n npm install;")
  }
}