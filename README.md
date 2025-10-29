# Checkout API-first Service
=======

## About
A lightweight **checkout service** built with **Java (Spring Boot)**, following clean architecture principles and documented with an **OpenAPI Specification**.  
It demonstrates a modular approach to cart, checkout, payment, and order management with in-memory persistence for easy demo and testing.

---

## ✨ Features
- **Cart Management** – create carts, add items, view contents
- **Checkout Flow** – compute totals with coupons, tax, shipping
- **Order Lifecycle** – track order states (`AwaitingPayment`, `Paid`, `Failed`)
- **Payment Providers** – configurable providers (`MockPay`, `FailPay`, `CashOnDelivery`)
- **API-First** – documented with an **OpenAPI Specification (openapi.yaml)**
- **In-Memory Persistence** – quick demo setup, easily swappable for a database

---

## Project structure
```
checkout-service/
├─ src/main/
│  ├─ java/com/checkoutservice/
│  │  ├─ api/			# API Layer (Controllers / REST endpoints)
│  │  ├─ app/       	# Application Layer (Orchestration)
│  │  ├─ domain/		# Core business logic
│  │  │  ├─ cart/
│  │  │  ├─ order/
│  │  │  ├─ pricing/
│  │  │  └─ payment/
│  │  └─ persistence/	# Persistance Layer (Infra / Repository contracts)
│  │
├─ pom.xml
└─ README.md
```

## Build
```
mvn clean package
```
