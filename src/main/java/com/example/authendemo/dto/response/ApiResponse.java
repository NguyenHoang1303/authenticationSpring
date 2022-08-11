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
public class ApiResponse {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Msg msg;

}
