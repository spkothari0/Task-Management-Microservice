package com.shreyas.search.repository;

import com.shreyas.search.document.TaskDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskSearchRepository extends ElasticsearchRepository<TaskDocument, String> {
}
