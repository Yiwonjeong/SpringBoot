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
public class BookVO {
	
	private int bookId;
	private String bookName;
	private String publisher;
	private int price;
	
}
