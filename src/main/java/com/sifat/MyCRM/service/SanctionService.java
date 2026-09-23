package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.entity.SanctionIndividual;
import com.sifat.MyCRM.entity.SanctionIndividualDesignation;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanctionService extends BaseService {
    private final SanctionXmlService sanctionXmlService;
    private final SanctionIndividualRepository sanctionIndividualRepository;

    public void myFunc() {

    }

    public SanctionIndividual savedSanctionData(InputStream inputStream) throws Exception {
        USSanctionListDataOutDTO usSanctionListDataOutDTO = sanctionXmlService.parseXmlFile(inputStream);
        USSanctionIndividualDataDTO individualXmlDataDTO = usSanctionListDataOutDTO.getIndividuals().getFirst();


        SanctionIndividual sanctionIndividual = new SanctionIndividual();
        sanctionIndividual.setId(getUUID());
        sanctionIndividual.setDataId(individualXmlDataDTO.getData_id());
        sanctionIndividual.setVersionNo(individualXmlDataDTO.getVersion_no());
        sanctionIndividual.setFirstName(individualXmlDataDTO.getFirst_name());
        sanctionIndividual.setSecondName(individualXmlDataDTO.getSecond_name());
        sanctionIndividual.setThirdName(individualXmlDataDTO.getThird_name());
        sanctionIndividual.setFourthName(individualXmlDataDTO.getFourth_name());
        sanctionIndividual.setUnListType(individualXmlDataDTO.getUn_list_type());
        sanctionIndividual.setReferenceNumber(individualXmlDataDTO.getReference_number());
        sanctionIndividual.setListedOn(individualXmlDataDTO.getListed_on());
        sanctionIndividual.setNameOriginalScript(individualXmlDataDTO.getName_original_script());
        sanctionIndividual.setComments1(individualXmlDataDTO.getComments1());
        sanctionIndividual.setHasInterpolLink(individualXmlDataDTO.getHas_interpol_link());
        sanctionIndividual.setInterpolLink(individualXmlDataDTO.getInterpol_link());

        List<String> xmlDesignations = individualXmlDataDTO.getDesignation();
        ArrayList<SanctionIndividualDesignation> sanctionIndividualDesignations = new ArrayList<>();
        xmlDesignations.forEach(e->{
            SanctionIndividualDesignation sanctionIndividualDesignation =
                    SanctionIndividualDesignation.builder()
                            .individualId(sanctionIndividual)
                            .designation(e)
                            .build();
            sanctionIndividualDesignations.add(sanctionIndividualDesignation);
        });

        sanctionIndividual.setDesignations(sanctionIndividualDesignations);

        return sanctionIndividualRepository.save(sanctionIndividual);
    }
}
