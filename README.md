# Food Finder App

## Project Summary
Food Finder is a desktop application designed to streamline the dining experience. By aggregating data from three APIs (Yelp, Google Maps, Spoonacular), the app allows users to search for restaurants by location, explore detailed menus with real-time pricing, read authentic user reviews, and manage their own ratings and user profile.

This project strictly adheres to **Clean Architecture** principles, ensuring a separation of concerns between the UI, business logic, and data access layers.

## Team Members & User Stories

All User Stories have been successfully implemented and tested.

| Team Member | Feature / User Story          | Description |
| :--- |:------------------------------| :--- |
| **Ruhan Kiani** | **Restaurant Search**         | Search for dining options near a specific address using Geocoding & Yelp Fusion API. |
| **Vignesh Khajuria** | **View Menu**          | Retrieve and display detailed menu items and prices via Spoonacular API. |
| **Mertali Muhsin Tercan** | **Search Menu Items**  | Filter and search for specific food items (e.g., "burger", "vegan") within a restaurant's menu. |
| **Bowen Zhao** | **View Reviews**       | Fetch and display user reviews and ratings from the Yelp API. |
| **Arthur Jiang** | **Rate Restaurant**    | Allow users to submit their own 1-5 star ratings, persisted to a local CSV database. |
| **Team** | **User Management**           | Secure Login and Signup functionality with password verification and user session management. |

## Technical Architecture & Design
The application is built using **Java** and **Swing** for the GUI, following the **Clean Architecture** pattern:
- **Entity Layer:** Core business objects (`Restaurant`, `User`, `MenuItem`).
- **Use Case Layer:** Business rules and application logic (Interactors).
- **Interface Adapters:** Controllers, Presenters, and ViewModels that convert data between the View and Use Cases.
- **Data Access:** Implementations for external APIs (Yelp, Google, Spoonacular) and local storage (CSV).


## API Configuration
To run the application, you must verify your API keys. **Note:** Sensitive keys are not committed to Git.

1. Locate `src/main/resources/config.properties.template`.
2. Copy/Rename this file to `src/main/resources/config.properties`.
3. Enter your valid API keys in the file:
   ```properties
   yelp.api.key=YOUR_YELP_API_KEY
   google.api.key=YOUR_GOOGLE_MAPS_API_KEY
   spoonacular.api.key=YOUR_SPOONACULAR_API_KEY
   spoonacular.host=api.spoonacular.com