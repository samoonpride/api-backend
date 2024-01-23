package com.samoonpride.backend.dto;

import com.samoonpride.backend.enums.UserEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
        private UserEnum type;
//        if type is "line", then key is line user id
//        if type is "staff", then key is staff email
        private String key;
}
