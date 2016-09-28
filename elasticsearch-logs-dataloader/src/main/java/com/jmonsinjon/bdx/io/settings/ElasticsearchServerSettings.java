package com.jmonsinjon.bdx.io.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchServerSettings {
	private List<ElasticsearchNode> nodes;
	private String clusterName;
	private String indexName;
	private String documentType;
	
	public List<ElasticsearchNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<ElasticsearchNode> nodes) {
		this.nodes = nodes;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
}
