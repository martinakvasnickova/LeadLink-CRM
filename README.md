# LeadLink Backend - Dokumentace k modulům Users, Security a Cases

## Popis projektu a cíle práce

LeadLink je aplikace zaměřená na správu obchodních případů a kontaktů, včetně přidělování případů k uživatelům. Cílem bylo vytvořit bezpečný a škálovatelný backend, který umožní správu uživatelů, autentizaci, autorizaci a správu obchodních případů pomocí moderní architektury REST API.

## Architektura systému

- **Controller vrstva**: Zpracovává HTTP požadavky a vrací odpovědi. (UserController, CaseController)
- **Service vrstva**: Obsahuje business logiku a interaguje s repository vrstvou. (UserService, CaseService)
- **Repository vrstva**: Pracuje s databází pomocí Spring Data JPA. (UserRepository, CaseRepository)
- **Security vrstva**: Řeší autentizaci a autorizaci uživatelů (JWT, Spring Security).
- **Model/DTO**: Definuje entity (`Users`, `Cases`) a přenosové objekty (`UserRequestDTO`, `CaseDTO`).
- **Exception handling**: Globální zpracování chyb pomocí `GlobalExceptionHandler`.

## Popis implementovaných bezpečnostních mechanismů

- **JWT autentizace**: Každé přihlášení generuje token, který musí být přiložen v HTTP Authorization hlavičce (`Bearer token`) pro přístup k chráněným endpointům.
- **Role-based autorizace**: Přístup k určitým endpointům (např. `/admin/**`) je omezen pouze na uživatele s rolí `ADMIN`.
- **Hashování hesel**: Hesla uživatelů jsou ukládána bezpečně pomocí algoritmu BCrypt.
- **Filtr JWT** (`JwtAuthFilter`) validuje token před zpracováním požadavků.

## Ukázky validace a zpětné vazby API

Příklad validace registrace uživatele (`UserRequestDTO`):

- **Pravidla**:
  - `firstname`, `lastname`, `email`, `username` musí být neprázdné (`@NotBlank`).
  - `email` musí být ve správném formátu (`@Email`).
  - `password` musí mít minimálně 6 znaků (`@Size(min = 6)`).

- **Chybová odpověď na špatné údaje**:
```json
{
  "status": 400,
  "message": "Validation error",
  "errors": {
    "password": "Password must have at least 6 characters",
    "email": "Email must be valid"
  }
}
```

- **Chybová odpověď při neexistujícím uživateli:**

```json
{
  "status": 404,
  "message": "Could not found user with id 123",
  "data": null,
  "errors": null
}

```

## Informace o logování a monitoringu

### Logování

- Přihlášení, registrace, vytvoření, mazání a editace případů i uživatelů jsou logovány pomocí SLF4J (Logger).
- Chyby validace, neočekávané výjimky a chyby přístupu jsou logovány s úrovní WARN nebo ERROR.

### Monitoring

- Projekt obsahuje základy pro Spring Boot Actuator (endpoint `/actuator/metrics`).
- Možné snadno doplnit Health Checky, metriky apod.

## Odkaz na generovanou API dokumentaci

Swagger UI je dostupný na:
http://localhost:8080/swagger-ui/index.html
