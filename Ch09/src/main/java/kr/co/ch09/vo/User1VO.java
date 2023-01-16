package kr.co.ch09.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User1VO {

	private String uid;
	private String pass;
	private String name;
	private String hp;
	private int age;
	
}
