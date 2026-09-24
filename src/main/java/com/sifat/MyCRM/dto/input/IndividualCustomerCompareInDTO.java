package com.sifat.MyCRM.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerCompareInDTO {
    private CustomerBasicDetailDTO basic_details;
    private CustomerAMLIndividualPermanentAddressInDTO permanent_address;
}
