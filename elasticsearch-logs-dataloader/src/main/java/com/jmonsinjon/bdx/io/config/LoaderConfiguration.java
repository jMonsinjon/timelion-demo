package com.jmonsinjon.bdx.io.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.NoRepositoryBean;

import java.net.InetAddress;
import java.util.List;
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
