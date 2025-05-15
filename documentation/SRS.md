# Software Requirements Specification (SRS)

## Obsah dokumentu

#### 1. Úvod
- 1.1 Název projektu
- 1.2 Cíl systému
- 1.3 Rozsah projektu
  - Funkce v první verzi
  - Funkce mimo rozsah první verze
- 1.4 Odkazy na další dokumenty

#### 2. Obecný popis
- 2.1 Přehled systému a jeho kontext
- 2.2 Přehled uživatelů
  - Role: Freelancer (User)
  - Role: Administrátor (Admin)
- 2.3 Předpoklady a omezení
- 2.4 Diagram tříd (UML Class Diagram)

#### 3. Funkční požadavky
- 3.1 Podrobně popsané funkcionality
  - Registrace a přihlášení
  - Správa klientů
  - Správa obchodních příležitostí
  - Kalendář
  - Generování faktur
- 3.2 Scénáře
  -   Plánování obchodního případu do kalendáře
  -   Generování faktury z obchodního případu
  -   Změna stavu faktury
- 3.3 Use Case Diagram

#### 4. UX & UI
- 4.1 Hlavní komponenty UI
  - Navigace (public/private)
  - Aside navigace
- 4.2 Dashboard
- 4.3 Adresář
- 4.4 Obchodní případy
- 4.5 Kalendář
- 4.6 Fakturace

#### 5. Přílohy
- 5.1 Mockupy
- 5.2 Diagramy

---

## 1. Úvod

### 1.1 Název projektu
**LeadLink CRM** – Jednoduchý CRM systém pro freelancery

### 1.2 Cíl systému
Cílem aplikace **LeadLink** je poskytnout jednoduché a efektivní řešení pro správu obchodních případu, kontaktů a kalendáře pro freelancery. Aplikace usnadňuje organizaci každodenní práce tím, že centralizuje a propojuje různé aspekty obchodních procesů do jednoho systému.

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
- Exporty a analýzy.

### 1.4 Odkazy na další dokumenty
- **Software Design Document (SDD)** – Další podrobnosti o návrhu a architektuře systému.

---

## 2. Obecný popis

### 2.1 Přehled systému a jeho kontext
**LeadLink CRM** je webová aplikace, která slouží k efektivní správě klientů, obchodních případů a kalendáře pro freelancery. Aplikace pomáhá uživatelům:
- Uchovávat a spravovat kontaktní informace o klientech.
- Vést propojený kalendář s obchodními případy.
- Generovat faktury na základě obchodních případu, což usnadňuje fakturaci.

**Jak pomůže uživateli?**
- Nahrazuje ruční evidenci na papíře nebo v tabulkách.
- Umožňuje lepší organizaci a kontrolu nad obchodními procesy.
- Umožňuje snadno sledovat historii obchodních případů a interakcí s klienty.
- Usnadňuje a automatizuje generování faktur.

### 2.2 Přehled uživatelů
Aplikace **LeadLink CRM** je určena především pro freelancery, kteří potřebují efektivně spravovat své obchodní případy a komunikaci s klienty.

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

### 2.4 Diagram tříd (UML Class Diagram)

Následující UML třídní diagram poskytuje logický pohled na hlavní entity systému LeadLink CRM a jejich vztahy. Slouží k pochopení struktury dat bez vazby na konkrétní technologii nebo databázovou implementaci.

Diagram znázorňuje klíčové entity jako jsou **Uživatel**, **Klient**, **Obchodní případ**, **Faktura** a **Kalendářní událost**, a popisuje, jak spolu tyto entity souvisejí. Vztahy mezi třídami odrážejí základní obchodní logiku systému – například že obchodní případ je přiřazen ke konkrétnímu klientovi a může být propojen s kalendářní událostí nebo fakturou.

