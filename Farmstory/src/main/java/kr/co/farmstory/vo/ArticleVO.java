package kr.co.farmstory.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {
    private Integer no;
    private Integer parent;
    private Integer comment;
    private String cate;
    private String title;
    private String content;
    private Integer file;
    private MultipartFile fname;
    private Integer hit;
    private String uid;
    private String regip;
    private String rdate;

    // 추가 필드
    private String nick;
    private FileVO fileVO;
    private String page;

    public String getRdate(){return rdate.substring(2, 10);}
}
