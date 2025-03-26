package com.suslike.web.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like {
    private Long id;
    private Long liker;
    private Long postId;
}
