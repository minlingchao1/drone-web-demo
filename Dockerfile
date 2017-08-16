FROM index.neunn.com/library/tomcat:7

MAINTAINER zhaozy@neunn.com

COPY ./target/webdemo.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]


