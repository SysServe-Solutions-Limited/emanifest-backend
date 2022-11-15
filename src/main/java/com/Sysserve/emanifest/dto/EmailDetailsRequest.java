package com.Sysserve.emanifest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsRequest {
    private String recipient;
    private String msgBody;
    private String subject;
}
