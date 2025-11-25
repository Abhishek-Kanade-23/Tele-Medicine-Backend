# Tele-Medicine-Backend
# Telemedicine Microservices Architecture — README

Below is a clean, production-ready README containing the ASCII architecture diagram, flow, endpoints, and recommendations.

---

## 📌 **System Architecture (Text-Based ASCII Diagram)**

```
                                 INTERNET / CLIENTS
                       (React SPA / Mobile App / Admin UI)
                                       |
                                       v
                             +----------------------+
                             |   API GATEWAY (GW)   |
                             |  - Global JWT Filter |
                             |  - Rate limiting     |
                             |  - Route & LB        |
                             +----------+-----------+
                                        |
        -----------------------------------------------------------------------
        |                     |                      |                      |
        v                     v                      v                      v
+---------------+     +---------------+      +----------------+     +--------------------+
|  AUTH SERVICE |     | APPOINTMENT   |      |  DOCTOR SVC    |     |  PATIENT SVC       |
| (auth-db)     |     | SERVICE       |      | (doctor-db)    |     | (patient-db)       |
| - users/roles |     | (appointment- |      | - profile      |     | - profile, history |
| - JWT issue   |     |  db)          |      | - schedule     |     |                    |
+-------+-------+     +-------+-------+      +--------+-------+     +---------+----------+
        |                     |                       |                        |
        |                     |    (sync / async)     | (sync checks)          |
        |   events (user.*)   |<--------------------->|<---------------------->|
        |-------------------->|                       |                        |
        |                     |                       |                        |
        v                     v                       v                        v
+--------------------+  +--------------------+   +--------------------+   +--------------------+
|  NOTIFICATION SVC  |  |  EVENT BUS (Kafka) |   |  EUREKA & CONFIG   |   |  MONITORING / LOGS |
|  (email / sms /    |  |  (topics:          |   |  (service registry |   |  (Prometheus / ELK) |
|   push)            |  |   appointment.*)    |   |   & centralized cfg)|   |  tracing: Jaeger    |
+--------------------+  +--------------------+   +--------------------+   +--------------------+
```

---

## 🔄 **Request Flow (Step-by-Step)**

### **1. Register / Login**

* Client → `POST /auth/register` or `POST /auth/login` → GW → Auth Service
* Auth Service writes to `auth-db` and returns:

  ```json
  { "userId": "...", "accessToken": "...", "refreshToken": "..." }
  ```

### **2. Create User Profile (Two-Step Flow)**

* After login:

  * Doctor: `POST /doctor/profile`
  * Patient: `POST /patient/profile`
* GW injects:

  * `X-User-Id`
  * `X-Roles`

### **3. Booking Appointments**

* Client → `POST /appointments/book`
* GW validates JWT → forwards → Appointment Service
* Appointment Service:

  * Checks doctor availability (sync to Doctor Svc)
  * Writes to `appointment-db`
  * Publishes `appointment.created` event

### **4. Notification Handling**

* Notification Service listens on `appointment.*` topics
* Sends email / SMS / push alerts

### **5. Reschedule / Cancel**

* Client → GW → Appointment Service
* Appointment Service updates DB & sends event → Notification Svc notifies

### **6. Service Discovery & Config**

* All services register with Eureka
* Config Server distributes configuration from Git

---

## 🔐 **Security Contract (Gateway → Services)**

Gateway performs **full JWT validation** and injects identity headers:

```
X-User-Id: <userId>
X-Roles: <comma-separated roles>
X-Trace-Id: <traceId>
```

### Services should only trust these headers if:

* Internal network only, or
* mTLS enabled, or
* Signed internal headers

### Token Storage Recommendation

* Access token → short-lived (5–15 min)
* Refresh token → HttpOnly Secure SameSite cookie

---

## ⚙️ **Microservice Endpoints Summary**

### **Auth Service**

* `POST /auth/register`
* `POST /auth/login`
* `POST /auth/refresh`

### **Doctor Service**

* `POST /doctor/profile`
* `GET /doctor/{userId}`
* `GET /doctor/{id}/slots`

### **Patient Service**

* `POST /patient/profile`
* `GET /patient/{userId}`

### **Appointment Service**

* `POST /appointments/book`
* `POST /appointments/cancel`
* `GET /appointments/doctor/{doctorId}`
* `GET /appointments/patient/{patientId}`

---

## 📦 **Design Decisions**

* Auth service stores **only users & roles**, nothing else.
* Doctor/Patient profiles live in their own services.
* Appointment Service owns availability + scheduling logic.
* Events (Kafka/RabbitMQ) used for notifications & decoupling.
* Use Resilience4j for retries, timeouts, circuit breakers.

---

## ✔️ This README is ready for use

You can directly copy this into your GitHub project.
If you want, I can also generate:

* A **GitHub-ready README with emojis & badges**
* A **draw.io architecture diagram**
* A **PNG / SVG diagram file**

Just tell me what format you want next!
Chnages