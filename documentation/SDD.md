### 5.1 API specifikace
Tato sekce popisuje API pro komunikaci mezi frontendem (React) a backendem (Spring Boot). API je založeno na RESTful architektuře a používá HTTP metody pro interakci s databází.

#### 1. Konfigurace a přístup k API

- **Backend**: Aplikace běží na portu `8080` na adrese `http://localhost:8080/`.
- **Frontend**: Aplikace běží na portu `3000` na adrese `http://localhost:3000/`.
- Pro umožnění komunikace mezi frontendem a backendem běžícím na různých portech je nutné mít nakonfigurovaný **CORS** (Cross-Origin Resource Sharing) na backendu.

#### 2. Autentifikace

API používá **JWT (JSON Web Token)** pro autentifikaci a autorizaci uživatelů. Po přihlášení obdrží uživatel token, který musí připojit do hlavičky `Authorization` každého následného požadavku:

- **POST /user/register** – Registrace nového uživatele  
  Parametry:
  ```json
  { "firstname":"Jon", "lastname":"Doe", "email": "user@example.com", "password": "password123" }
  ```

  Odpověď:
    ```json
  { "message": "Registration successful" }
    ```

- **POST /user/login** – Přihlášení uživatele  
  Parametry:
  ```json
  { "email": "user@example.com", "password": "password123"}
  ```

  Odpověď:
      ```json
  { "message": "Registration successful" }
      ```
      