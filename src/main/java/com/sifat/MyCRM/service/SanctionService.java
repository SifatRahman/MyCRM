package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionAddressDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionAliasDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDOBDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualPOBDataDTO;
import com.sifat.MyCRM.entity.*;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import com.sifat.MyCRM.utility.CustomerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SanctionService extends BaseService {
    private final SanctionXmlService sanctionXmlService;
    private final SanctionIndividualRepository sanctionIndividualRepository;


    public List<SanctionIndividual> savedSanctionData(InputStream inputStream) throws Exception {
        USSanctionListDataOutDTO usSanctionListDataOutDTO = sanctionXmlService.parseXmlFile(inputStream);
        List<USSanctionIndividualDataDTO> individualXmlDataDTOs = usSanctionListDataOutDTO.getIndividuals();

        List<SanctionIndividual> savingIndividuals = Stream.of(individualXmlDataDTOs.getFirst())
                .map(individualXmlDataDTO -> {
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

                    //lastDayUpdated
                    List<String> xmlLastDayUpdated = individualXmlDataDTO.getLast_day_updated();
                    ArrayList<SanctionIndividualLastDayUpdated> lastDayUpdated = new ArrayList<>();
                    xmlLastDayUpdated.forEach(e -> {
                        SanctionIndividualLastDayUpdated lastDayUpdated1 =
                                SanctionIndividualLastDayUpdated.builder()
                                        .id(getUUID())
                                        .individual(sanctionIndividual)
                                        .lastDayUpdated(e)
                                        .build();
                        lastDayUpdated.add(lastDayUpdated1);
                    });
                    sanctionIndividual.setLastDayUpdated(lastDayUpdated);

                    //lastReviewedOns
                    List<String> xmlLastReviewedOns = individualXmlDataDTO.getLast_reviewed_on();
                    ArrayList<SanctionIndividualLastReviewedOn> lastReviewedOns = new ArrayList<>();
                    xmlLastReviewedOns.forEach(e -> {
                        SanctionIndividualLastReviewedOn lastReviewedOn =
                                SanctionIndividualLastReviewedOn.builder()
                                        .id(getUUID())
                                        .individual(sanctionIndividual)
                                        .lastReviewedOn(e)
                                        .build();
                        lastReviewedOns.add(lastReviewedOn);
                    });
                    sanctionIndividual.setLastReviewedOns(lastReviewedOns);


                    //individualAlias
                    List<USSanctionAliasDTO> xmlIndividualAlias = individualXmlDataDTO.getIndividual_alias();
                    ArrayList<SanctionAlias> individualAliases = new ArrayList<>();
                    xmlIndividualAlias.forEach(e -> {
                        SanctionAlias individualAlias = SanctionAlias.builder()
                                        .id(getUUID())
                                        .individual(sanctionIndividual)
                                        .entity(null)
                                        .customerType(CustomerType.INDIVIDUAL.name())
                                        .quality(e.getQuality())
                                        .alias_name(e.getAlias_name())
                                        .build();
                        individualAliases.add(individualAlias);
                    });
                    sanctionIndividual.setIndividualAlias(individualAliases);


                    //individualAlias
                    List<USSanctionAddressDataDTO> xmlIndividualAddress = individualXmlDataDTO.getIndividual_address();
                    ArrayList<SanctionAddress> individualAddresses = new ArrayList<>();
                    xmlIndividualAddress.forEach(e -> {
                        SanctionAddress individualAddress = SanctionAddress.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .entity(null)
                                .customerType(CustomerType.INDIVIDUAL.name())
                                .street(e.getStreet())
                                .city(e.getCity())
                                .zip_code(e.getZip_code())
                                .state_province(e.getState_province())
                                .country(e.getCountry())
                                .note(e.getNote())
                                .build();
                        individualAddresses.add(individualAddress);
                    });
                    sanctionIndividual.setIndividualAddress(individualAddresses);


                    //individualAlias
                    List<String> xmlTitles = individualXmlDataDTO.getTitle();
                    ArrayList<SanctionIndividualTitle> titles = new ArrayList<>();
                    xmlTitles.forEach(e -> {
                        SanctionIndividualTitle title = SanctionIndividualTitle.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .title(e)
                                .build();
                        titles.add(title);
                    });
                    sanctionIndividual.setTitles(titles);


                    //individualDOB
                    List<USSanctionIndividualDOBDTO> xmlDOB = individualXmlDataDTO.getIndividual_date_of_birth();
                    ArrayList<SanctionIndividualDateOfBirth> dobs = new ArrayList<>();
                    xmlDOB.forEach(e -> {
                        SanctionIndividualDateOfBirth dob = SanctionIndividualDateOfBirth.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .type_of_date(e.getType_of_date())
                                .date(e.getDate())
                                .from_year(e.getFrom_year())
                                .to_year(e.getTo_year())
                                .year(e.getYear())
                                .note(e.getNote())
                                .build();
                        dobs.add(dob);
                    });
                    sanctionIndividual.setIndividualDateOfBirth(dobs);


                    //individualDOB
                    List<USSanctionIndividualPOBDataDTO> xmlPOB = individualXmlDataDTO.getIndividual_place_of_birth();
                    ArrayList<SanctionIndividualPlaceOfBirth> pobs = new ArrayList<>();
                    xmlPOB.forEach(e -> {
                        SanctionIndividualPlaceOfBirth pob = SanctionIndividualPlaceOfBirth.builder()
                                .id(getUUID())
                                .individual(sanctionIndividual)
                                .country(e.getCountry())
                                .state_province(e.getState_province())
                                .city(e.getCity())
                                .build();
                        pobs.add(pob);
                    });
                    sanctionIndividual.setIndividual_place_of_birth(pobs);

                    return sanctionIndividual;
                }).toList();
        return sanctionIndividualRepository.saveAll(savingIndividuals);
    }
}
