# BankApp

API REST que simula operaciones bancarias y lógica de cajero automático: transacciones seguras, control de estados de sesión y bloqueo de cuentas.

🔗 **Documentación / probar la API (Swagger):** [bankapp-tndg.onrender.com/swagger-ui](https://bankapp-tndg.onrender.com/swagger-ui/index.html)

## Tecnologías

- Java 17
- Spring Boot
- Swagger / OpenAPI
- Docker
- Desplegado en Render

## Funcionalidades

- Simulación de operaciones bancarias (consignaciones, retiros, transferencias)
- Manejo de estados de sesión
- Bloqueo de cuentas por intentos fallidos o reglas de negocio
- Documentación interactiva de todos los endpoints vía Swagger

## Cómo ejecutarlo localmente

```bash
git clone https://github.com/King-911/BankApp.git
cd BankApp
# Compilar y ejecutar con Maven
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080` y la documentación Swagger en `http://localhost:8080/swagger-ui/index.html`.

### Con Docker

```bash
docker build -t bankapp .
docker run -p 8080:8080 bankapp
```

## Autor

Stewart Leon Gil — [GitHub](https://github.com/King-911) · [LinkedIn](https://www.linkedin.com/in/stewartleon87)
