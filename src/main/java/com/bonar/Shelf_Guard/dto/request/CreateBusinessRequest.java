package com.bonar.Shelf_Guard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBusinessRequest(@NotBlank @Size(max = 150) String name) {
    public CreateBusinessRequest {
        name = name == null ? null : name.trim();
    }

}
