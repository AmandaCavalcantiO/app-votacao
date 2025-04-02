package com.amandac.desafio_votacao.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "topics")
public class Topic {
    @Id
    private String id;
    private String title;
    private String description;
    @CreatedDate
    private LocalDateTime createdDate;


}
