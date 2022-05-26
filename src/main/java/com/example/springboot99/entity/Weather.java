package com.example.springboot99.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "weather")
@Data
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;

    private String description;
    private int temp;
    private int bodyFeels;
    private int tempMin;
    private int tempMax;
    private LocalDate localDate = LocalDate.now();
    @JsonProperty("name")
    private String cityName;
    @OneToOne(mappedBy = "weather",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private City city;

    @JsonProperty("weather")
    private void setDesc(List<Map<String, String>> weather) {
        this.description = weather.get(0).get("description");
    }

    @JsonProperty("main")
    private void setMain(Map<String, Double> main) {
        this.temp = tempConvertions(main.get("temp"));
        this.bodyFeels = tempConvertions(main.get("feels_like"));
        this.tempMin = tempConvertions(main.get("temp_min"));
        this.tempMax = tempConvertions(main.get("temp_max"));
    }

    private int tempConvertions(Double temp) {
        Double d = temp - 273.5;
        return d.intValue();
    }

    public void setCity(City city) {
        city.setWeather(this);
        this.city = city;
    }

    @JsonBackReference
    public City getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", temp=" + temp +
                ", bodyFeels=" + bodyFeels +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", localDate=" + localDate +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
