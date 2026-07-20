FROM eclipse-temurin:21-jdk-alpine

# Instalar Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copiar código fuente y archivos de Maven
COPY . .

# Compilar la librería (omitir tests para acelerar)
RUN mvn clean package -DskipTests

# Ejecutar el ejemplo (cambia la clase por la que corresponda)
CMD ["java", "-jar", "target/Notification-Library-1.0-SNAPSHOT.jar", "pe.com.example.Main"]