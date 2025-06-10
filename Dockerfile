# Usa una imagen oficial de OpenJDK
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia todo el proyecto y da permisos al wrapper
COPY . /app
RUN chmod +x mvnw

# Construye usando Maven sin test
RUN ./mvnw clean package -DskipTests

# Expone el puerto que usará Spring Boot
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-jar", "target/crepelandia-backend-1.0.0.jar.original"]
