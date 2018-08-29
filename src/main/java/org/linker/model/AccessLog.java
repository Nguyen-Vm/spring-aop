package org.linker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author RWM
 * @date 2018/8/28
 */
@Document(indexName = "log", type = "access", refreshInterval = "-1")
public class AccessLog {

    @Id
    public String id;

    public String url;

    public String httpMethod;

    public String ip;

    public String classMethod;

    public String args;

    public String api;

    public String response;

    @Override
    public String toString() {
        return "AccessLog{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", ip='" + ip + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", args='" + args + '\'' +
                ", api='" + api + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
