node {
  stage('Get private repo last commit') {
    sshagent (credentials: ['git_private']) {
      // get the last commit id from a repository you own
      sh 'git ls-remote -h --refs git@github.com:bondarukoleh/test_private_repo.git master |awk "{print $1}"'
    }
  }
}
