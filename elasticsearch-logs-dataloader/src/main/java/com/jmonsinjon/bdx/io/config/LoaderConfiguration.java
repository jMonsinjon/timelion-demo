package com.jmonsinjon.bdx.io.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.NoRepositoryBean;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@EnableElasticsearchRepositories(basePackages = "com.jmonsinjon.bdx.io.repository")
// @ComponentScan(basePackages = {"com.baeldung.spring.data.es.service"})
@Configuration(value = "loader")
public class LoaderConfiguration {
    private List<String> countries;

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    @Bean
    public Client client() {
        TransportClient client = new TransportClient(new InetSocketTransportAddress("localhost", 9300));
        Settings settings = new SettingsB
        // TransportClient.builder()
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }
}
