package kr.co.sboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.sboard.entity.ArticleEntity;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer>{
	
	
	
}
