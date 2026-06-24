package com.bajaj.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BfhlResponse(
    @JsonProperty("is_success")
    boolean isSuccess,
    
    @JsonProperty("user_id")
    String userId,
    
    @JsonProperty("email")
    String email,
    
    @JsonProperty("roll_number")
    String rollNumber,
    
    @JsonProperty("odd_numbers")
    List<String> oddNumbers,
    
    @JsonProperty("even_numbers")
    List<String> evenNumbers,
    
    @JsonProperty("alphabets")
    List<String> alphabets,
    
    @JsonProperty("special_characters")
    List<String> specialCharacters,
    
    @JsonProperty("sum")
    String sum,
    
    @JsonProperty("concat_string")
    String concatString
) {}
