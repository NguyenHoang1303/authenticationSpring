package com.example.authendemo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Msg {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
