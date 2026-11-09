package com.bonar.Shelf_Guard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewBusinessRequest(@NotBlank @Size(max = 150) String name, @NotBlank String location,
                                 @NotBlank @Pattern(regexp = "^\\d{6}$", message = "PIN must be 6 digits") String pin){

    public NewBusinessRequest {
        name = name == null ? null : name.trim();
        location = location == null ? null : location.trim();
    }

    @Override
    public String toString(){
        return "NewBusinessRequest[name=" + name + "]";
    }
}
