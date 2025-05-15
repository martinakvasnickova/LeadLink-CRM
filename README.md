# LeadLink CRM

**LeadLink CRM** je jednoduchý CRM systém navržený pro freelancery. Umožňuje správu klientů, obchodních případů, kalendáře a generování faktur.

## Struktura projektu

Projekt je rozdělen do několika částí:

```
.
├── backend/         # Backend(Spring Boot)
├── frontend/        # Frontend (React)
├── database/        # Vytvoření databáze, test
├── documentation/   # Technická dokumentace (SRS, SDD)
├── manual/          # Uživatelské a administrátorské příručky
└── README.md        # Tento soubor
```

## Požadavky

- Java 
- Node.js 
- PostgreSQL
- Maven

## Rychlý start

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Databáze

Vytvořte si lokální instanci PostgreSQL 


## Dokumentace

### Uživatelská příručka
Dostupná z aplikace

### Administrátorská příručka
Cesta: `manual/admin-manual.md`

### Software Requirements Specification (SRS)
Cesta: `documentation/SRS.md`

### Software Design Document (SDD)
Cesta: `documentation/SDD.md`

## Funkce

- Registrace a přihlášení uživatele (JWT)
- Evidence klientů a obchodních případů
- Kalendář napojený na případy a události
- Generování faktur včetně sledování stavu (zaplaceno, čekající, zrušeno)




