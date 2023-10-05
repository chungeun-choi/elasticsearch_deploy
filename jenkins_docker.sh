docker run -d -it -v $(which docker):/usr/bin/docker -p 8090:8080 --name jenkins jenkins/jenkins:lts
docker exec -it -u root jenkins bash
chown jenkins:jenkins /usr/bin/docker