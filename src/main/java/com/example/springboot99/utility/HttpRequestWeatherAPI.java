package com.example.springboot99.utility;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.util.NumberPointType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class HttpRequestWeatherAPI {

//    TODO: HIDE THE API KEY
    private static final String API = "6cabf84a4fba39918788106bb0cea492";
    private static final ObjectMapper  mapper = new ObjectMapper();
    private static final LocalDate date = LocalDate.now();

    public static City getTheCity(String cityName){
        String cityNameProcess = cityName.substring(0,1).toUpperCase()+cityName.substring(1).toLowerCase();
        String theURL = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityNameProcess + ",RO&limit=1&appid=" + API;

        HttpResponse<String> response = httpRequestAPI(theURL);
        System.out.println("-----------------------------------------REQUEST FOR CITY-----------------------------------------");
        System.out.println(cityName);
        List<City> cities = null;
        try {
            cities = mapper.readValue(response.body(), new TypeReference<List<City>>() {            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Parsing error, the reposne is not good");
            System.out.println("Error parsing for City");
        } catch (NullPointerException e) {
            //        TODO: ERROR - NO CITY RETURNED
            System.out.println("+++++++++++++++++++++++++++++++++++++++"+cityName+"++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++++++++++No respose body++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        City localCity = cities.get(0);
        localCity.setCityName(cityNameProcess);
        return localCity;
        }

    public static Weather getTheWeather(City city){
        String theURL = "https://api.openweathermap.org/data/2.5/weather?lat="+city.getLat()+"&lon="+city.getLon()+"&appid="+API;
        HttpResponse<String> response = httpRequestAPI(theURL);
        System.out.println("-----------------------------------------REQUEST FOR WEATHER-----------------------------------------");
        System.out.println(city.getCityName());
        Weather weatherToday = null;
        try {
            weatherToday = mapper.readValue(response.body(), Weather.class);
            weatherToday.setLocalDate(date);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Parsing error, the reposne is not good");
            System.out.println("Error parsing for Weather");
        } catch (NullPointerException e) {
            System.out.println("No response back");
            e.printStackTrace();
        }

        return weatherToday;
    }

    public static HttpResponse<String> httpRequestAPI(String theURL) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(theURL))
                    .build();
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("1.API Key is expired," +
                    "\n2.cityNameProcess is wrong" +
                    "\n3.Some other problem with the URL");
            e.printStackTrace();
            return null;
        } catch(InterruptedException e) {
            System.out.println("Connectivity problems, try again");
            e.printStackTrace();
            return null;
        }
    }
}
