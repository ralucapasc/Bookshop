package com.pitech.mappers;


import com.pitech.dtos.UserRegisterDto;
import com.pitech.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Pasc Raluca on 01.08.2017.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

    @Mapping(target = "password", ignore = true)
    public abstract User toEntity(UserRegisterDto userRegisterDto);


    public User toEntityWithPassword(UserRegisterDto userDto){
        User user = toEntity(userDto);
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        return user;
    }

}
