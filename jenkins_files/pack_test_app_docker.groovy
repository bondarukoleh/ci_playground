job("Build_and_pack_test_app_docker") {

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
    /* Documentation about the flags we can get from 
    https://jenkinsci.github.io/job-dsl-plugin/#method/javaposse.jobdsl.dsl.helpers.step.StepContext.dockerBuildAndPublish
     */
    dockerBuildAndPublish {
      repositoryName('bondarukoleh/docker_test_app')
      tag('${GIT_REVISION,length=9}')
      registryCredentials('dockerhub')
      forcePull(false)
      forceTag(false)
      createFingerprints(false)
      skipDecorate()
    }
  }
}