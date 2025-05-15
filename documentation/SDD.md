# Software Design Document (SDD)

## 1. Úvod

### 1.1 Cíl dokumentu
Tento dokument slouží jako návrhový základ pro vývoj systému LeadLink CRM – webové aplikace určené pro freelancery (OSVČ) ke správě obchodních případů, klientů, kalendáře a fakturace. Jeho cílem je popsat technický návrh systému a architekturu tak, aby vývojový tým měl jasné podklady pro implementaci.

Dokument zahrnuje návrh softwarové architektury, komunikace mezi komponentami, návrh databáze, popis API, uživatelského rozhraní a bezpečnostních mechanismů. Zajišťuje konzistenci mezi požadavky uvedenými v SRS a jejich implementací.

### 1.2 Rozsah systému
Tento dokument pokrývá návrh všech hlavních částí systému LeadLink CRM, konkrétně:
  - Frontendovou část: React aplikace, komponentová struktura a interakce s API.
  - Backendovou část: Aplikace postavená na Spring Boot, návrh REST API, business logika a bezpečnost.
  - Databázový model: Návrh tabulek, relací a datových struktur v PostgreSQL.
  - API specifikaci: Endpointy, jejich struktura, autentifikace a odpovědi.
  - Bezpečnostní vrstvu: Ověření uživatelů pomocí JWT, ochrana dat, řízení přístupových práv.
  - Návrh UI/UX: Struktura komponent, hlavní obrazovky a návrh interakcí.

---

## 2. Architektura systému

### 2.1 Architektura aplikace

Aplikace **LeadLink CRM** je postavena na **monolitické architektuře**, kde všechny hlavní funkcionality (autentifikace, správa klientů, obchodních případů, kalendáře a fakturace) běží v rámci jednoho backendového serveru. Tato architektura byla zvolena pro svou jednoduchost, snadnější nasazení a menší složitost v rámci první verze systému.

Monolit je rozdělen do modulárních vrstev (kontroléry, služby, repository), ale všechny běží ve stejném procesu. S frontendem aplikace komunikuje prostřednictvím REST API.

**Doporučený diagram**:
- **Component Diagram** – znázorňující vztah mezi frontendem, backendem a databází.
- Alternativně jednoduchý **Deployment Diagram**, pokud chceš vizualizovat i provozní prostředí (např. server, porty, připojení).


### 2.2 Komponenty systému

Systém je složen z těchto hlavních komponent:

#### Frontend (React)
- Implementován v knihovně **React**.
- Poskytuje uživatelské rozhraní pro interakci s aplikací.
- Komunikuje s backendem pomocí HTTP požadavků (REST API).
- Zodpovědný za správu stavu, UI/UX logiku a prezentaci dat.

#### Backend (Spring Boot)
- Vytvořen pomocí **Spring Boot** (Java).
- Zajišťuje aplikační logiku, zpracování dat, autentifikaci a autorizaci.
- Obsahuje vrstvy: kontroléry, služby, datové repository.
- Exponuje REST API, přes které komunikuje s frontendem.

#### Databáze (PostgreSQL)
- Relační databáze sloužící pro uložení aplikačních dat.
- Obsahuje tabulky pro uživatele, klienty, obchodní případy, faktury a události.
- Backend přistupuje k databázi přes JPA/Hibernate.

#### API komunikace
- Komunikace mezi frontendem a backendem probíhá pomocí **RESTful API**.
- Backend poskytuje zabezpečené endpointy pro CRUD operace nad jednotlivými entitami.
- Autentifikace probíhá přes JWT (JSON Web Token).

### 2.3 Komunikace mezi komponentami

Všechny komponenty komunikují **přes REST API** vystavené backendem:

- **Frontend** odesílá HTTP požadavky (GET, POST, PUT, DELETE) na backendové endpointy.
- Backend tyto požadavky zpracuje, případně validuje, a přistupuje k databázi.
- Backend vrací odpovědi ve formátu JSON.
- JWT token je odesílán v hlavičce každého požadavku pro ověření oprávnění.

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

