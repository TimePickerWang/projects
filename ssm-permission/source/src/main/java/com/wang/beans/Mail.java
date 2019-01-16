package com.wang.beans;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

	private String subject;

	private String message;

	private Set<String> receivers;
}
