FROM amazoncorretto:17-alpine

# Variable para el directorio de la aplicación dentro del contenedor
ARG JAR_FILE=target/*.jar

# Copiar el archivo jar al directorio de la aplicación
COPY ./target/med-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8000
EXPOSE 8000

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]