import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WeatherService } from '../services/weather.service';

@Component({
  selector: 'app-weather',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.css']
})
export class WeatherComponent {

  city: string = '';
  weatherData: any = null;
  errorMsg: string = '';
  loading: boolean = false;

  constructor(private weatherService: WeatherService) {}

  searchWeather() {

    if (!this.city.trim()) {
      this.errorMsg = 'Please enter a city name';
      this.weatherData = null;
      return;
    }

    this.loading = true;       // 🔵 start loader
    this.errorMsg = '';
    this.weatherData = null;

    this.weatherService.getWeather(this.city).subscribe({
      next: (data) => {
        console.log('Weather response:', data); // ✅ CONFIRMED WORKING
        this.weatherData = data;               // ✅ BIND DATA
        this.loading = false;                  // ✅ STOP loader
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = err?.error?.message || 'Failed to fetch weather';
        this.loading = false;
      }
    });
  }
}
