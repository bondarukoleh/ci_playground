node {
  // if you want this stage failed if we cannot obtain info - put it in try catch and fail it
  stage('Get private repo last commit') {
    sshagent (credentials: ['git_private_key']) {
      // get the last commit id from a repository you own
      sh 'git ls-remote -h --refs git@github.com:bondarukoleh/test_private_repo.git master |awk "{print $1}"'
    }
  }
}
