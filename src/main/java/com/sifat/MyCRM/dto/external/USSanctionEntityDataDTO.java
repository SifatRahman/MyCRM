package com.sifat.MyCRM.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionEntityDataDTO {
    private String dataId;
    private String firstName;

    private String unListType;
    private String referenceNumber;
    private String listedOn;
    private String comments;

    private String address;

    private List<String> aliases;
}
