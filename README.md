# Imposter Game Companion

Imposter Game Companion is a simple web app that helps you run an in-person
game. One person hosts the game on their device and
passes it around so each player can privately see their role.

Live Demo: https://imposter-game-companion.vercel.app
> Note: Backend may take ~30-60 seconds to wake up on first "Create Game" request

---

## How It Works

1. Create a new game
2. Add players to the lobby
3. Select a category
4. Start the game
5. Pass the device to each player so they can reveal their secret word
6. After everyone has seen their role, can restart game and play again

---

### Backend
- Java 17
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- REST API
- DTO-based architecture

### Frontend
- React (Vite)
- JavaScript
- Fetch API
- Minimal CSS

---

## Architecture
- Backend: Spring Boot REST API exposing game session and player endpoints
- Frontend: React app that uses REST API to manage game state and UI
- Database: PostgreSQL (Supabase)
- ORM: JPA/Hibernate for entity mapping and persistence
- Migrations: Flyway for schema versioning and data
- Deployment: Dockerized backend deployed on Render

## Data Management
- Database schema and seed data are managed with Flyway migrations
- Inactive sessions automatically cleaned up by scheduled background jobs

## Production Notes
- Backend containerized on Docker with a multi-stage build
- Deployed on Render with a managed PostgreSQL database on Supabase
- Frontend deployed and hosted on Vercel

---

## Running the App Locally

### Backend

```bash
./mvnw spring-boot:run
```

### Frontend

```bash
npm install
npm run dev
```
