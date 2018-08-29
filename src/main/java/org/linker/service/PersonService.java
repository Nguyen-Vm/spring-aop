package org.linker.service;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.linker.model.Person;
import org.linker.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * `
 *
 * @author RWM
 * @date 2018/8/28
 */
@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    private static final String PERSON_INDEX_NAME = "person_index";
    private static final String PERSON_INDEX_TYPE = "person";

    public String add(Person person) {
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(person.id + "");
        indexQuery.setObject(person);
        indexQuery.setIndexName(PERSON_INDEX_NAME);
        indexQuery.setType(PERSON_INDEX_TYPE);
        return elasticsearchTemplate.index(indexQuery);
    }

    public void bulkIndex(List<Person> personList) {
        int counter = 0;
        try {
            if (!elasticsearchTemplate.indexExists(PERSON_INDEX_NAME)) {
                elasticsearchTemplate.createIndex(PERSON_INDEX_NAME);
            }
            List<IndexQuery> queries = new ArrayList<>();
            for (Person person : personList) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(person.id + "");
                indexQuery.setObject(person);
                indexQuery.setIndexName(PERSON_INDEX_NAME);
                indexQuery.setType(PERSON_INDEX_TYPE);

                queries.add(indexQuery);
                if (counter % 500 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                    System.out.println("bulkIndex counter: " + counter);
                }
                counter++;
            }
            if (queries.size() > 0) {
               elasticsearchTemplate.bulkIndex(queries);
            }
        } catch (Exception e) {
            System.out.println("IndexerService.bulkIndex e;" + e.getMessage());
            throw e;
        }
    }


    public Object find() {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder("名字");
        Iterable<Person> result = personRepository.search(builder);
        Iterator<Person> iterator = result.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        return null;
    }
}
