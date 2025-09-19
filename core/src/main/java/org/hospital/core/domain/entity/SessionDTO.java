package org.hospital.core.domain.entity;

import lombok.Builder;

@Builder
public class SessionDTO {
    private String token;
    private Long userId;
    private String userName;
    private String role;
}
