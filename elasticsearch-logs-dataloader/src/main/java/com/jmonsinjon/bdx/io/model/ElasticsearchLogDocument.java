package com.jmonsinjon.bdx.io.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by jmonsinjon on 18/09/16.
 */
@Document(indexName = "timelion-demo", type = "navigation_log", shards = 1, replicas = 0)
public class ElasticsearchLogDocument {

    @Id
    private String Id;

    @Field(type = FieldType.Date)
    private DateTime logDate;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String ipAddress;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String country;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public DateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(DateTime logDate) {
        this.logDate = logDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
