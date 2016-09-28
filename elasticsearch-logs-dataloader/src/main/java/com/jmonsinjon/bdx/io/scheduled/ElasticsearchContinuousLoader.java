package com.jmonsinjon.bdx.io.scheduled;

import com.jmonsinjon.bdx.io.config.LoaderConfiguration;
import com.jmonsinjon.bdx.io.model.ElasticsearchLogDocument;
import com.jmonsinjon.bdx.io.repository.LogsRepository;
import org.elasticsearch.common.inject.Inject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@EnableScheduling
@Component
public class ElasticsearchContinuousLoader {

    private LoaderConfiguration loaderConfiguration;

    private LogsRepository logsRepository;

    Logger logger = LoggerFactory.getLogger(ElasticsearchContinuousLoader.class);

    @Inject
    public ElasticsearchContinuousLoader(LoaderConfiguration loaderConfiguration, LogsRepository logsRepository) {
        this.loaderConfiguration = loaderConfiguration;
        this.logsRepository = logsRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void loadData() {
        logger.info(Calendar.getInstance().getTime().toString());
        logsRepository.save(constructLogDocuments());
    }

    private List<ElasticsearchLogDocument> constructLogDocuments() {
        List<ElasticsearchLogDocument> results = new ArrayList<>();

        for (int i=1; i<=250; i++)
        results.add(
                new ElasticsearchLogDocument().setCountry("FR").setIpAddress("127.0.0." + i).setLogDate(DateTime.now())
        );

        return results;
    }
}
