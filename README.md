# forge-cdbookstore
JBoss Forge tutorial (CD Bookstore), running Arquillian tests on remote Wildfly/docker container

If you're new to Forge, start with the [Forge Hands on Lab] (http://forge.jboss.org/document/hands-on-lab) 

#### Fire up a Wildfly container(eg Wildfly Camel)

docker run --rm -ti -p 9990:9990 -p 8080:8080 -e WILDFLY_MANAGEMENT_USER=admin -e WILDFLY_MANAGEMENT_PASSWORD=admin wildflyext/wildfly-camel

#### mvn install

#### Open your browser at http://$DOCKER_IP and play around with the CRUD stuff, eg
 
    http://192.168.99.100:8080/cdbookstore

