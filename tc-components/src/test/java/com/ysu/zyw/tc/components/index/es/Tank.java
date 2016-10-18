package com.ysu.zyw.tc.components.index.es;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "tank", shards = 1, replicas = 1)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tank {

    @Id
    private String id;

    private long no;

    @Field(type = FieldType.Date)
    private Date createdTimestamp;

}
