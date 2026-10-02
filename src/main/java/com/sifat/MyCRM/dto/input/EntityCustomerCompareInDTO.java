package com.sifat.MyCRM.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityCustomerCompareInDTO {
    private EntityCustomerBasicDetailDTO basic_details;
    private CustomerAMLEntityPermanentAddressInDTO permanent_address;
}
