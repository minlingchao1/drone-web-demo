FROM index.neunn.com/library/tomcat:7

MAINTAINER zhaozy@neunn.com

RUN mkdir -p /usr/local/tomcat/webapps/site && \
    mkdir -p /usr/local/tomcat/webapps/cobertura

COPY ./target/site /usr/local/tomcat/webapps/site

COPY ./target/cobertura /usr/local/webapps/cobertura

COPY ./target/webdemo.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]


