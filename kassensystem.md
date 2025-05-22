## Plichtenheft: Kassenprogramm mit Frontend, REST-API und Spring Boot

### 1. Einleitung
Dieses Dokument spezifiziert die technische Umsetzung des Kassenprogramms gemäß dem Lastenheft, erweitert um die Anforderungen an eine REST-API-basierte Client-Server-Architektur. Das System besteht aus einem HTML, CSS, JavaScript(Client), einem Spring Boot-Backend (Server) und einer filebasierten H2 Datenbank.

### 2. Ziel
Entwicklung eines Kassenprogramms mit:

Frontend: Benutzeroberfläche zur Interaktion mit dem Kassensystem HTML, CSS, JavaScript.

Backend: Spring Boot-Anwendung mit REST-API für die Geschäftslogik.

Datenbank: Filebasierte Persistierung H2.

### 3. Zielgruppe
Endnutzer: Ladenbetreiber:innen, die das Frontend zur Verwaltung nutzen.


### 4. Systemarchitektur
Frontend:

Single-Page-Application (SPA) mit REST-API-Integration.

Kommunikation über RESTFUL API's dem Backend.

Backend:

Spring Boot-Anwendung mit REST-Controllern.

Persistierung der Daten in einer H2 filebasierten Datenbank.

Nutzung von Java.sql JDBC für Datenbankzugriffe.

Datenbank:

Filebasierte Lösung  .db-Datei.

Tabellenstruktur für Produkte, Transaktionen und Lagerbestände.

### 5. Funktionale Anforderungen

#### 5.1 Frontend
Hauptmenü: Darstellung der Optionen als UI-Komponenten (Buttons/Navigation).

Kassenvorgang:

Anzeige der Produktliste (REST-GET /api/products).

Produktauswahl über Dropdown/Liste mit Produktnummern.

Eingabefeld für Mengen, Validierung des Lagerbestands (REST-GET /api/products/{id}/stock).

Berechnung des Gesamtpreises.

Bon-Ansicht nach Abschluss (REST-POST /api/transactions).

Produktverwaltung:

Formular zum Hinzufügen neuer Produkte (REST-POST /api/products).

Warenzugang:

Auswahl eines Produkts und Eingabe der Menge (REST-PATCH /api/products/{id}/stock).

Lagerbestand:

Tabellarische Darstellung aller Produkte mit Beständen (REST-GET /api/products).

#### 5.2 Backend
REST-API-Endpunkte:

GET /api/products: Liefert alle Produkte mit Name, Preis, Lagerbestand.

POST /api/products: Erstellt ein neues Produkt.

PATCH /api/products/{id}/stock: Aktualisiert den Lagerbestand (für Verkäufe/Warenzugänge).

POST /api/transactions: Verarbeitet Kassenvorgänge, reduziert Lagerbestände, generiert Bon-Daten.

Datenpersistierung:

Speicherung der Produkte und Transaktionen in der H2 Datenbank.

Transaktionsmanagement für konsistente Lagerupdates.

Bon-Generierung:

JSON-Response mit Bon-Daten (gekaufte Produkte, Einzelpreise, Gesamtsumme).

#### 5.3 Datenbank
Produkte-Tabelle:

id (Primärschlüssel), name (String), price (Double), stock (Integer).

Transaktionen-Tabelle:

id (Primärschlüssel), timestamp (DateTime), total (Double).

Transaktionsdetails-Tabelle:

transaction_id (Fremdschlüssel), product_id (Fremdschlüssel), quantity (Integer).

### 6. Projektmanagement
Versionierung: Git mit Repository (z. B. GitHub/GitLab).

