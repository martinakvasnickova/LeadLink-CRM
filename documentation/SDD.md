# Software Design Document (SDD)

## Osnova:

#### 1. Úvod
- 1.1 Cíl dokumentu
- 1.2 Rozsah systému

#### 2. Architektura systému
- 2.1 Architektura aplikace
- 2.2 Komponenty systému
  - Frontend (React)
  - Backend (Spring Boot)
  - Databáze (PostgreSQL)
  - API komunikace
- 2.3 Komunikace mezi komponentami
- 2.4 Struktura projektu (složková / balíčková)

#### 3. API specifikace
- 3.1 Úvod do API
- 3.2 API endpointy
  - Uživatelé
  - Obchodní případy (Cases)
  - Kontakty (Contacts)
  - Události (Events)
  - Faktury (Invoices)
  - Propojení (Case-Event, Contact-Case, Contact-Event)
- 3.3 Odkaz na Swagger dokumentaci

#### 4. Databázový design
- 4.1 Databázová architektura
- 4.2 ER diagram
- 4.3 Popis tabulek
  - users
  - cases
  - contact
  - contact_case
  - events
  - case_event
  - contact_event
  - invoice

#### 5. Návrh uživatelského rozhraní (UI)
- 5.1 Struktura a komponenty UI
  - Sidebar
  - Topbar
  - Dashboard
  - Kalendář
  - Adresář
  - Případy
  - Fakturace
- 5.2 UX Design
  - Principy a interakce
  - Responsivita
  - Validace vstupů
  - Odkaz na mockupy

#### 6. Zabezpečení
- 6.1 Autentizace a autorizace
  - JWT autentizace
  - Role-based access control
  - Průběh autentizace
- 6.2 Zabezpečení aplikace pomocí Spring Security

#### 7. Logování a monitoring
- 7.1 Logování (SLF4J, Logger)
- 7.2 Monitoring (Spring Boot Actuator)


## 1. Úvod

### 1.1 Cíl dokumentu
Tento dokument slouží jako návrhový pro vývoj LeadLink CRM – webové aplikace určené pro freelancery ke správě obchodních případů, klientů, kalendáře a fakturace. Jeho cílem je popsat technický návrh systému a architekturu. 

### 1.2 Rozsah systému
Dokument pokrývá návrh všech hlavních částí aplikace:
  - Frontendovou část: React aplikace, komponentová struktura a interakce s API.
  - Backendovou část: Aplikace postavená na Spring Boot, návrh REST API, business logika a bezpečnost.
  - Databázový model: Návrh tabulek, relací a datových struktur v PostgreSQL.
  - API specifikaci: Endpointy, jejich struktura, autentifikace a odpovědi.
  - Bezpečnostní vrstvu: Ověření uživatelů pomocí JWT, ochrana dat, řízení přístupových práv.
  - Návrh UI/UX: Struktura komponent, hlavní obrazovky a návrh interakcí.

---

## 2. Architektura systému

### 2.1 Architektura aplikace

Aplikace **LeadLink CRM** je postavena na **monolitické architektuře**, kde všechny hlavní funkcionality (autentifikace, správa klientů, obchodních případů, kalendáře a fakturace) běží v rámci jednoho backendového serveru. 

Monolit je rozdělen do vrstev (kontroléry, služby, repository), ale všechny běží ve stejném procesu. S frontendem aplikace komunikuje prostřednictvím REST API.



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



### 2.4 Struktura projektu

Tato sekce popisuje základní složkovou strukturu (balíčkování) frontendové a backendové části systému LeadLink CRM. Cílem je poskytnout přehled, kde se jednotlivé části logiky a komponent nachází, aby se usnadnila orientace v kódu.

#### Backend – Spring Boot (`/src/main/java/com/leadlink/backend`)

| Balíček             | Popis                                                                 |
|---------------------|-----------------------------------------------------------------------|
| `configuration`     | Nastavení OpenApiConfig                  |
| `controller`        | REST kontroléry – mapují HTTP požadavky na služby.                   |
| `dto`               | Přenosové objekty (Data Transfer Objects) pro komunikaci s klientem. |
| `exception`         | Vyjímky (např. `CaseNotFoundException`) a `GlobalExceptionHandler`|
| `model`             | Datové entity (JPA), např. `Users`, `Cases`, `Contact`.              |
| `repository`        | JPA repozitáře pro přístup do databáze.                              |
| `security`          | JWT filtry, Spring Security konfigurace, user detail implementace.   |
| `service`           | Business logika aplikace.                                             |

