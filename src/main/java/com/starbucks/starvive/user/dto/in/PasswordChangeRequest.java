package com.starbucks.starvive.user.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {
    private String currentPassword;
    private String newPassword;
    
    @Builder
    public PasswordChangeRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
} 