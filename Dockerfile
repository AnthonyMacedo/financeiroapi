# Use a imagem base do OpenJDK
FROM openjdk:11-jre-slim

# Copie o arquivo JAR da pasta local para o contêiner
COPY /Desenvolvimento/Workspace_sts/financeiro-api/target/financeiro-api-0.0.1-SNAPSHOT.jar /app/financeiro-api-0.0.1-SNAPSHOT.jar

# Defina a pasta de trabalho para /app
WORKDIR /app

# Exponha a porta 8080 (se necessário)
EXPOSE 8080

# Comando para executar o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "financeiro-api-0.0.1-SNAPSHOT.jar"]