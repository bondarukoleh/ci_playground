job("Test Allure") {

  /* Software configuration management. User and email - are mandatory */
  scm {
    git("https://github.com/bondarukoleh/ci_playground") { node ->
      node / gitConfigName("Oleh Bondaruk DSL")
      node / gitConfigEmail("bondaruk9000@gmail.com")
    }
  }

  wrappers {
    /* this is the name of the NodeJS installation in Jenkins, Manage -> Configure Tools ->
     NodeJS installations -> Name */
    nodejs("nodejs")
  }

  steps {
    shell("npm i")
    shell("npm t")
  }
}
