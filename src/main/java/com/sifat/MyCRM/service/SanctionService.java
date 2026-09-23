package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.entity.SanctionIndividual;
import com.sifat.MyCRM.entity.SanctionIndividualDesignation;
import com.sifat.MyCRM.entity.SanctionIndividualListType;
import com.sifat.MyCRM.entity.SanctionIndividualNationality;
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


    public List<SanctionIndividual> savedSanctionData(InputStream inputStream) throws Exception {
        USSanctionListDataOutDTO usSanctionListDataOutDTO = sanctionXmlService.parseXmlFile(inputStream);
        List<USSanctionIndividualDataDTO> individualXmlDataDTOs = usSanctionListDataOutDTO.getIndividuals();

        return individualXmlDataDTOs.stream().map(individualXmlDataDTO -> {
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
            xmlDesignations.forEach(e -> {
                SanctionIndividualDesignation sanctionIndividualDesignation =
                        SanctionIndividualDesignation.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .designation(e)
                                .build();
                sanctionIndividualDesignations.add(sanctionIndividualDesignation);
            });

            sanctionIndividual.setDesignations(sanctionIndividualDesignations);


            //nationalities
            List<String> xmlNationalities = individualXmlDataDTO.getNationality();
            ArrayList<SanctionIndividualNationality> nationalities = new ArrayList<>();
            xmlNationalities.forEach(e -> {
                SanctionIndividualNationality nationality =
                        SanctionIndividualNationality.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .nationality(e)
                                .build();
                nationalities.add(nationality);
            });
            sanctionIndividual.setNationalities(nationalities);

            //listTypes
            List<String> xmlDataDTOListType = individualXmlDataDTO.getList_type();
            ArrayList<SanctionIndividualListType> listTypes = new ArrayList<>();
            xmlDataDTOListType.forEach(e -> {
                SanctionIndividualListType listType =
                        SanctionIndividualListType.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .list_type(e)
                                .build();
                listTypes.add(listType);
            });
            sanctionIndividual.setListTypes(listTypes);

            //remaining
//        sanctionIndividual.setDesignations(sanctionIndividualDesignations);
//        sanctionIndividual.setDesignations(sanctionIndividualDesignations);
//        sanctionIndividual.setDesignations(sanctionIndividualDesignations);
//        sanctionIndividual.setDesignations(sanctionIndividualDesignations);

             return sanctionIndividualRepository.save(sanctionIndividual);
        }).toList();
    }
}
