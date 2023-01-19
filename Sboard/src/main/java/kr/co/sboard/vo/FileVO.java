package kr.co.sboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class FileVO {

	
	private int fNo;
	private int parent;
	private String newName;
	private String oriName;
	private int download;
	
}
