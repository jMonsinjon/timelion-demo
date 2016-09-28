package com.jmonsinjon.bdx.io.scheduled;

import com.jmonsinjon.bdx.io.repository.LogsRepository;
import com.jmonsinjon.bdx.io.service.ElasticsearchLogDocumentService;
import org.elasticsearch.common.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@EnableScheduling
@Component
public class ElasticsearchContinuousLoader {


    private LogsRepository logsRepository;
    private ElasticsearchLogDocumentService elasticsearchLogDocumentService;

    Logger logger = LoggerFactory.getLogger(ElasticsearchContinuousLoader.class);

    @Autowired
    public ElasticsearchContinuousLoader(LogsRepository logsRepository, ElasticsearchLogDocumentService elasticsearchLogDocumentService) {
        this.elasticsearchLogDocumentService = elasticsearchLogDocumentService;
        this.logsRepository = logsRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void loadData() {
        logger.info(Calendar.getInstance().getTime().toString());
        logsRepository.save(elasticsearchLogDocumentService.constructLogDocuments());
    }
}
