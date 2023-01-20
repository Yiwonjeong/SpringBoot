package kr.co.sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartRequest;

import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;

@Mapper
@Repository
public interface ArticleDAO {

	public int insertArticle(ArticleVO vo);
	
	public int insertFile(FileVO vo);
	
	public int selectCountTotal();
	
	public ArticleVO selectArticle(int no);
	
	public List<ArticleVO> selectArticles(int start);
	
	public int updateArticle(ArticleVO vo);
	
	public int deleteArticle(int no);
	
	// 게시글 조회수 +1
	public int updateArticleHit(int no);
	
	// 파일 다운로드 '클릭'
	public FileVO selectFile(int parent);
	
	
}
