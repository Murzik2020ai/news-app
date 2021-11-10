package com.example.h2newsjava11.repository;
import com.example.h2newsjava11.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRep extends CrudRepository<Article, Long> {
}
