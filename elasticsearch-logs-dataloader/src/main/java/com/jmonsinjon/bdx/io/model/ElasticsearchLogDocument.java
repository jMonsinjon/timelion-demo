package com.jmonsinjon.bdx.io.model;

import org.joda.time.DateTime;

/**
 * Created by jmonsinjon on 18/09/16.
 */
public class ElasticsearchLogDocument {

    private DateTime logDate;

    private String ipAddress;

    private String country;

    public String getLogDate() {
        return logDate.toString("dd-MM-yyyy HH:mm:ss.SSSS");
    }

    public ElasticsearchLogDocument setLogDate(DateTime logDate) {
        this.logDate = logDate;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ElasticsearchLogDocument setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ElasticsearchLogDocument setCountry(String country) {
        this.country = country;
        return this;
    }
}
