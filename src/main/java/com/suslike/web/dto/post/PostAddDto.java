package com.suslike.web.dto.post;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ContentOrImageRequired(message = "Необходимо предоставить либо текст, либо изображение")
public class PostAddDto {
	private String image;
	private String content;
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContentOrImageValidator.class)
@interface ContentOrImageRequired {
	String message() default "Необходимо предоставить либо текст, либо изображение";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}