#### Frontend – React (`/src`)

| Složka              | Popis                                                                 |
|---------------------|------------------------------------------------------------------------|
| `assets/`           | Použité ikony a obrázky                    |
| `components/`       | Znovupoužitelné komponenty UI (např. navigace)    |
| `context/`          | Kontexty pro globální stav (např. přihlášený uživatel)      |
| `fonts/`            | Použité fonty    |
| `hooks/`            | Vlastní hooky (např.`addCase.js`)                    |
| `pages/`            | Rozdělené na Private a Public obrazovky (např. `Dashboard.jsx`, `Clients.jsx`, `Invoices.jsx`). |
| `routes/`           | Nastavení `ProtectedRoute.js`                   |
| `slices/`           | Nastavení `AuthSlice.js`                   |


---

## 3. API specifikace

### 3.1 Úvod do API
Tato sekce popisuje API pro komunikaci mezi frontendem (React) a backendem (Spring Boot). API je založeno na RESTful architektuře a používá HTTP metody pro interakci s databází.

#### Konfigurace a přístup k API

- **Backend**: Aplikace běží na portu `8080` na adrese `http://localhost:8080/`.
- **Frontend**: Aplikace běží na portu `3000` na adrese `http://localhost:3000/`.
- Pro umožnění komunikace mezi frontendem a backendem běžícím na různých portech je nutné mít nakonfigurovaný **CORS** (Cross-Origin Resource Sharing) na backendu.

### 3.2 API Endpoints
Níže je uveden přehled hlavních REST API endpointů systému LeadLink CRM. Endpointy pokrývají práci s uživateli, klienty, obchodními případy, kalendářními událostmi, fakturami a jejich propojením.

#### Uživatelé

- **POST /user/register** – Registrace uživatele
- **POST /user/register-admin** – Registrace administrátora
- **POST /user/login** – Přihlášení a získání JWT tokenu

#### Obchodní případy (Cases)

- **GET /case** – Získat všechny obchodní případy
- **GET /case/{id}** – Získat konkrétní případ podle ID
- **POST /case** – Vytvořit nový případ
- **PUT /case/{id}** – Upravit existující případ
- **DELETE /case/{id}** – Smazat případ
- **GET /case/user** – Získat případy aktuálně přihlášeného uživatele

#### Kontakty (Contacts)

- **GET /contact** – Získat všechny kontakty
- **GET /contact/{id}** – Získat kontakt podle ID
- **POST /contact** – Vytvořit nový kontakt
- **PUT /contact/{id}** – Upravit kontakt
- **DELETE /contact/{id}** – Smazat kontakt
- **GET /contact/user** – Získat kontakty aktuálního uživatele

#### Kalendářní události (Events)

- **GET /event** – Získat všechny události
- **GET /event/{id}** – Získat událost podle ID
- **POST /event** – Vytvořit novou událost
- **PUT /event/{id}** – Upravit událost
- **DELETE /event/{id}** – Smazat událost
- **GET /event/user** – Získat události přihlášeného uživatele

#### Faktury (Invoices)

- **GET /invoice** – Získat všechny faktury
- **GET /invoice/{id}** – Získat fakturu podle ID
- **POST /invoice** – Vytvořit fakturu na základě případu
- **PUT /invoice/{id}** – Upravit fakturu
- **DELETE /invoice/{id}** – Smazat fakturu
- **GET /invoice/user** – Získat faktury přihlášeného uživatele
- **GET /invoice/pre-fill?caseId=ID** – Načíst předvyplněná data pro případ
- **GET /invoice/{id}/pdf** – Stáhnout PDF faktury

#### Propojení: Případy & Události (Case-Event)

- **GET /case-event** – Získat všechna propojení
- **POST /case-event** – Vytvořit propojení
- **DELETE /case-event/{id}** – Smazat propojení
- **GET /case-event/case/{caseId}** – Získat události podle případu
- **GET /case-event/event/{eventId}** – Získat případy podle události

#### Propojení: Kontakty & Případy (Contact-Case)

- **GET /contact-case** – Získat všechna propojení
- **POST /contact-case** – Vytvořit propojení
- **DELETE /contact-case/{id}** – Smazat propojení
- **GET /contact-case/contact/{contactId}** – Získat případy dle kontaktu
- **GET /contact-case/case/{caseId}** – Získat kontakty dle případu

