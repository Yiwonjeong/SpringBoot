package kr.co.farmstory.service;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.repo.ArticleRepo;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleDAO dao;

    @Autowired
    private ArticleRepo repo;

    // -------------------- 글 ----------------------
    // 글 목록 불러오기
    public List<ArticleVO> selectArticles(String cate, int start){
        return dao.selectArticles(cate, start);
    }
    // 글 등록하기
    @Transactional
    public int insertArticle(ArticleVO vo){
        int result = dao.insertArticle(vo);
        FileVO fvo = fileUpload(vo);
        if(fvo != null){
            dao.insertFile(fvo);
        }
        return result;
    }
    // 글 보기
    public ArticleVO selectArticle(int no){
        ArticleVO vo = dao.selectArticle(no);
        return vo;
    }
    // 글 수정하기
    public int updateArticle(ArticleVO vo) {
        int result = dao.updateArticle(vo);
        return result;
    }
    // 글 삭제하기
    public int deleteArticle(int no){
        return dao.deleteArticle(no);
    }
    // 글 조회수 +1
    public int updateArticleHit(int no) {
        return dao.updateArticleHit(no);
    }

    // --------------------  파일 -------------------------
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public FileVO fileUpload(ArticleVO vo){
        MultipartFile file = vo.getFname();
        FileVO fvo = null;

        if(!file.isEmpty()) {
            // 시스템 경로
            String path = new File(uploadPath).getAbsolutePath();

            // 새 파일명 생성
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String nName = UUID.randomUUID().toString()+ext;

            // 파일 저장
            try {
                file.transferTo(new File(path, nName));
            } catch (IllegalStateException e) {
                log.error(e.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            fvo = FileVO.builder()
                    .parent(vo.getNo())
                    .oriName(oName)
                    .newName(nName)
                    .build();
        }

        return fvo;
    }

    // 파일 다운로드
    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
        Path path = Paths.get(uploadPath+vo.getNewName());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(vo.getOriName(), StandardCharsets.UTF_8)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 파일 다운로드 '클릭'
    @Transactional
    public FileVO selectFile(int fno) {
        FileVO vo = dao.selectFile(fno);
        // 파일 다운로드 횟수 +1
        dao.updateDownload(fno);
        return vo;
    }

    // --------------------  페이징  -----------------------
    // 페이지 시작값
    public int getLimitStart(int currentPage) {
        return (currentPage - 1) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg) {
        int currentPage = 1;
        if(pg != null) {
            currentPage = Integer.parseInt(pg);
        }
        return currentPage;
    }

    // 전체 게시글 개수
    public long getTotalCount() {
        return dao.selectCountTotal();
    }

    // 마지막 페이지 번호
    public int getLastPageNum(long total) {

        int lastPage = 0;

        if(total % 10 == 0) {
            lastPage = (int) (total / 10);
        }else {
            lastPage = (int) (total / 10) + 1;
        }

        return lastPage;

    }

    // 각 페이지의 시작 번호
    public int getPageStartNum(long total, int start) {
        return (int) (total - start);
    }

    // 페이지 그룹 1그룹(1~10) 2그룹(11~20)
    public int[] getPageGroup(int currentPage, int lastPage) {
        int groupCurrent = (int) Math.ceil(currentPage/10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage) {
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }

}
