package com.example.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDto {

    private String city;
    private String country;

    private double temperature;
    private double feelsLike;
    private int humidity;

    private String weather;
    private String description;
    private double windSpeed;

    private String icon;                // ✅ OpenWeather icon
    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cachedAt;
}