#### Propojení: Kontakty & Události (Contact-Event)

- **GET /contact-event** – Získat všechna propojení
- **POST /contact-event** – Vytvořit propojení
- **DELETE /contact-event/{id}** – Smazat propojení
- **GET /contact-event/contact/{contactId}** – Získat události dle kontaktu
- **GET /contact-event/event/{eventId}** – Získat kontakty dle události

### 3.3 Odkaz na generovanou API dokumentaci
Swagger UI je dostupný na: http://localhost:8080/swagger-ui/index.html

---

## 4. Databázový design

### 4.1 Databázová architektura

Systém **LeadLink CRM** používá relační databázi **PostgreSQL**. Databázová struktura je navržena tak, aby reflektovala entity a vztahy mezi uživateli, klienty, obchodními případy, fakturami, událostmi a jejich vzájemnými propojeními.

#### Typické vztahy:
- **1:N** – Uživatel může mít více případů, kontaktů, faktur, událostí.
- **M:N** – Spojení mezi kontakty a případy/událostmi je řešeno pomocí spojovacích tabulek `contact_case`, `contact_event`, `case_event`.

### 4.2 ERD (Entity Relationship Diagram)



### 4.3 Popis tabulek
#### users
- `id` (int, PK)
- `email` (varchar)
- `firstname` (varchar)
- `lastname` (varchar)
- `username` (varchar)
- `password` (varchar)
- `password_hash` (varchar)
- `role` (enum: USER, ADMIN)

#### cases
- `id` (int, PK)
- `name` (varchar)
- `price` (int)
- `user_id` (int, FK → users.id)

#### contact
- `id` (int, PK)
- `email` (varchar)
- `firstname` (varchar)
- `lastname` (varchar)
- `user_id` (int, FK → users.id)

#### contact_case
- `id` (int, PK)
- `role` (varchar)
- `case_id` (int, FK → cases.id)
- `contact_id` (int, FK → contact.id)

#### events
- `id` (int, PK)
- `created_at` (timestamp)
- `end_at` (timestamp)
- `name` (varchar)
- `start_at` (timestamp)
- `user_id` (int, FK → users.id)
- `case_id` (int, FK → cases.id)

#### case_event
- `id` (int, PK)
- `case_id` (int, FK → cases.id)
- `event_id` (int, FK → events.id)

#### contact_event
- `id` (int, PK)
- `contact_id` (int, FK → contact.id)
- `event_id` (int, FK → events.id)

#### invoice
- `id` (int, PK)
- `amount` (decimal)
- `description` (varchar)
- `due_date` (date)
- `invoice_number` (varchar)
- `issue_date` (date)
- `status` (varchar)
- `case_id` (int, FK → cases.id)
- `contact_id` (int, FK → contact.id)
- `user_id` (int, FK → users.id)

---

## 5. Návrh uživatelského rozhraní (UI)

### 5.1 Struktura a komponenty UI

Uživatelské rozhraní aplikace **LeadLink CRM** je navrženo jako moderní webová aplikace s přístupem k jednotlivým sekcím pomocí levého postranního navigačního panelu (sidebar) a horní lišty (topbar).

#### Hlavní komponenty:

- **Sidebar (levý panel)**:
  - Nástěnka (Dashboard)
  - Kalendář
  - Adresář (kontakty)
  - Případy (obchodní příležitosti)
  - Fakturace
  - Tlačítko Podpora (plovoucí dolní fialové tlačítko)

- **Topbar (horní lišta)**:
  - Vyhledávací pole pro hledání kontaktů
  - Ikona nastavení
  - Ikona oznámení
  - Tlačítko "Odhlášení"

- **Dashboard**:
  - Bloky s přehledem:
    - Příjmy tento měsíc
    - Počet klientů
    - Počet faktur
  - Dnešní události
  - Seznam obchodních případů
  - Nezaplacené faktury

- **Kalendář**:
  - Měsíční / týdenní / denní náhled
  - Zobrazení naplánovaných událostí
  - Tlačítko pro přidání nové události

- **Adresář**:
  - Tabulka kontaktů se jménem, příjmením, e-mailem
  - Ikona pro mazání nebo úpravu kontaktu

