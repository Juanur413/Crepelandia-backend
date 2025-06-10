# Usa Maven con JDK 17 preinstalado
FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Copia todos los archivos al contenedor
COPY . .

# Construye el proyecto (sin tests para velocidad)
RUN mvn clean package -DskipTests

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Ejecuta el JAR generado
CMD ["java", "-jar", "target/crepelandia-backend-1.0.0.jar.original"]
