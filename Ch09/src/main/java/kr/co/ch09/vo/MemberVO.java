package kr.co.ch09.vo;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;


@Data
@Entity
@Table(name="member")
public class MemberVO {

	@Id
	private String uid;
	private String name;
	private String hp;
	private String pos;
	private int dep;
	
	@Column(name="rdate", updatable = false)	// (수정 필요) update시 rdate now로 update 해야 함 
	@CreationTimestamp
	private LocalDateTime rdate;
	
}
