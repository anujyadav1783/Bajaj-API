package com.bajaj.bfhl.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record BfhlRequest(
    @NotNull(message = "Data field cannot be null")
    List<String> data
) {}
