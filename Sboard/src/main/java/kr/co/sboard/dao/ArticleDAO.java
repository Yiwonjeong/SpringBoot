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

	// ---------- 글 ------------
	// 글 등록하기
	public int insertArticle(ArticleVO vo);
	
	// 글 목록 불러오기
	public List<ArticleVO> selectArticles(int start);
	
	// 글 불러오기
	public ArticleVO selectArticle(int no);
	
	// 글 수정하기
	public int updateArticle(ArticleVO vo);
	
	// 글 삭제하기
	public int deleteArticle(int no);
	
	// 글 조회수 +1
	public int updateArticleHit(int no);
	
	
	// ----------  파일 -------------
	// 파일 등록하기
	public int insertFile(FileVO vo);
	
	// 파일 다운로드 '클릭'
	public FileVO selectFile(int fno);
	
	//	파일 다운로드 횟수 +1
	public int updateDownload(int fno);
	
	
	// ----------  페이징 - 글 전체 개수 -------------
	public int selectCountTotal();
	
	
	
	
	
	
	
	
	
	
	
	
}