![image](https://github.com/user-attachments/assets/4e9340b0-5976-48ee-9ed8-f53792245c47)


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

### 3.2 Scénáře

#### 3.2.1 Plánování obchodního případu do kalendáře

- **Název**: Plánování obchodního případu  
- **Aktér**: Freelancer  
- **Předpoklad**: Uživatel má vytvořený klientský kontakt  

**Hlavní scénář**:
1. Uživatel vytvoří nový obchodní případ a přiřadí jej ke klientovi.
2. Otevře kalendář a klikne na vybraný termín.
3. Vybere typ akce "Obchodní případ", přiřadí konkrétní případ.
4. Přidá poznámku a časové rozmezí.
5. Potvrdí akci.

**Výsledek**: Obchodní případ je zobrazen v kalendáři jako naplánovaná událost.

#### 3.2.2 Generování faktury z obchodního případu

- **Název**: Generování faktury  
- **Aktér**: Freelancer  
- **Předpoklad**: Existuje obchodní případ, který má připojeného klienta a hodnotu  

**Hlavní scénář**:
1. Uživatel přejde do detailu obchodního případu.
2. Klikne na možnost "Vygenerovat fakturu".
3. Systém automaticky předvyplní údaje klienta a hodnotu případu.
4. Uživatel případně upraví data (např. datum splatnosti).
5. Potvrdí vytvoření faktury.

**Výsledek**: Nová faktura je vytvořena a zařazena do seznamu faktur.

#### 3.2.3 Změna stavu faktury

- **Název**: Změna stavu faktury  
- **Aktér**: Freelancer  
- **Předpoklad**: Existuje alespoň jedna vygenerovaná faktura  

**Hlavní scénář**:
1. Uživatel otevře sekci „Faktury“.
2. Vyhledá konkrétní fakturu podle klienta nebo data.
3. Klikne na „Změnit stav“ a vybere jednu z možností:
   - Zaplacená
   - Čekající
   - Zrušená
4. Potvrdí změnu.

**Výsledek**: Nový stav faktury je uložen a zobrazen v přehledu.

#### Zobrazení historie obchodních případů klienta

- **Název**: Historie případů klienta  
- **Aktér**: Freelancer  
- **Předpoklad**: Klient má přiřazeno více obchodních případů  

**Hlavní scénář**:
1. Uživatel otevře adresář klientů.
2. Klikne na konkrétního klienta.
3. V detailu klienta se zobrazí seznam všech historických i aktivních obchodních případů.
4. Uživatel může zobrazit detail konkrétního případu.

**Výsledek**: Uživatel získá přehled o všech interakcích a případech spojených s klientem.


### 3.3 Use Case Diagram
![image](https://github.com/user-attachments/assets/a454986d-1f29-44fb-8a42-1035d928a8da)


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
- **Rychlé přehledy**: Na dashboardu jsou zobrazeny klíčové statistiky a informace, jako například:
  - **Kolik jsme vyfakturovali**: Celková částka vygenerovaných faktur.
  - **Naplánované události na dnešek**: Zobrazení nadcházejících událostí a úkolů na aktuální den.
  - **Probíhající obchodní případy**: Seznam obchodních případů, které jsou aktivní a čekají na vyřízení.
  - **Faktury po splatnosti**: Přehled faktur, jejichž termín splatnosti uplynul.

### 4.3 Adresář
- **Seznam klientů**: Adresář zobrazuje informace o všech klientech, včetně:
  - **Jméno a příjmení**.
  - **E-mail**.
  - **Fotka** (pokud je k dispozici).
  - **Poznámky** (osobní nebo relevantní informace o klientovi).
  - **Firma** (pokud je relevantní).
  - **Obchodní případy připojené ke kontaktu**: Zobrazení všech obchodních případů, které jsou spojené s konkrétním klientem.

### 4.4 Obchodní případy
- **Seznam obchodních případu**: Každý obchodní případ obsahuje:
  - **Název případu**: Stručný popis nebo název obchodní příležitosti.
  - **Cena**: Stanovená cena pro daný obchodní případ.
  - **Poznámka**: Další relevantní informace o obchodním případu.
  - **Připojený kontakt**: Klient nebo osoba, která je s obchodním případem spojena.

### 4.5 Kalendář
- **Velký kalendář**: Zobrazení kalendáře, který je defaultně nastaven na měsíční zobrazení, kde se zobrazují všechny akce a obchodní případy. Každá událost obsahuje:
  - **Název**: Krátký popis akce nebo obchodního případu.
  - **Od kdy do kdy**: Časový rámec pro danou akci nebo událost.
  - **Připojený případ či kontakt**: Pokud je akce propojena s obchodním případem nebo konkrétním kontaktem, bude tato informace uvedena.

### 4.6 Fakturace
- **Přehled faktur**: Zobrazení všech vygenerovaných faktur s aktuálním stavem. Stav faktur může být:
  - **Zaplacené**: Faktury, které byly již uhrazeny.
  - **Čekající**: Faktury, které čekají na úhradu.
  - **Zrušené**: Faktury, které byly zrušeny.
- **Možnost změny stavu faktur**: Uživatel může měnit stav faktur podle aktuální situace.
- **Generování nové faktury**: Možnost vygenerovat novou fakturu na základě obchodního případu.

---

## 5. Přílohy

### 5.1 Mockupy
Vizualizace uživatelského rozhraní ve formě wireframů a návrhů.

https://www.figma.com/design/WqbHm3ER4OKU8BTMYH8GVi/LeadLink?node-id=247-498&t=skUW3MCewCADjHap-1

### 5.2 Diagramy
UML diagramy pro znázornění vztahů mezi jednotlivými komponentami systému.
