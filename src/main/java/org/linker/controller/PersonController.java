package org.linker.controller;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.linker.model.Person;
import org.linker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author RWM
 * @date 2018/8/28
 */
@RestController
public class PersonController {

    @Autowired
    PersonService personService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping("/add")
    public Object add() {
        Person person = new Person();
        person.id = 1;
        person.name = "名字";
        person.phone = "电话";
        person.address = "39.929986,116.395645";
        return personService.add(person);
    }

    @GetMapping("/bulk")
    public Object bulk() {
        double lat = 39.929986;
        double lon = 116.395645;

        Long nowTime = System.currentTimeMillis();
        List<Person> personList = new ArrayList<>(90000);
        for (int i = 10000; i < 100000; i++) {
            double max = 0.00001;
            double min = 0.000001;
            Random random = new Random();
            double s = random.nextDouble() % (max - min + 1) + max;
            DecimalFormat df = new DecimalFormat("######0.000000");
            String lons = df.format(s + lon);
            String lats = df.format(s + lat);
            Double dlon = Double.valueOf(lons);
            Double dlat = Double.valueOf(lats);

            Person person = new Person();
            person.id = i;
            person.name = "名字" + i;
            person.phone = "电话" + i;
            person.address = dlat + "," + dlon;

            personList.add(person);
        }
        personService.bulkIndex(personList);
        System.out.println("耗时: " + (System.currentTimeMillis() - nowTime)/1000 + "s");
        return "添加数据成功!";
    }

    @GetMapping("/query")
    public Object query() {
        double lat = 39.929986;
        double lon = 116.395645;

        Long nowTime = System.currentTimeMillis();

        GeoDistanceQueryBuilder builder = QueryBuilders.geoDistanceQuery("address").point(lat, lon).distance(100, DistanceUnit.METERS);

        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("address", lat, lon).unit(DistanceUnit.METERS).order(SortOrder.ASC);

        Pageable pageable = new PageRequest(0, 10);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withFilter(builder).withSort(sortBuilder).withPageable(pageable);
        SearchQuery searchQuery = queryBuilder.build();

        List<Person> personList = elasticsearchTemplate.queryForList(searchQuery, Person.class);

        System.out.println("耗时: " + (System.currentTimeMillis() - nowTime)/1000 + "s");
        return personList;

    }
}
