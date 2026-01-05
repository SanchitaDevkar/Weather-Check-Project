**# 🌤 Weather Search Application**



**🔗 \*\*Demo \& Screenshots\*\***  

👉 https://drive.google.com/drive/folders/1jRAbjCNQwXg1govmDxQLYXZ71zuvkMGv



---



**## 📖 Project Overview**



A full-stack \*\*Weather Search Application\*\* that allows users to search for real-time weather information for any city.



The application integrates the \*\*OpenWeatherMap API\*\*, implements a \*\*multi-level caching strategy\*\* for performance optimization, and presents weather data using a \*\*modern, animated UI\*\*.



This project demonstrates:



\- Clean REST API design

\- Performance optimization using caching

\- Scalable backend architecture

\- Polished frontend UI \& UX



---



**## ✨ Key Features**



\- 🔍 Search current weather by city name  

\- ⚡ Fast responses using multi-level caching  

\- 🌡 Temperature, feels-like, humidity, wind speed, conditions  

\- 🖼 Dynamic weather icons  

\- ⏳ Loading spinner with graceful error handling  

\- 🎨 Glassmorphism UI with smooth animations  

\- 🧠 Clean and extensible REST API  

\- 💾 Database-backed cache for persistence  



---



**## 🏗 System Architecture**



Frontend (Angular)

|

| HTTP Request

v

Backend (Spring Boot REST API)

|

| Fetch / Cache

v

OpenWeatherMap API





---



**## 🧩 Tech Stack**



**### 🔹 Backend**



| Technology         | Purpose                 |

|--------------------|-------------------------|

| Java 21            | Core backend language   |

| Spring Boot 3      | REST API framework      |

| Spring Data JPA    | Database interaction    |

| MySQL              | Persistent cache storage|

| Caffeine Cache     | In-memory caching       |

| OpenWeatherMap API | Weather data provider   |



**### 🔹 Frontend**



| Technology                      | Purpose              |

|---------------------------------|----------------------|

| Angular (Standalone Components) | UI framework         |

| HTML5                           | Markup               |

| CSS3                            | Styling \& animations |

| TypeScript                      | Frontend logic       |



---



**## 🌐 External API Used**



\*\*OpenWeatherMap – Current Weather API\*\*  

🔗 https://openweathermap.org/current



---



**## 🔁 End-to-End Application Flow**



**### 1️⃣ User Interaction**

\- User enters a city name

\- Clicks \*Search\* or presses \*Enter\*



**### 2️⃣ Frontend (Angular)**

\- Sends request:

GET /api/weather/{city}



\- Displays loading spinner



**### 3️⃣ Backend Processing**



| Step | Action                                 |

|-----|-----------------------------------------|

| 1   | Check in-memory (Caffeine) cache            |

|  2  | If not found → check MySQL DB cache     |

| 3 | If expired → call OpenWeatherMap API |

| 4 | Save response in DB + in-memory cache|

| 5 | Convert response to DTO              |

| 6 | Send JSON response                   |



**### 4️⃣ Response to UI**

\- Weather data rendered

\- Weather icon displayed

\- Spinner stops

\- Errors handled gracefully



---



**## ⚡ Caching Strategy**



\### 🔹 In-Memory Cache (Caffeine)

\- Fastest access

\- Max entries: 100

\- Expiry: 10 minutes



\### 🔹 Database Cache (MySQL)

\- Persistent across restarts

\- Prevents repeated external API calls

\- Expiry handled using `cachedAt`



✅ \*\*Two-level caching ensures high performance \& reliability\*\*



---



**## 📡 REST API Design**



\### 🔹 Get Weather by City



\*\*GET\*\* `/api/weather/{city}`



Example:

GET http://localhost:8080/api/weather/Pune





\#### ✅ Success Response (200 OK)

```json

{

&nbsp; "city": "Pune",

&nbsp; "country": "IN",

&nbsp; "temperature": 28.4,

&nbsp; "feelsLike": 29.1,

&nbsp; "humidity": 55,

&nbsp; "weather": "Clouds",

&nbsp; "description": "scattered clouds",

&nbsp; "windSpeed": 3.6,

&nbsp; "icon": "03d"

}

❌ Error Response (404)

json



{

&nbsp; "timestamp": "2026-01-02T15:30:12",

&nbsp; "status": 404,

&nbsp; "error": "City Not Found",

&nbsp; "message": "City XYZ not found",

&nbsp; "path": "/api/weather/XYZ"

}





**🎨 UI Highlights**

Glassmorphism weather card



Animated sun \& clouds



Smooth fade-in animations



Weather icons from OpenWeatherMap



Loading spinner during API calls



Clean \& responsive layout



**⚙️ How to Run Locally**

**🔧 Backend Setup**

cd backend

Create database:



sql



CREATE DATABASE weatherdb;

Set environment variable:





export WEATHER\_API\_KEY=your\_api\_key\_here



Run application:

mvn spring-boot:run

Backend URL:

http://localhost:8080



**🎨 Frontend Setup**

cd frontend

npm install

ng serve

Frontend URL:

http://localhost:4200





**🧪 Testing**

Backend: Spring Boot test framework



Frontend: Angular tests using HttpClientTestingModule



**🔒 Security Notes**

API key is not committed



Environment variables used for secrets



CORS configured for local frontend access



**👩‍💻 Author**

Sanchita Devkar

