package com.jmonsinjon.bdx.io.config;

import com.jmonsinjon.bdx.io.settings.ElasticsearchNode;
import com.jmonsinjon.bdx.io.settings.ElasticsearchServerSettings;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jeremie.monsinjon on 28/09/2016.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jmonsinjon.bdx.io")
public class ElasticsearchConfiguration {

    private ElasticsearchServerSettings elasticsearchServerSettings;

    @Autowired
    public ElasticsearchConfiguration(ElasticsearchServerSettings elasticsearchServerSettings) {
        this.elasticsearchServerSettings = elasticsearchServerSettings;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }

    @Bean
    public Client client() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("client.transport.sniff", true)
                .put("cluster.name", elasticsearchServerSettings.getClusterName())
                .build();
        TransportClient esClient = TransportClient.builder().settings(settings).build();
        for (ElasticsearchNode esNode : elasticsearchServerSettings.getNodes()) {
            esClient.addTransportAddress(
                    new InetSocketTransportAddress(
                            InetAddress.getByName(esNode.getHost()), esNode.getPort()));
        }
        return esClient;
    }
}
