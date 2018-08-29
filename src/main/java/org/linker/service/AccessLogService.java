package org.linker.service;

import org.linker.model.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

/**
 * @author RWM
 * @date 2018/8/28
 */
@Service
public class AccessLogService {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public void save(AccessLog accessLog) {
        System.out.println(accessLog);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(accessLog.id);
        indexQuery.setObject(accessLog);
        indexQuery.setIndexName("log");
        indexQuery.setType("access");
        elasticsearchTemplate.index(indexQuery);
    }
}
