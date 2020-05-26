FROM openjdk:8
ADD target/articles-mysql.jar articles-mysql.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "articles-mysql.jar"]