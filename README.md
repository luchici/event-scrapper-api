# **API Description**

This is a simple API who, based on a city name will return geolocation, like latitude and longitude data about that city and how is the weather in that city. This project starts as a REST API, but later for the sake of simplicity I add a home page. The mani external APIs are:

OpenWeather Geocoding API - who based on city name will return the geolocation coordinations.
OpenWeather Current Weather API - who based on city geolocation coordination will return the current weather

# **Endpoints**

For best experience use Mozilla Firefox, Firefox has a built-in JSON viewer that activates when a server sends a file as "application/json" document.

Return type, for all endpoints is JSON

**"/api/city/{cityName}**" - return data about the chosen city(default Craiova).
![](C:\Users\abcd123\Desktop\api-city-craiova.PNG)

**"/api/cities"** - return all cities

**"/api/weather/{cityName}"** - return weather in the chosen city(default Craiova)
![](C:\Users\abcd123\Desktop\api-weather-craiova.PNG)

**"/admin"** - return actuator endpoints

# Project roadmap

 - **Improve security**
    - Hide the API key
    - Add a log in form, and the ability to create account
 - **Add iaBilet.ro data ALMOST THERE!**
    - Optimize the scrapping process
    - Build the database relation between the new entities
    - Display city events on the main page
 - **Build a JavaScript frontend**
    - Choose between Angular or React
    - Add GMaps, and pinpoint events on map
 - **Build a Twitter Bot, who will regularly tweet all the events in a city**

