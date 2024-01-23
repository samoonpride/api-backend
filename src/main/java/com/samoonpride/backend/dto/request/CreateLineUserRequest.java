package com.samoonpride.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLineUserRequest {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("display_name")
    private String displayName;
}
