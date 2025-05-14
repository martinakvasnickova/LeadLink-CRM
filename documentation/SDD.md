# Software Design Document (SDD)

## 1. Úvod

### 1.1 Cíl dokumentu
Popis účelu tohoto dokumentu, který je zaměřen na návrh systému a popis technických aspektů aplikace. Tento dokument by měl sloužit jako návod pro vývojáře při implementaci systému.

### 1.2 Rozsah systému
Popis, jaké konkrétní části systému tento dokument pokrývá, např. backendové API, databázový model, struktura aplikace atd.

### 1.3 Definice a zkratky
Seznam specifických termínů a zkratek použitých v dokumentu.

---

## 2. Architektura systému

### 2.1 Architektura aplikace
Popis základní architektury systému, např. zda používá monolitickou strukturu, mikroservisy, serverless architekturu, atd. Zde je možné připojit diagramy.

### 2.2 Komponenty systému
Podrobný popis hlavních komponent systému, jejich odpovědnosti a vztahy mezi nimi. Komponenty mohou zahrnovat:
- Frontend (React aplikace)
- Backend (Spring Boot)
- Databáze (PostgreSQL)
- API komunikace

### 2.3 Komunikace mezi komponentami
Popis, jak jednotlivé komponenty komunikují mezi sebou, např. přes REST API, WebSockety, nebo jiný způsob.

---

## 3. API specifikace

### 3.1 Úvod do API
Tato sekce popisuje API pro komunikaci mezi frontendem (React) a backendem (Spring Boot). API je založeno na RESTful architektuře a používá HTTP metody pro interakci s databází.

#### 1. Konfigurace a přístup k API

- **Backend**: Aplikace běží na portu `8080` na adrese `http://localhost:8080/`.
- **Frontend**: Aplikace běží na portu `3000` na adrese `http://localhost:3000/`.
- Pro umožnění komunikace mezi frontendem a backendem běžícím na různých portech je nutné mít nakonfigurovaný **CORS** (Cross-Origin Resource Sharing) na backendu.

### 3.2 API Endpoints
Podrobný popis jednotlivých API endpointů, včetně metod (GET, POST, PUT, DELETE), popisu požadavků a odpovědí, formátu dat, status kódů atd.

#### Příklad:
- **POST /api/auth/register**
  - Popis: Registrace nového uživatele.
  - Parametry:
    ```json
    { "email": "user@example.com", "password": "password123" }
    ```
  - Odpověď:
    ```json
    { "message": "Registration successful" }
    ```

### 3.3 Autentifikace a autorizace
Popis mechanismu autentifikace (např. JWT) a autorizace, jak budou uživatelé identifikováni a jaké role (admin, uživatel) budou podporovány.

---

## 4. Databázový design

### 4.1 Databázová architektura
Popis struktury databáze, jaké tabulky budou použity a jaké vztahy mezi nimi existují (např. 1:N, N:M).

### 4.2 ERD (Entity Relationship Diagram)
Diagram, který znázorňuje vztahy mezi entitami v databázi. Tento diagram může být připojen jako obrázek.

### 4.3 Popis tabulek
Podrobný popis každé tabulky, včetně jejich sloupců a datových typů. Můžete zde zahrnout informace o primárních klíčích, cizích klíčích, indexech atd.

#### Příklad:
- **Users**: Tabulka pro uživatele.
  - `id` (int, primary key)
  - `email` (varchar, unique)
  - `password` (varchar)

---

## 5. Návrh uživatelského rozhraní (UI)

### 5.1 Struktura a komponenty UI
Popis hlavních komponent uživatelského rozhraní, jaké budou sekce, jaké funkce budou implementovány a jaké interakce jsou očekávány.

### 5.2 UX Design
Podrobný popis uživatelského zážitku, jak by mělo uživatelské rozhraní vypadat a jak by mělo fungovat. Může to zahrnovat diagramy nebo mockupy.

---

## 6. Bezpečnostní aspekty

### 6.1 Autentifikace a autorizace
Podrobný popis, jak bude systém zabezpečen proti neoprávněnému přístupu, jaké metody autentifikace budou použity (např. OAuth, JWT), a jak bude probíhat autorizace pro různé role (admin, uživatel).

### 6.2 Šifrování
Popis, jak budou citlivé informace (např. hesla) šifrovány, jaké šifrovací algoritmy budou použity a jaká bezpečnostní opatření budou přijata.

---

## 7. Výkon a škálovatelnost

### 7.1 Požadavky na výkon
Specifikace požadavků na výkon systému, například jaké maximální latence a doby odezvy by měly být dosaženy.

### 7.2 Škálovatelnost
Jak bude systém navržen, aby zvládal rostoucí počet uživatelů, dat a požadavků. Zde můžete specifikovat horizontální a vertikální škálování.

---

## 8. Testování

### 8.1 Testování API
Jaké testy budou prováděny na API, včetně unit testů, integračních testů a testů výkonu.

### 8.2 Testování UI
Jaké testy budou prováděny na uživatelském rozhraní, včetně testování použitelnosti a funkčnosti.

### 8.3 Testovací prostředí
Popis prostředí, ve kterém budou testy prováděny (např. vývojové prostředí, staging, produkce).

---

## 9. Nasazení

### 9.1 Nasazovací pipeline
Popis procesu nasazení, jakým způsobem bude aplikace nasazována na produkční server (CI/CD pipeline).

### 9.2 Provozní prostředí
Popis prostředí, ve kterém bude aplikace běžet (serverová infrastruktura, databázová infrastruktura, apod.).

---

## 10. Přílohy

### 10.1 Diagramy
Připojte diagramy, které ilustrují návrh systému, architekturu nebo databázové schéma.

### 10.2 Kódové ukázky
Pokud je to potřeba, připojte ukázky kódu nebo fragmenty kódu, které ilustrují klíčové části implementace.

