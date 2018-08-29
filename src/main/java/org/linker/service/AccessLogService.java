package org.linker.service;

import com.google.common.collect.Lists;
import org.linker.model.AccessLog;
import org.linker.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author RWM
 * @date 2018/8/28
 */
@Service
public class AccessLogService {

    @Autowired
    AccessLogRepository accessLogRepository;

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

    public List<AccessLog> find(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        List<AccessLog> accessLogList = Lists.newArrayList();
        Iterator<AccessLog> iterator = accessLogRepository.findAll(pageable).iterator();
        while (iterator.hasNext()) {
            accessLogList.add(iterator.next());
        }
        return accessLogList;
    }
}
