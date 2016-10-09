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
    private Long nbPushedDocuments = 0L;

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
        client = TransportClient.builder().settings(settings).build();
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

        nbPushedDocuments += docs.size();
        logger.info("{} documents pushed", nbPushedDocuments);
    }

    private List<ElasticsearchLogDocument> constructLogDocuments() {
        List<ElasticsearchLogDocument> results = new ArrayList<>();

        for (int i = 1; i <= 500; i++) {
            results.add(
                    new ElasticsearchLogDocument()
                            .setCountry("BE")
                            .setIpAddress(getRandomIp())
                            .setLogDate(getRandomDate())
            );
        }
        return results;
    }

    private DateTime getRandomDate() {
        DateTime date = new DateTime();
        date = date.year().setCopy(2016);

        Integer month = getRandomMonth();
        Integer dayOfMonth = getRandomDay(month);

        date = date.monthOfYear().setCopy(month);
        date = date.dayOfMonth().setCopy(dayOfMonth);
        date = date.hourOfDay().setCopy(getRandomHour());
        date = date.minuteOfHour().setCopy(getRandomMinute());
        date = date.secondOfMinute().setCopy(getRandomSecond());

        if (date.isAfterNow()){
            date = date.year().setCopy(2015);
        }

        return date;

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

    private String getRandomIp() {
        StringBuffer ipAddress = new StringBuffer();
        ipAddress
                // FR .append(getRandomNumber(90))
                .append(getRandomNumber(80))
                .append(".")
                .append(getRandomNumber(44))
                // FR .append(getRandomNumber(64))
                .append(".")
                .append(getRandomNumber(80))
                // FR .append(getRandomNumber(120))
                .append(".")
                .append(getRandomNumber(130))
                // FR .append(getRandomNumber(200))
        ;

        return ipAddress.toString();
    }

    private Integer getRandomNumber(Integer high) {
        Random r = new Random();
        Integer low = 1;
        return r.nextInt(high - low) + low;
    }
}
