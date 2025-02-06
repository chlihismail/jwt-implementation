package com.cxi.see_rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cxi.see_rest.dto.AppUserDto;
import com.cxi.see_rest.dto.SignUpDto;
import com.cxi.see_rest.model.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper{

    @Mapping(target = "token", ignore = true)
    AppUserDto toAppUserDto(AppUser appUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "accountExpiryDate", ignore = true)
    @Mapping(target = "passwordExpiryDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    AppUser toAppUser(SignUpDto signUpDto);
}
