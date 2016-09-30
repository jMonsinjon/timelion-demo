package com.jmonsinjon.bdx.io.scheduled;

import com.jmonsinjon.bdx.io.service.ElasticsearchLogDocumentService;
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


    private ElasticsearchLogDocumentService elasticsearchLogDocumentService;

    Logger logger = LoggerFactory.getLogger(ElasticsearchContinuousLoader.class);

    @Autowired
    public ElasticsearchContinuousLoader(ElasticsearchLogDocumentService elasticsearchLogDocumentService) {
        this.elasticsearchLogDocumentService = elasticsearchLogDocumentService;
    }

    @Scheduled(fixedDelay = 1000)
    public void loadData() {
        logger.info(Calendar.getInstance().getTime().toString());
        elasticsearchLogDocumentService.pushDocuments();
    }
}
