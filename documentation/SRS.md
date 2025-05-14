# Software Requirements Specification (SRS)

## 1. Úvod

### 1.1 Název projektu
**LeadLink CRM** – Jednoduchý CRM systém pro freelancery

### 1.2 Cíl systému
Cílem systému **LeadLink CRM** je poskytnout jednoduché a efektivní řešení pro správu obchodních případu, kontaktů a kalendáře pro freelancery (OSVČ). Aplikace usnadňuje organizaci každodenní práce tím, že centralizuje a propojuje různé aspekty obchodních procesů do jednoho integrovaného systému.

Systém umožní:
- **Založení uživatelského účtu** pro jednotlivé freelancery.
- **Evidenci zákazníků** – správa kontaktů, poznámek a podrobností.
- **Evidenci obchodních případů** – správa jednotlivých zakázek, projektů nebo úkolů.
- **Vedení kalendáře** – propojení s obchodními případy a možnost přidávat poznámky.
- **Generování faktur** – fakturace na základě obchodního případu, automatické vyplnění informací o klientovi.

### 1.3 Rozsah projektu
Tento projekt pokrývá vývoj webové aplikace s následujícím rozsahem funkcí pro první verzi:

**Funkce v první verzi:**
- Vytváření a správa uživatelského účtu.
- Správa obchodních případů v propojeném kalendáři.
- Přidávání, úprava a mazání klientů.
- Generování faktur na základě obchodních případů.

**Funkce mimo rozsah první verze:**
- Automatické notifikace (upozornění na follow-upy, termíny atd.).
- Kontaktní hlášení (exporty a analýzy).

### 1.4 Odkazy na další dokumenty
- **Software Design Document (SDD)** – Další podrobnosti o návrhu a architektuře systému.

---

## 2. Obecný popis

### 2.1 Přehled systému a jeho kontext
**LeadLink CRM** je webová aplikace, která slouží k efektivní správě klientů, obchodních případů a kalendáře pro freelancery (OSVČ). Aplikace pomáhá uživatelům:
- Uchovávat a spravovat kontaktní informace o klientech.
- Vést propojený kalendář s obchodními případy.
- Generovat faktury na základě obchodních případu, což usnadňuje fakturaci.

**Jak pomůže uživateli?**
- Nahrazuje ruční evidenci na papíře nebo v tabulkách.
- Umožňuje lepší organizaci a kontrolu nad obchodními procesy.
- Umožňuje snadno sledovat historii obchodních případů a interakcí s klienty.
- Usnadňuje a automatizuje generování faktur.

### 2.2 Přehled uživatelů
Aplikace **LeadLink CRM** je určena především pro freelancery (OSVČ), kteří potřebují efektivně spravovat své obchodní případy a komunikaci s klienty.

**Hlavní role:**
- **User (Freelancer)**: 
  - Přidává a spravuje klienty.
  - Sleduje obchodní případy a přidává je do kalendáře.
  - Vidí reporting vlastních obchodních případů.
  - Nastavuje follow-upy pro jednotlivé obchodní případy.

- **Admin (Administrátor systému)**: 
  - Spravuje uživatelské účty a oprávnění.
  - Může konfigurovat a upravovat nastavení systému.

### 2.3 Předpoklady a omezení
**Předpoklady:**
- Systém bude webová aplikace přístupná přes moderní webové prohlížeče.
- **Backend**: Spring Boot.
- **Frontend**: React.
- **Databáze**: PostgreSQL.

---

## 3. Funkční požadavky

### 3.1 Podrobně popsané funkcionality

- **Registrace a přihlášení:**
  - Uživatelé se mohou registrovat pomocí e-mailu a hesla.
  - Registrace vyžaduje ověření e-mailu, aby byla potvrzena validita účtu.
  
- **Správa klientů:**
  - Uživatel může přidávat, upravovat a mazat klienty.
  - Každému klientovi lze přiřadit kontaktní informace (telefon, e-mail, adresa) a poznámky.
  - Možnost přidávat tagy pro snadnou kategorizaci klientů.

- **Správa obchodních příležitostí:**
  - Uživatel může vytvářet a spravovat obchodní případy, které mohou být přiřazeny k jednotlivým klientům.
  - Každý obchodní případ bude mít specifikovaný termín, stav a poznámky.

- **Kalendář:**
  - Aplikace poskytuje propojený kalendář, kde si uživatel může plánovat obchodní případy a přidávat poznámky.
  - Obchodní případy jsou zobrazeny v kalendáři, což umožňuje lepší organizaci času.

- **Generování faktur:**
  - Uživatel může generovat faktury na základě obchodních případů.
  - Faktura se automaticky vyplní informacemi o klientovi, pokud je obchodní případ správně propojen s klientem.

### 3.2 Případy užití (Use Cases)
Popis konkrétních případů užití, jako je:
- Registrace nového uživatele.
- Vytváření obchodního případu.
- Generování faktury.
  
---

## 4. UX & UI

### 4.1 Hlavní komponenty UI

- **Nav (public)**:
  - O nás
  - Kontakty
  - Podpora (uživatelská příručka)

- **Nav (private)**:
  - Vyhledávání (uživatel může hledat podle jména, e-mailu nebo jiných údajů).
  - Profil (fotka, jméno, e-mail uživatele).

- **Aside Nav**:
  - **Přehled**: Dashboard, Zprávy, Kalendář.
  - **Obchod**: Adresář, Obchod.

### 4.2 Dashboard
- **Adresář**: Seznam všech klientů s možností přidat, upravit nebo odstranit.
  
### 4.3 Adresář
- **Seznam klientů**: Zobrazuje fotku, jméno, příjmení, telefon, e-mail, společnost, datum přidání a status.
- **Detail klienta**: Zobrazení základních informací o klientovi, poznámky a přehled všech obchodních případů, které jsou s tímto klientem spojeny.

---

## 5. Přílohy

### 5.1 API specifikace
Specifikace API pro interakci s backendem a komunikaci mezi frontendem a backendem.

### 5.2 Mockupy
Vizualizace uživatelského rozhraní ve formě wireframů a návrhů.

### 5.3 Diagramy
UML diagramy pro znázornění vztahů mezi jednotlivými komponentami systému.
