package com.kt.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviewDto {

	private Integer id;
	
	private Integer numberofstart;
	
	private String content;
	
	private String datereview;
	
}
