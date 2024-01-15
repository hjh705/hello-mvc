FROM tomcat:9-jdk17
COPY target/hello-mvc.war /usr/local/tomcat/webapps/mvc.war
COPY src/main/resources/configs/Wallet_YH5J45K2SD3FKWR9 /Wallet_YH5J45K2SD3FKWR9