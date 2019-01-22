package com.pitech.dtos;

import com.pitech.enums.Education;
import com.pitech.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Created by Pasc Raluca on 31.07.2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRegisterDto {

    @Length(min = 2, message = "First name should have minimum 2 characters!")
    private String firstName;

    @Length(min = 2, message = "First name should have minimum 2 characters!")
    private String lastName;

    @Email(message = "Invalid email address!")
    private String email;

    @Pattern(regexp = "^(?:(?:(?:00\\s?|\\+)40\\s?|0)(?:7\\d{2}\\s?\\d{3}\\s?\\d{3}|(21|31)\\d{1}\\s?\\d{3}\\s?\\d{3}|((2|3)[3-7]\\d{1})\\s?\\d{3}\\s?\\d{3}|(8|9)0\\d{1}\\s?\\d{3}\\s?\\d{3}))$", message = "Invalid phone number")
    private String mobile;
    private Gender gender;
    private Education education;

    @Length(min = 2, message = "Username should have minimum 2 characters!")
    private String username;

    @Length(min = 6, message = "Password should have minimum 6 characters!")
    private String password;
}
