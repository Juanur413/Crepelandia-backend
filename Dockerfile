# Imagen base con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 as build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el proyecto
COPY . .

# Construir el proyecto con Maven, generando el .jar ejecutable
RUN mvn clean package -DskipTests

# -----------------------------
# Segunda etapa para ejecución
# -----------------------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el .jar generado desde la etapa de compilación
COPY --from=build /app/target/crepelandia-backend-1.0.0.jar .

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de ejecución
CMD ["java", "-jar", "crepelandia-backend-1.0.0.jar"]