- **Případy**:
  - Tabulka obchodních případů: název, cena, kontakt
  - Možnost mazat, upravovat, propojovat kontakty

- **Fakturace**:
  - Tabulka faktur s číslem, popisem, částkou, klientem, stavem
  - Vyskakovací modální okno pro generování faktury
  - Možnost změnit stav (vydaná, zaplacená, zrušená)

### 5.2 UX Design

Uživatelský zážitek (UX) je postaven na jednoduchosti, konzistenci a rychlé dostupnosti informací.

#### Designové principy:
- **Minimalistický vzhled** s přívětivým kontrastem barev (světle šedé pozadí, tmavé texty, akční prvky v modré)
- **Jednoduché a jasné ikony** (např. v menu nebo pro akce jako mazání)
- **Zachování konzistence mezi sekcemi** – stejná struktura tabulek, vyhledávání, řazení

#### Interakce:
- **Plynulé přechody mezi sekcemi** pomocí routeru
- **Modální okna** pro zadávání dat bez nutnosti opouštět hlavní kontext (např. generování faktury)
- **Responsivita** – layout je optimalizovaný i pro menší obrazovky (např. tablety)
- **Validace vstupů** u formulářů (např. datum, číslo faktury, vyplnění povinných polí)

#### Mockupy:

https://www.figma.com/design/WqbHm3ER4OKU8BTMYH8GVi/LeadLink?node-id=247-498&t=zFMjrBlKvRDNauAK-1

---

## 6. Zabezpečení 

### 6.1 Autentizace a autorizace
Systém **LeadLink CRM** využívá ke správě přístupu k chráněným zdrojům **autentizaci pomocí JWT (JSON Web Token)** a **autorizaci na základě rolí uživatelů**.

#### 6.1.1 Průběh:

1. **Uživatel odešle přihlašovací požadavek** na endpoint `POST /user/login` s `username` a `password`.
2. Backend pomocí služby `UserService` ověří přihlašovací údaje.
3. Pokud je ověření úspěšné, vygeneruje se **JWT token** pomocí třídy `JwtService`. Token obsahuje:
   - `sub` (uživatelské jméno),
   - `exp` (datum expirace – 1 hodina),
   - `role` (např. `"ROLE_USER"` nebo `"ROLE_ADMIN"`).
4. Tento token je vrácen klientovi jako součást objektu `JwtResponse`.
5. Klient pak token posílá v hlavičce každého chráněného požadavku:

```
Authorization: Bearer <JWT>
```

#### 6.1.2 Zpracování tokenu:
- Token je při každém požadavku zkontrolován filtrem `JwtAuthFilter`, který:
  - extrahuje JWT z hlavičky,
  - ověří jeho platnost (`JwtService.isTokenValid()`),
  - extrahuje `username` a roli,
  - nastaví ověřeného uživatele do `SecurityContext`.


#### 6.1.3 Autorizace (Role-based access control)

Systém definuje dvě hlavní role uživatelů pomocí výčtového typu `Role`:

```java
public enum Role {
    USER,
    ADMIN
}
```

#### 6.1.4 Role a oprávnění:

- `USER` (běžný uživatel):
  - může spravovat vlastní klienty, případy, události, faktury,
  - nemá přístup k administrativním funkcím.

- `ADMIN`:
  - má navíc přístup k administrativním endpointům (např. `/admin/**`, `/user/register-admin`),
  - je omezen přes Spring Security konfiguraci:

```java
.requestMatchers("/admin/**", "/user/register-admin").hasRole("ADMIN")
```

#### 6.2 Zabezpečení aplikace (Spring Security)

- Konfigurace bezpečnosti je řešena pomocí třídy `SecurityConfig`.
- Používá se `BCryptPasswordEncoder` pro šifrování hesel.
- Autentizace je spravována filtrem `JwtAuthFilter`, kterým požadavek projde **před** standardní autentizační logikou (`UsernamePasswordAuthenticationFilter`).

---

## 7. Logování a monitoring

### 7.1 Logování 
Přihlášení, registrace, vytvoření, mazání a editace případů i uživatelů jsou logovány pomocí SLF4J (Logger).
Chyby validace, neočekávané výjimky a chyby přístupu jsou logovány s úrovní WARN nebo ERROR.

### 7.2 Monitoring
rojekt obsahuje základy pro Spring Boot Actuator (endpoint /actuator/metrics).


