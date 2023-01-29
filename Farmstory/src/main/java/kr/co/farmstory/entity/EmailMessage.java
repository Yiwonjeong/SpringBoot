package kr.co.farmstory.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class EmailMessage {

    private String to;
    private String subject;
    private String message;

}
