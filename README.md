# Fork & Code - Food Finder App

## Project Summary
Food Finder is a desktop application designed to help users find dining options. It aggregates data from multiple APIs to allow users to search for restaurants by location, view detailed menus with prices, read Yelp reviews, and make their own ratings.

## Team Members & User Stories

| Team Member | Feature / User Story | Status                    |
| :--- | :--- |:--------------------------|
| **Ruhan Kiani** | **Search Restaurants by Location** (User Story #1)<br>Find dining options near a specific address using Google Maps & Yelp. | In Progress               |
| **Vignesh Khajuria** | **View Restaurant Menu** (User Story #2)<br>View detailed menu items and prices (via Spoonacular). | In Progress               |
| **Mertali Muhsin Tercan** | **Search Menu Items** (User Story #3)<br>Search for specific food (e.g., "burger") within a menu. | In Progress               |
| **Bowen Zhao** | **View Yelp Reviews** (User Story #4)<br>Retrieve and display reviews/ratings from Yelp API. | Done (100% Test Coverage) |
| **Arthur Jiang** | **Rate a Restaurant** (User Story #5)<br>Allow users to rate a restaurant (1-5 stars) and save to local DB. | Done (100% Test Coverage) |

> **Note:** We also have **Login/Signup** implemented to support User Story #5.

## API & Data Configuration
We use the following APIs:
1.  **Yelp Fusion API:** For restaurant search, details, and reviews.
2.  **Google Maps API:** For geocoding addresses to coordinates.
3.  **Spoonacular API:** For retrieving menu items and prices.

### Important: How to Run
To run the application, you must configure your API keys locally:
1.  Locate `src/main/resources/config.properties.template`.
2.  Duplicate it and rename the file to `config.properties`.
3.  Paste your API keys into the corresponding fields.
    * *Note: `config.properties` is ignored by Git to protect API keys. DO NOT COMMIT IT.*

## Current Progress & To-Do List
**Goal:** Achieve 100% Code Coverage for **Use Case (Interactor)** and **Entity** layers.

### 1. Entity Layer Tests
- [x] **Restaurant:** `RestaurantTest.java` is complete (100% coverage).
- [ ] **User:** Need to create `UserTest.java`.
- [ ] **MenuItem:** Need to create `MenuItemTest.java`.
- [ ] **FoodFinderApp:** Need to create `FoodFinderAppTest.java`.

### 2. Use Case Layer Tests
- [x] **View Ratings:** `ViewRatingsInteractorTest.java` is complete (100% coverage).
- [x] **Star Rate:** `starRateTest.java` is complete (100% coverage).
- [x] **Login:** `LoginTest.java` is complete (100% coverage).
- [x] **Signup:** `SignupTest.java` is complete (100% coverage).
- [ ] **Menu Search:** Need `MenuSearchInteractorTest.java`.

### 3. How to Check Coverage
1.  In IntelliJ, right-click the `test/java` folder.
2.  Select **"Run 'All Tests' with Coverage"**.
3.  Ensure `entity` package and `use_case` package show 100%.

## Project Structure
- `src/main/java/app`: Main entry point.
- `src/main/java/data_access`: API implementations (Yelp, Google, Spoonacular).
- `src/main/java/entity`: All entity used in the project.
- `src/main/java/interface_adaptor`: Controllers, Presenters, and ViewModels.
- `src/main/java/use_case`: Interactors.
- `src/main/java/view`: UI components.
