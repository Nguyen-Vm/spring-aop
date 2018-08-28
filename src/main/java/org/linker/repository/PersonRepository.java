package org.linker.repository;

import org.linker.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author RWM
 * @date 2018/8/28
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Integer> {
}
