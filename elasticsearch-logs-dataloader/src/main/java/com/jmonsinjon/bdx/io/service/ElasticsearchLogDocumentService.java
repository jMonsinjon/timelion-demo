package com.jmonsinjon.bdx.io.service;

import com.jmonsinjon.bdx.io.config.LoaderConfiguration;
import com.jmonsinjon.bdx.io.model.ElasticsearchLogDocument;
import org.elasticsearch.common.inject.Inject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jmonsinjon on 28/09/16.
 */
@Service
public class ElasticsearchLogDocumentService {

    private LoaderConfiguration loaderConfiguration;
    private List<Integer> availableMonths = new ArrayList<>();

    @Autowired
    public ElasticsearchLogDocumentService(LoaderConfiguration loaderConfiguration) {
        this.loaderConfiguration = loaderConfiguration;
    }

    @PostConstruct
    private void initMonth() {
        loaderConfiguration.getMonthBoost().forEach((key, value) -> {
            for (int i = 0; i < value; i++) {
                availableMonths.add(key);
            }
        });
    }

    public List<ElasticsearchLogDocument> constructLogDocuments() {
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
