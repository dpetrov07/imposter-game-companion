# Project Plan – Imposter Game Companion

## Goal
Build and deploy a web-based tool that helps run an in-person "Imposter" game by managing players, assigning roles securely, and persisting game state.

---

## MVP

### Core Features
- Create a game session
- Add players to a game session
- Assign roles to players (server-side only)
- Persist game state in a database
- Retrieve game state via API
- Backend is deployed and accessible via a public URL

### Technical Requirements
- Backend: Java + Spring Boot
- Database: PostgreSQL
- API: REST
- Persistence via JPA/Hibernate
- Environment-based configuration
- Deployed backend (Railway / Fly.io / AWS)

## Post MVP
- Analytics or dashboards
- User authentication / accounts
- Machine learning or data science
- WebSockets
- Fancy UI or animations

---

## Success Criteria

The MVP is successful if:
- A game can be created via an API call
- Players can be added to the game
- Roles are assigned and stored securely
- Restarting the server does NOT lose game data
- The backend runs in the cloud and responds to requests
