package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SanctionService extends BaseService {
    private final SanctionXmlService sanctionXmlService;

    public void myFunc() {

    }

    public USSanctionListDataOutDTO savedSanctionData(InputStream inputStream) throws Exception {
        USSanctionListDataOutDTO usSanctionListDataOutDTO = sanctionXmlService.parseXmlFile(inputStream);

        return usSanctionListDataOutDTO;
    }
}
