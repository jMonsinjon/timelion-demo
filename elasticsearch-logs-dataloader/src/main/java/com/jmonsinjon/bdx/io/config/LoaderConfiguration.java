package com.jmonsinjon.bdx.io.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@Configuration
@ConfigurationProperties(prefix = "loader")
public class LoaderConfiguration {
    private String country;
    private String year;
    private Map<Integer, Integer> monthBoost;
    private Map<Integer, Integer> daysForMonth;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<Integer, Integer> getMonthBoost() {
        return monthBoost;
    }

    public void setMonthBoost(Map<Integer, Integer> monthBoost) {
        this.monthBoost = monthBoost;
    }

    public Map<Integer, Integer> getDaysForMonth() {
        return daysForMonth;
    }

    public void setDaysForMonth(Map<Integer, Integer> daysForMonth) {
        this.daysForMonth = daysForMonth;
    }
}
