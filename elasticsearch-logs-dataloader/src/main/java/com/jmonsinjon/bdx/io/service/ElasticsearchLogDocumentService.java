package com.jmonsinjon.bdx.io.service;

import com.jmonsinjon.bdx.io.config.LoaderConfiguration;
import com.jmonsinjon.bdx.io.model.ElasticsearchLogDocument;
import com.jmonsinjon.bdx.io.settings.ElasticsearchNode;
import com.jmonsinjon.bdx.io.settings.ElasticsearchServerSettings;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jmonsinjon on 28/09/16.
 */
@Service
public class ElasticsearchLogDocumentService {

    private LoaderConfiguration loaderConfiguration;
    private ElasticsearchServerSettings elasticsearchServerSettings;
    private List<Integer> availableMonths = new ArrayList<>();
    private TransportClient client;
    private ObjectMapper mapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(ElasticsearchLogDocumentService.class);

    @Autowired
    public ElasticsearchLogDocumentService(LoaderConfiguration loaderConfiguration, ElasticsearchServerSettings elasticsearchServerSettings) {
        this.loaderConfiguration = loaderConfiguration;
        this.elasticsearchServerSettings = elasticsearchServerSettings;
    }

    @PostConstruct
    private void initMonth() throws UnknownHostException {
        loaderConfiguration.getMonthBoost().forEach((key, value) -> {
            for (int i = 0; i < value; i++) {
                availableMonths.add(key);
            }
        });

        Settings settings = Settings.builder()
                .put("cluster.name", elasticsearchServerSettings.getClusterName())
                .put("client.transport.sniff", true)
                .build();
        client = new PreBuiltTransportClient(settings);
        for (ElasticsearchNode esNode : elasticsearchServerSettings.getNodes()) {
            client.addTransportAddress(
                    new InetSocketTransportAddress(
                            InetAddress.getByName(esNode.getHost()), esNode.getPort()));
        }
    }

    public void pushDocuments() {
        List<ElasticsearchLogDocument> docs = constructLogDocuments();

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (ElasticsearchLogDocument doc:docs) {
            try {
                bulkRequest.add(client.prepareIndex(elasticsearchServerSettings.getIndexName(), elasticsearchServerSettings.getIndexName())
                        .setSource(mapper.writeValueAsString(doc)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            logger.error(bulkResponse.buildFailureMessage());
        }
    }

    private List<ElasticsearchLogDocument> constructLogDocuments() {
        List<ElasticsearchLogDocument> results = new ArrayList<>();

        for (int i = 1; i <= 250; i++) {
            Integer month = getRandomMonth();
            Integer dayOfMonth = getRandomDay(month);
            DateTime date = new DateTime();
            date.year().setCopy(2016);
            date.monthOfYear().setCopy(month);
            date.dayOfMonth().setCopy(dayOfMonth);
            date.hourOfDay().setCopy(getRandomHour());
            date.minuteOfHour().setCopy(getRandomMinute());
            date.secondOfMinute().setCopy(getRandomSecond());

            results.add(
                    new ElasticsearchLogDocument()
                            .setCountry("FR")
                            .setIpAddress("127.0.0." + i)
                            .setLogDate(date)
            );
        }
        return results;
    }

    private Integer getRandomMonth() {
        Integer monthIndex = getRandomNumber(availableMonths.size());
        return availableMonths.get(monthIndex);
    }

    private Integer getRandomDay(Integer month) {
        return getRandomNumber(loaderConfiguration.getDaysForMonth().get(month));
    }

    private Integer getRandomHour() {
        return getRandomNumber(24);
    }

    private Integer getRandomMinute() {
        return getRandomNumber(60);
    }

    private Integer getRandomSecond() {
        return getRandomNumber(60);
    }

    private Integer getRandomNumber(Integer high) {
        Random r = new Random();
        Integer low = 1;
        return r.nextInt(high - low) + low;
    }
}
