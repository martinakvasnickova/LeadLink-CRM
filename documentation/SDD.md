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

![image](https://github.com/user-attachments/assets/9e437c24-40d6-4fcb-bff1-eb1d41edf75a)



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

![image](https://github.com/user-attachments/assets/ccdc60ab-a40a-491f-adbe-ab6106edd9dd)


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


### 3.3 Autentizace a autorizace
Systém **LeadLink CRM** využívá ke správě přístupu k chráněným zdrojům **autentizaci pomocí JWT (JSON Web Token)** a **autorizaci na základě rolí uživatelů**.

#### 3.3.1 Průběh:

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

#### 3.3.2 Zpracování tokenu:
- Token je při každém požadavku zkontrolován filtrem `JwtAuthFilter`, který:
  - extrahuje JWT z hlavičky,
  - ověří jeho platnost (`JwtService.isTokenValid()`),
  - extrahuje `username` a roli,
  - nastaví ověřeného uživatele do `SecurityContext`.


#### 3.3.3 Autorizace (Role-based access control)

Systém definuje dvě hlavní role uživatelů pomocí výčtového typu `Role`:

```java
public enum Role {
    USER,
    ADMIN
}
```

#### 3.3.4 Role a oprávnění:

- `USER` (běžný uživatel):
  - může spravovat vlastní klienty, případy, události, faktury,
  - nemá přístup k administrativním funkcím.

- `ADMIN`:
  - má navíc přístup k administrativním endpointům (např. `/admin/**`, `/user/register-admin`),
  - je omezen přes Spring Security konfiguraci:

```java
.requestMatchers("/admin/**", "/user/register-admin").hasRole("ADMIN")
```

#### 3.3.5 Zabezpečení aplikace (Spring Security)

- Konfigurace bezpečnosti je řešena pomocí třídy `SecurityConfig`.
- Používá se `BCryptPasswordEncoder` pro šifrování hesel.
- Autentizace je spravována filtrem `JwtAuthFilter`, který je zaregistrován **před** standardní autentifikační logikou (`UsernamePasswordAuthenticationFilter`).


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

### 7.1 Monitorování
Aplikace je monitorována 

### 7.2 Škálovatelnost
Jak bude systém navržen, aby zvládal rostoucí počet uživatelů, dat a požadavků. Zde můžete specifikovat horizontální a vertikální škálování.

---

## 10. Přílohy

### 10.1 Diagramy
Připojte diagramy, které ilustrují návrh systému, architekturu nebo databázové schéma.

### 10.2 Kódové ukázky
Pokud je to potřeba, připojte ukázky kódu nebo fragmenty kódu, které ilustrují klíčové části implementace.

