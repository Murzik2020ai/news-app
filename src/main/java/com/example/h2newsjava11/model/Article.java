package com.example.h2newsjava11.model;

import org.hibernate.sql.ordering.antlr.OrderingSpecification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.UUID;
import static java.lang.String.format;

@Entity
@Table(name = "ARTICLES")
public class Article {
    @javax.persistence.Id
    @GeneratedValue
    @Column(name="id",
            nullable = false
    )
    public Long id;

    @Column(name = "news_head")
    public String head;
    @Column(name = "news_body")
    public String body;

    //Constructors
    public Article(){
//        String uid = UUID.randomUUID().toString();
//        this.head = format("head_%s", uid);
//        this.body = format("body_%s",uid);
    }
    public Article(String head, String body){
        this.head = head;
        this.body = body;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        id = id;
    }
    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getHead(){
        return head;
    }
    public void setHead(String head){
        this.head = head;
    }
}
