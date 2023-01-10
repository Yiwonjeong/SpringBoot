package kr.co.bookstore.vo;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerVO {

	private int custId;
	private String name;
	private String address;
	private String phone;
	
}
