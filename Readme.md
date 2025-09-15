# NIT3213 Android Application Project

**App:** NIT3213 Connect  
**Student:** Mehakpreet Kaur  
**Student ID:** s8074762  
**Demo/Test Keypass:** `animals`

## Overview
This Android app demonstrates API integration, clean UI, and modern Android practices.  
It implements three screens — **Login → Dashboard → Details** — and uses the official NIT3213 API to authenticate and fetch data. The project showcases **Dependency Injection (Hilt)** and **unit testing** for core logic.

## Features
- **Login Screen:**  
  Authenticate with your **first name** and **student ID (without the ‘s’)**. On success, you’re navigated to the Dashboard.
- **Dashboard Screen:**  
  Loads entities from the API using a `RecyclerView`. Tap an item to view more details.
- **Details Screen:**  
  Clear, readable view of the selected entity’s information, including its description.
- **Architecture & Tooling:**  
  MVVM, Repository pattern, Hilt (DI), Retrofit/OkHttp/Gson, Coroutines/StateFlow, ViewBinding, Material Design.

---

## API Details
- **Base URL:** `https://nit3213api.onrender.com`

### Login Endpoint
- **Paths:** `/footscray/auth`, `/sydney/auth`, `/br/auth`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "username": "YourFirstName",
    "password": "YourStudentIDWithoutS"
  }
### Successful Response
{
"keypass": "..."
}
#Dashboard Endpoint
https://nit3213api.onrender.com/dashboard/animals
### Successful Response
{
"entities": [
{
"species": "Giant Panda",
"scientificName": "Ailuropoda melanoleuca",
"habitat": "Temperate forests",
"diet": "Herbivore",
"conservationStatus": "Vulnerable",
"averageLifespan": 20,
"description": "A bear native to China, known for its distinctive black and white coat and bamboo diet."
}
],
"entityTotal": 1
}
### Project Structure

LoginActivity — handles login and navigation on success.
DashboardActivity — shows a list of entities using RecyclerView.
DetailsActivity — displays full details for a selected item.
ViewModels (MVVM) — manage UI state & call the repository (Coroutines + StateFlow).
Repository & API — NitRepository (impl) + Retrofit service & DTOs.
Dependency Injection — Dagger Hilt modules for Retrofit/OkHttp/Repo/ViewModels.
Unit Tests — ViewModel tests for success & error flows.

## Technical Requirements
Dependency Injection: Hilt for wiring Retrofit/OkHttp/Repository and ViewModels.
RecyclerView: Dashboard list of entities.
Unit Testing: JUnit tests for critical ViewModels (login, dashboard).
Clean Code: MVVM + Repository, separation of concerns, simple mappers.
Git: Clear, meaningful commit messages and a tidy history.

## Installation and Steps
Installation & Setup
Clone the repository
git clone https://github.com/Mehakpreet973/nit3213-connect.git

Open in Android Studio (Giraffe/Koala or newer) and let Gradle sync.
Run on an emulator/device (API 24+).
