package com.jmonsinjon.bdx.io.repository;

import com.jmonsinjon.bdx.io.model.ElasticsearchLogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by jmonsinjon on 18/09/16.
 */
public interface LogsRepository extends ElasticsearchRepository<ElasticsearchLogDocument, String> {

}
