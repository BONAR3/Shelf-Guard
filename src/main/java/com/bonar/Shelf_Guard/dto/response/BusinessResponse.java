package com.bonar.Shelf_Guard.dto.response;

import com.bonar.Shelf_Guard.enums.AccountStatus;

import java.util.UUID;

public record BusinessResponse (String name, String location, AccountStatus status){
}
