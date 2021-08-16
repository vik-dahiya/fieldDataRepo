FROM openjdk:16
EXPOSE 9900
ADD target/field-data-api.jar field-data-api.jar
ENTRYPOINT [ "java","-jar","field-data-api.jar" ]
