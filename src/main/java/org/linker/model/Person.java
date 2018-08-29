package org.linker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;

/**
 * @author RWM
 * @date 2018/8/28
 */
@Document(indexName = "person_index", type = "person", refreshInterval = "-1")
public class Person implements Serializable {

    @Id
    public int id;

    public String name;

    public String phone;

    @GeoPointField
    public String address;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
