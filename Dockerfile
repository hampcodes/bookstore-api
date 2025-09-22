# Imagen base con Java 21
FROM eclipse-temurin:21-jdk AS build
# Copiar código al contenedor
WORKDIR /app
COPY . .
# Construir la aplicación (usa Maven Wrapper si lo tienes)
RUN ./mvnw clean package -DskipTests

# -------- Imagen final --------
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Copiar JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponer el puerto
EXPOSE 8080
# Ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]