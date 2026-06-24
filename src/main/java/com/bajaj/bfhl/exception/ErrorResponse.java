package com.bajaj.bfhl.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(
    @JsonProperty("is_success")
    boolean isSuccess,
    
    @JsonProperty("message")
    String message
) {}
