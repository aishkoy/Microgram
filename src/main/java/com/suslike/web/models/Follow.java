package com.suslike.web.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow {
    private Long id;
    private Long follower;
    private Long actualUser;
}
