package com.example.weather.service;

import com.example.weather.dto.WeatherResponseDto;
import com.example.weather.entity.WeatherCache;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.repository.WeatherCacheRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherCacheRepository weatherCacheRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private static final int CACHE_EXPIRY_MINUTES = 10;
    private static final int MAX_CACHE_ENTRIES = 1000;

    /**
     * MAIN METHOD
     * Spring Cache → DB Cache → External API
     */
    @Override
    @Cacheable(
            value = "weatherCache",
            key = "#city.toLowerCase()",
            unless = "#result == null"
    )
    public WeatherResponseDto getWeather(String city) {

        log.info("➡️ REQUEST RECEIVED for city: {}", city);

        // 1️⃣ DB Cache
        Optional<WeatherCache> cacheOpt =
                weatherCacheRepository.findByCityIgnoreCase(city);

        if (cacheOpt.isPresent()) {
            WeatherCache cache = cacheOpt.get();

            if (cache.getCachedAt() != null &&
                cache.getCachedAt().isAfter(
                        LocalDateTime.now().minusMinutes(CACHE_EXPIRY_MINUTES))) {

                log.info("🟡 RETURNING DATA FROM DB CACHE for city: {}", city);
                return mapToDto(cache.getWeatherJson(), cache.getCachedAt());
            }

            log.info("🟠 DB CACHE EXPIRED for city: {}", city);
        }

        // 2️⃣ External API
        log.info("🔵 CALLING EXTERNAL WEATHER API for city: {}", city);

        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";
        String response;

        try {
            response = restTemplate.getForObject(url, String.class);

        } catch (HttpClientErrorException.NotFound e) {
            throw new CityNotFoundException(city);

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Weather API error: " + e.getStatusCode());

        } catch (Exception e) {
            throw new RuntimeException("Weather service unavailable");
        }

        // 3️⃣ Save DB cache
        WeatherCache cache = cacheOpt.orElse(new WeatherCache());
        cache.setCity(city.toLowerCase());
        cache.setWeatherJson(response);
        cache.setCachedAt(LocalDateTime.now());

        if (weatherCacheRepository.count() >= MAX_CACHE_ENTRIES) {
            weatherCacheRepository.deleteAll();
            log.warn("⚠️ CACHE CLEARED: MAX LIMIT REACHED");
        }

        weatherCacheRepository.save(cache);

        log.info("✅ DATA SAVED TO DB CACHE for city: {}", city);

        return mapToDto(response, cache.getCachedAt());
    }

    /**
     * JSON → DTO
     */
    private WeatherResponseDto mapToDto(String json, LocalDateTime cachedAt) {
        try {
            JsonNode root = objectMapper.readTree(json);

            return new WeatherResponseDto(
                    root.get("name").asText(),
                    root.get("sys").get("country").asText(),
                    root.get("main").get("temp").asDouble(),
                    root.get("main").get("feels_like").asDouble(),
                    root.get("main").get("humidity").asInt(),
                    root.get("weather").get(0).get("main").asText(),
                    root.get("weather").get(0).get("description").asText(),
                    root.get("wind").get("speed").asDouble(),
                    root.get("weather").get(0).get("icon").asText(),
                    cachedAt
            );

        } catch (Exception e) {
            throw new RuntimeException("Error parsing weather response");
        }
    }
}
