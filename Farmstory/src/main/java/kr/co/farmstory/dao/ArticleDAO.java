package kr.co.farmstory.dao;

import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    // -------------------- 글 ----------------------
    // 글 목록 불러오기
    public List<ArticleVO> selectArticles(@Param("cate") String cate, @Param("start") int start);
    // 글 등록하기
    public int insertArticle(ArticleVO vo);
    // 글 보기
    public ArticleVO selectArticle(int no);
    // 글 수정하기
    public int updateArticle(ArticleVO vo);
    // 글 삭제하기
    public int deleteArticle(int no);
    // 글 조회수 +1
    public int updateArticleHit(int no);

    // --------------------  파일 -----------------------
    // 파일 등록하기
    public int insertFile(FileVO vo);
    // 파일 다운로드 '클릭'
    public FileVO selectFile(int fno);
    //	파일 다운로드 횟수 +1
    public int updateDownload(int fno);

    // --------------------  페이징 - 글 전체 개수 -----------------------
    public int selectCountTotal();


}
