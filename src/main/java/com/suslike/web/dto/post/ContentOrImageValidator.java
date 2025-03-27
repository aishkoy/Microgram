package com.suslike.web.dto.post;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ContentOrImageValidator implements ConstraintValidator<ContentOrImageRequired, PostAddDto> {
    @Override
    public boolean isValid(PostAddDto post, ConstraintValidatorContext context) {
        if (post == null) {
            return false;
        }
        
        boolean hasContent = post.getContent() != null && !post.getContent().trim().isEmpty();
        boolean hasImage = post.getImage() != null && !post.getImage().trim().isEmpty();
        
        return hasContent || hasImage;
    }
}