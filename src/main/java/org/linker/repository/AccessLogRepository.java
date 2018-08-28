package org.linker.repository;

import org.linker.model.AccessLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author RWM
 * @date 2018/8/28
 */
public interface AccessLogRepository extends ElasticsearchRepository<AccessLog, String> {
}
