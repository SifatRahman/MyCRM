package com.sifat.MyCRM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanctionPersonHelperDTO {
    private String name;
    private String dateOfBirth;
    private String nationality;
}
