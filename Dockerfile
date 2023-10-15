FROM openjdk:11-jre-slim
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /target/financeiro-api-0.0.1-SNAPSHOT.jar financeiro-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","financeiro-api-0.0.1-SNAPSHOT.jar"]