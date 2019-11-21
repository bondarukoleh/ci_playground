FROM jenkins/jenkins
USER root

RUN mkdir -p /tmp/download && \
 curl -L https://download.docker.com/linux/static/stable/x86_64/docker-18.03.1-ce.tgz | tar -xz -C /tmp/download && \
 rm -rf /tmp/download/docker/dockerd && \
 mv /tmp/download/docker/docker* /usr/local/bin/ && \
 rm -rf /tmp/download && \
 groupadd -g 999 docker && \
 usermod -aG staff,docker jenkins

# We need groupadd -g 999 docker to add docker group
# We need usermod -aG staff,docker jenkins to add jenkins to docker group so it can execute docker images
# $>ls -ahl /var/run/docker.sock - this is the socket docker client file to speak to docker on machine
# srw-rw---- 1 root docker 0 nov 21 22:27 /var/run/docker.sock owned by root and in docker group as a jenkins also
# So when we exec to jenkins container - we can execute docker commands inside jenkins container

USER jenkins