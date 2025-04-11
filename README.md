# 🧱 Proyecto con Scaffold Clean Architecture Gradle Plugin

Este proyecto implementa una arquitectura limpia basada en el plugin Scaffold Clean Architecture para Gradle, e integra servicios como Redis, PostgreSQL y AWS Localstack para pruebas locales.

## 🧰 Requisitos Previos

- Docker y Docker Compose
- AWS CLI configurado
- Java 17+
- Gradle
- Localstack CLI (opcional)

---

## 🚀 Levantar Infraestructura Local

Para levantar los servicios de Redis, PostgreSQL y Localstack, navega a la carpeta `localstack` y ejecuta:

```bash
docker-compose up -d
```

---

## 📬 Crear Cola en SQS (Localstack)

Asegúrate de tener configurado el CLI de AWS apuntando a Localstack, y luego ejecuta el siguiente comando para crear una cola de SQS:

```bash
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name usuarios-creados
```

---

## 🧾 Crear Tabla en DynamoDB (Localstack)

Para crear la tabla `UsersTable` en DynamoDB, usa el siguiente comando:

```bash
aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name UsersTable \
  --attribute-definitions AttributeName=id,AttributeType=N \
  --key-schema AttributeName=id,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST
```

---


## 🛠️ Herramientas y Tecnologías

- Spring Boot
- Java 17
- DynamoDB (Localstack)
- Redis (Docker)
- PostgreSQL (Docker)
- AWS SQS (Localstack)
- Scaffold Gradle Plugin

---

## 🧪 Pruebas Locales

Puedes utilizar [Localstack](https://docs.localstack.cloud/) para emular los servicios de AWS de forma local. La conexión a servicios como SQS o DynamoDB debe apuntar al endpoint:

```
http://localhost:4566
```

