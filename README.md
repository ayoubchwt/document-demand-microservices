# Document Demand Microservices

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Stripe](https://img.shields.io/badge/Stripe-008CDD?style=for-the-badge&logo=stripe&logoColor=white)
![Cloudinary](https://img.shields.io/badge/Cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
![Ngrok](https://img.shields.io/badge/Ngrok-1F1E1E?style=for-the-badge&logo=ngrok&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)

> Full-stack microservices backend built with Spring Cloud and Docker. Features a central API Gateway for routing, Netflix Eureka for service discovery, and a fully decoupled architecture. Includes a secure Stripe payment flow using webhooks and Cloudinary integration for cloud-based file management. The entire environment is containerized for one-command deployment.

---

## Architecture Overview

The system follows a distributed microservices architecture using Spring Cloud, fully containerized with Docker.

| Component | Responsibility |
|-----------|---------------|
| API Gateway | Single entry point for all client requests. Handles routing and load balancing via `lb://` |
| Eureka Server | Service registry for dynamic discovery of all microservices |
| Demand Service | Manages document requests and handles file uploads via Cloudinary |
| Payment Service | Processes payments via Stripe and handles incoming Stripe webhooks |
| MySQL Database | Automatically provisioned via Docker Compose |
| Ngrok | Exposes local services to the internet for Stripe webhook testing |

---

## Tech Stack

| Category | Technologies |
|----------|-------------|
| Backend | Java, Spring Boot 3.x, Spring Cloud |
| Database | MySQL 8.0 |
| DevOps | Docker, Docker Compose |
| Payments | Stripe API |
| File Storage | Cloudinary API |
| Documentation | Swagger / OpenAPI 3 |

---

## Getting Started

This project is fully Dockerized. No local Java, Maven, or MySQL installation required.

### Prerequisites

- Docker Desktop installed and running
- Git installed

---

### 1. Clone the Repository

```bash
git clone https://github.com/ayoubchwt/document-demand-microservices
cd document-demand-microservices
```

---

### 2. Environment Setup

Copy the example environment file and fill in your credentials:

```bash
cp .env.example .env
```

| Variable | Where to find it |
|----------|-----------------|
| `CLOUDINARY_CLOUD_NAME` | Cloudinary Dashboard |
| `CLOUDINARY_API_KEY` | Cloudinary Dashboard |
| `CLOUDINARY_API_SECRET` | Cloudinary Dashboard |
| `NGROK_AUTH_TOKEN` | Ngrok Dashboard |
| `STRIPE_KEY_SECRET` | Stripe Dashboard → Developers → API Keys |
| `STRIPE_WEBHOOK_SECRET` | Leave empty for now — configured in step 4 |

---

### 3. Run the Application

```bash
docker compose up -d --build
```

---

### 4. Configure the Stripe Webhook

Once the application is running, Ngrok will generate a public HTTPS URL that Stripe uses to deliver webhook events.

1. Open the Ngrok inspector at `http://localhost:4040`
2. Copy your public HTTPS URL (e.g. `https://abcd-1234.ngrok-free.app`)
3. Go to Stripe Dashboard → Developers → Webhooks → Add endpoint
4. Set the endpoint URL to:
   ```
   https://<your-ngrok-url>/payment/webhook
   ```
5. Reveal the signing secret (`whsec_...`) and add it to your `.env`:
   ```env
   STRIPE_WEBHOOK_SECRET=whsec_xxx
   ```
6. Restart the payment service to apply the new secret:
   ```bash
   docker compose restart payment-service
   ```

---

## Services and Ports

| Service | Port | Access |
|---------|------|--------|
| API Gateway | 8087 | `http://localhost:8087` |
| Swagger UI | 8087 | `http://localhost:8087/swagger-ui/index.html` |
| Eureka Dashboard | 8761 | `http://localhost:8761` |
| Demand Service | 8082 | Internal only |
| Payment Service | 8083 | Internal only |
| MySQL | 3306 | `localhost:3306` |
| Ngrok Inspector | 4040 | `http://localhost:4040` |

All client requests should go through the API Gateway on port 8087. The individual service ports are internal to the Docker network and not intended for direct access.

---

## Stopping the Application

```bash
docker compose down
```

---

## Notes

- All inter-service communication happens over the internal Docker network
- Credentials are managed exclusively through the `.env` file and never committed to version control
- Stripe webhooks require a running Ngrok instance to reach your local environment
- Each microservice is independently scalable

---

## Planned Improvements

- JWT / OAuth2 authentication and authorization
- CI/CD pipeline via Jenkins
- Kubernetes deployment manifests
- Centralized logging with Grafana
