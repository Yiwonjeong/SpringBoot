package kr.co.farmstory.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileVO {
    private Integer fno;
    private Integer parent;
    private String newName;
    private String oriName;
    private Integer download;
}
