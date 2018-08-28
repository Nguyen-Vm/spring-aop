package org.linker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author RWM
 * @date 2018/8/28
 */
@Document(indexName = "blog_index", type = "blog", refreshInterval = "-1")
public class Blog {

    @Id
    public int id;

    public String name;

    @Field(type = FieldType.Date)
    public Date createTime;
}
