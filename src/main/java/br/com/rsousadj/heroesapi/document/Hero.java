package br.com.rsousadj.heroesapi.document;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "hero")
public class Hero {

    @Id
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    private String name;
    private String universe;
    private int films;


}
