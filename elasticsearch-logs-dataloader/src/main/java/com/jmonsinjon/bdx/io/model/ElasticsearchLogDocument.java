package com.jmonsinjon.bdx.io.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@Document(indexName = "timelion-demo", type = "navigation_log", shards = 1, replicas = 0)
public class ElasticsearchLogDocument {

    @Id
    private String id;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "dd-MM-yyyy HH:mm:ss.SSSS")
    private DateTime logDate;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ipAddress;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String country;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

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
