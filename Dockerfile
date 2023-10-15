# Estágio 1: Construa o aplicativo usando a imagem Maven
FROM maven:3.8.5-openjdk-11 AS build

# Defina o diretório de trabalho para a raiz do aplicativo no contêiner
WORKDIR /app

# Copie o arquivo pom.xml e os arquivos de código-fonte
COPY pom.xml .
COPY src src

# Execute o comando Maven para compilar e empacotar o aplicativo
RUN mvn clean package -DskipTests

# Estágio 2: Crie uma imagem mínima para executar o aplicativo
FROM openjdk:11-jre-slim

# Copie o arquivo JAR gerado do estágio anterior para o contêiner
COPY --from=build /app/target/financeiro-api-0.0.1-SNAPSHOT.jar /app/financeiro-api-0.0.1-SNAPSHOT.jar

# Defina a pasta de trabalho para /app
WORKDIR /app

# Exponha a porta 8080, se necessário
EXPOSE 8080

# Comando para executar o aplicativo quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "financeiro-api-0.0.1-SNAPSHOT.jar"]
