# API Gateway – MatchWork

Este microservicio actúa como puerta de entrada unificada para todos los servicios del ecosistema MatchWork. Se encarga del enrutamiento de solicitudes, validación de tokens JWT y manejo de CORS, proporcionando seguridad y centralización del tráfico hacia los microservicios internos.

## Tecnologías
- Spring Boot 3
- Spring Cloud Gateway
- JWT (seguridad)
- AWS EC2
- Docker

## Requisitos
- Java 17 o superior
- Maven 3.8+
- Docker
- Instancia EC2 (o entorno local)
- Microservicios desplegados con sus IPs públicas

## Ejecución local

```bash
# Clonar repositorio
git clone https://github.com/DarioOlivares2001/apigateway.git
cd apigateway

# Compilar y ejecutar
./mvnw spring-boot:run


# application.yml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: http://54.90.98.100:8083
        - id: job-service
          uri: http://35.174.51.1:8081
        - id: chat-service
          uri: http://34.193.228.160:8082
  cors:
    allowed-origins: "https://matchwork-wo14.vercel.app"


Despliegue en Producción
AWS EC2 – IP: 52.207.17.209

Puerto: 8080

Expone rutas seguras para todos los servicios internos

Seguridad
Validación de JWT en cada request

Soporte para roles: empresa, profesional

Configurado con filtros personalizados
