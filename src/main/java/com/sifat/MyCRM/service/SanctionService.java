package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionAddressDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionAliasDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDBOutDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDOBDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualPOBDataDTO;
import com.sifat.MyCRM.entity.*;
import com.sifat.MyCRM.repository.SanctionEntityRepository;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import com.sifat.MyCRM.utility.CustomerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanctionService extends BaseService {
    private final SanctionXmlService sanctionXmlService;
    private final SanctionIndividualRepository sanctionIndividualRepository;
    private final SanctionEntityRepository sanctionEntityRepository;


    @Transactional(rollbackFor = Exception.class)
    public USSanctionListDBOutDTO savedSanctionData(InputStream inputStream) throws Exception {

        try {
            USSanctionListDataOutDTO usSanctionListDataOutDTO = sanctionXmlService.parseXmlFile(inputStream);
            List<USSanctionIndividualDataDTO> individualXmlDataDTOs = usSanctionListDataOutDTO.getIndividuals();
            List<USSanctionEntityDataDTO> entityXmlDataDTOs = usSanctionListDataOutDTO.getEntities();

            //individuals
            List<SanctionIndividual> savingIndividuals = individualXmlDataDTOs.stream()
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


            //entities
            List<SanctionEntity> savingEntities = entityXmlDataDTOs.stream()
                    .map(entityXmlDataDTO -> {
                        SanctionEntity sanctionentity = new SanctionEntity();
                        sanctionentity.setId(getUUID());
                        sanctionentity.setDataId(entityXmlDataDTO.getData_id());
                        sanctionentity.setVersionNo(entityXmlDataDTO.getVersion_no());
                        sanctionentity.setFirstName(entityXmlDataDTO.getFirst_name());
                        sanctionentity.setUnListType(entityXmlDataDTO.getUn_list_type());
                        sanctionentity.setReferenceNumber(entityXmlDataDTO.getReference_number());
                        sanctionentity.setListedOn(entityXmlDataDTO.getListed_on());
                        sanctionentity.setNameOriginalScript(entityXmlDataDTO.getName_original_script());
                        sanctionentity.setComments1(entityXmlDataDTO.getComments1());
                        sanctionentity.setHasInterpolLink(entityXmlDataDTO.getHas_interpol_link());
                        sanctionentity.setInterpolLink(entityXmlDataDTO.getInterpol_link());

                        //listTypes
                        List<String> xmlDataDTOListType = entityXmlDataDTO.getList_type();
                        ArrayList<SanctionEntityListType> listTypes = new ArrayList<>();
                        xmlDataDTOListType.forEach(e -> {
                            SanctionEntityListType listType =
                                    SanctionEntityListType.builder()
                                            .id(getUUID())
                                            .entity(sanctionentity)
                                            .list_type(e)
                                            .build();
                            listTypes.add(listType);
                        });
                        sanctionentity.setListTypes(listTypes);

                        //lastDayUpdated
                        List<String> xmlLastDayUpdated = entityXmlDataDTO.getLast_day_updated();
                        ArrayList<SanctionEntityLastDayUpdated> lastDayUpdated = new ArrayList<>();
                        xmlLastDayUpdated.forEach(e -> {
                            SanctionEntityLastDayUpdated lastDayUpdated1 =
                                    SanctionEntityLastDayUpdated.builder()
                                            .id(getUUID())
                                            .entity(sanctionentity)
                                            .lastDayUpdated(e)
                                            .build();
                            lastDayUpdated.add(lastDayUpdated1);
                        });
                        sanctionentity.setLastDayUpdated(lastDayUpdated);

                        //lastReviewedOns
                        List<String> xmlLastReviewedOns = entityXmlDataDTO.getLast_reviewed_on();
                        ArrayList<SanctionEntityLastReviewedOn> lastReviewedOns = new ArrayList<>();
                        xmlLastReviewedOns.forEach(e -> {
                            SanctionEntityLastReviewedOn lastReviewedOn =
                                    SanctionEntityLastReviewedOn.builder()
                                            .id(getUUID())
                                            .entity(sanctionentity)
                                            .lastReviewedOn(e)
                                            .build();
                            lastReviewedOns.add(lastReviewedOn);
                        });
                        sanctionentity.setLastReviewedOns(lastReviewedOns);


                        //entityAlias
                        List<USSanctionAliasDTO> xmlEntityAlias = entityXmlDataDTO.getEntity_alias();
                        ArrayList<SanctionAlias> entityAliases = new ArrayList<>();
                        xmlEntityAlias.forEach(e -> {
                            SanctionAlias entityAlias = SanctionAlias.builder()
                                    .id(getUUID())
                                    .individual(null)
                                    .entity(sanctionentity)
                                    .customerType(CustomerType.ENTITY.name())
                                    .quality(e.getQuality())
                                    .alias_name(e.getAlias_name())
                                    .build();
                            entityAliases.add(entityAlias);
                        });
                        sanctionentity.setEntityAlias(entityAliases);


                        //entityAlias
                        List<USSanctionAddressDataDTO> xmlEntityAddress = entityXmlDataDTO.getEntity_address();
                        ArrayList<SanctionAddress> entityAddresses = new ArrayList<>();
                        xmlEntityAddress.forEach(e -> {
                            SanctionAddress entityAddress = SanctionAddress.builder()
                                    .id(getUUID())
                                    .individual(null)
                                    .entity(sanctionentity)
                                    .customerType(CustomerType.ENTITY.name())
                                    .street(e.getStreet())
                                    .city(e.getCity())
                                    .zip_code(e.getZip_code())
                                    .state_province(e.getState_province())
                                    .country(e.getCountry())
                                    .note(e.getNote())
                                    .build();
                            entityAddresses.add(entityAddress);
                        });
                        sanctionentity.setEntityAddress(entityAddresses);

                        return sanctionentity;
                    }).toList();

            //saving to DB
            sanctionIndividualRepository.saveAll(savingIndividuals);
            sanctionEntityRepository.saveAll(savingEntities);

            USSanctionListDBOutDTO usSanctionListDBOutDTO = new USSanctionListDBOutDTO();
            usSanctionListDBOutDTO.setIndividuals(savingIndividuals);
            usSanctionListDBOutDTO.setEntities(savingEntities);
            return usSanctionListDBOutDTO;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
