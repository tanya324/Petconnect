# 🐾 PetConnect — Smart Pet Adoption & Matching Web Application

> A modern, game-like Java web application for pet adoption featuring AI-powered matching,
> medical records, animated characters, and a full admin dashboard.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-teal?style=flat-square)

---

## ✨ Features

| Feature | Description |
|---|---|
| 🏠 Pet Adoption | Browse, filter, and adopt pets by type/breed/health |
| ✨ Smart Matching | AI algorithm calculates pet-owner compatibility score |
| 🏥 Medical Records | Vaccination tracker, vet appointments, history |
| ➕ Pet Enrollment | Shelter staff can add new pets with images |
| 🎮 Animated Characters | Clickable dog, cat, vet, staff characters with speech |
| 🔐 Auth & Roles | Login, register, role-based access (User/Admin) |
| 🛡️ Admin Dashboard | Full CRUD, stats, user/pet/adoption management |
| 📱 Responsive | Mobile-first Bootstrap layout |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/petconnect.git
cd petconnect
```

### 2. Set Up MySQL
```sql
CREATE DATABASE petconnect;
```

### 3. Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petconnect
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

### 5. Open in Browser
```
http://localhost:8080
```

### Demo Credentials
| Role | Email | Password |
|---|---|---|
| Admin | admin@petconnect.com | admin123 |
| User | arjun@example.com | password |

---

## 🗂️ Project Structure

```
petconnect/
├── src/main/java/com/petconnect/
│   ├── config/          # SecurityConfig, DataSeeder
│   ├── controller/      # Page + REST API controllers
│   ├── model/           # JPA Entity classes
│   ├── repository/      # Spring Data JPA interfaces
│   └── service/         # Business logic + matching algorithm
├── src/main/resources/
│   ├── templates/       # Thymeleaf HTML pages
│   └── static/
│       ├── css/style.css
│       └── js/main.js
└── pom.xml
```

---

## 📡 REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/pets` | Get all pets (filterable) |
| GET | `/api/pets/{id}` | Get pet by ID |
| GET | `/api/pets/search?q=` | Search pets |
| POST | `/api/pets` | Add new pet |
| PUT | `/api/pets/{id}` | Update pet |
| DELETE | `/api/pets/{id}` | Delete pet |
| POST | `/api/match` | Calculate pet matches |
| GET | `/api/appointments` | List appointments |
| POST | `/api/appointments` | Book appointment |
| GET | `/api/medical/pet/{id}` | Get medical records |
| POST | `/api/medical` | Add medical record |

---

## 🗄️ Database Schema

```sql
users (id, name, email, password, phone, role, registered_at)
shelters (id, name, city, address, phone, email, capacity)
pets (id, name, type, breed, age, gender, health_condition,
      vaccination_status, description, status, enrolled_date,
      shelter_id, image_url)
adoptions (id, pet_id, user_id, adoption_date, status, notes)
medical_records (id, pet_id, condition, treatment, vet_name,
                 record_date, next_visit)
appointments (id, pet_id, user_id, appointment_date,
              appointment_time, treatment_type, doctor_name, status)
matching_profiles (id, user_id, living_space, lifestyle,
                   allergies, experience, budget)
```

---

## ☁️ Deployment

### Railway (Backend + MySQL)
1. Push to GitHub
2. Connect repo on [railway.app](https://railway.app)
3. Add MySQL service
4. Set environment variables from application.properties
5. Deploy!

### Render
1. Create Web Service → connect GitHub
2. Build command: `mvn clean package -DskipTests`
3. Start command: `java -jar target/petconnect-1.0.0.jar`

---

## 📄 Resume Description

> **PetConnect — Smart Pet Adoption Web Application** | Java · Spring Boot · MySQL
>
> Developed a full-stack Java web application featuring a smart pet-owner compatibility
> matching algorithm, real-time medical record management, appointment scheduling, and
> interactive animated characters. Built 10+ RESTful APIs with Spring Boot & Hibernate/JPA,
> designed a 7-table MySQL relational schema, implemented role-based access control with
> Spring Security, and delivered a responsive animated frontend with JavaScript and Bootstrap.

---

## 👨‍💻 Tech Stack

- **Backend**: Java 17, Spring Boot 3.2, Spring Security, Hibernate/JPA
- **Database**: MySQL 8.0
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript (ES6+), Bootstrap 5
- **Fonts**: Fredoka One, Nunito
- **Build**: Maven
- **Auth**: Spring Security + BCrypt

---

*Built with ❤️ for learning and portfolio purposes.*
