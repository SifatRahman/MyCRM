package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.input.*;
import com.sifat.MyCRM.dto.output.EntityCustomerCompareResultOutDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerPermanentAddressViewDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerViewDTO;
import com.sifat.MyCRM.entity.*;
import com.sifat.MyCRM.exception.ResourceNotFoundException;
import com.sifat.MyCRM.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.nio.file.ReadOnlyFileSystemException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService {
    private final IndividualCustomerRepository individualCustomerRepository;
    private final EntityCustomerRepository entityCustomerRepository;
    private final EntityCustomerPermanentAddressRepository entityCustomerPermanentAddressRepository;
    private final IndividualCustomerPermanentAddressRepository individualCustomerPermanentAddressRepository;
    private final CustomerSanctionEntityComparisonHistoryRepository entityComparisonHistoryRepository;
    private final CustomerSanctionIndividualComparisonHistoryRepository individualComparisonHistoryRepository;
    private final SanctionComparisonService sanctionComparisonService;

    @Value("${string.concatenation.regex}")
    protected String stringConcatenationRegex;

    public String Hello() {
        var msg = showBaseName();
        return msg;
    }

    public IndividualCustomerViewDTO getIndividualCustomer(String individualCustomerId) {
        if(isBlankStringOrNull(individualCustomerId)){
            throw new ResourceNotFoundException("Individual customer id can't be null or blank!");
        }
        IndividualCustomer individualCustomer = individualCustomerRepository.findById(individualCustomerId).orElseThrow(() -> new ResourceNotFoundException(
                "Individual customer not found with id: " + individualCustomerId
        ));

        CustomerSanctionIndividualComparisonHistory comparisonHistory = null;
        if (!individualCustomer.getSanctionIndividualComparisonHistory().isEmpty()) {
            comparisonHistory = individualCustomer.getSanctionIndividualComparisonHistory()
                    .stream()
                    .max(Comparator.comparing(
                            CustomerSanctionIndividualComparisonHistory::getTimestamp
                    ))
                    .orElse(null);
        }
        IndividualCustomerPermanentAddress permanentAddress = individualCustomerPermanentAddressRepository.findByIndividualCustomer_Id(individualCustomer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Individual customer permanent address not found!"));

        IndividualCustomerPermanentAddressViewDTO individualCustomerPermanentAddress = IndividualCustomerPermanentAddressViewDTO.builder()
                .customer_id(permanentAddress.getIndividualCustomer().getId())
                .country(permanentAddress.getCountry())
                .division_or_state(permanentAddress.getDivision_or_state())
                .district(permanentAddress.getDistrict())
                .upazila(permanentAddress.getUpazila())
                .police_station(permanentAddress.getPolice_station())
                .post_code(permanentAddress.getPost_code())
                .village_or_area(permanentAddress.getVillage_or_area())
                .road_or_block(permanentAddress.getRoad_or_block())
                .house_or_flat_no(permanentAddress.getHouse_or_flat_no())
                .mobile_no(permanentAddress.getMobile_no())
                .phone_number_off_1(permanentAddress.getPhone_number_off_1())
                .email_address(permanentAddress.getEmail_address())
                .build();

        return IndividualCustomerViewDTO.builder()
                .id(individualCustomer.getId())

                .sanction_individual_comparison_status(comparisonHistory != null ? comparisonHistory.getMatchStatus() : null)

                .full_name(individualCustomer.getFullName())
                .full_name_2(individualCustomer.getFullName2())
                .family_name(individualCustomer.getFamilyName())
                .short_name(individualCustomer.getShortName())

                .mnemonic(individualCustomer.getMnemonic())
                .gender(individualCustomer.getGender())

                .account_officer(individualCustomer.getAccountOfficer())
                .sector(individualCustomer.getSector())
                .target(individualCustomer.getTarget())
                .customer_status(individualCustomer.getCustomerStatus())

                .industry(individualCustomer.getIndustry())
                .language(individualCustomer.getLanguage())
                .residence(individualCustomer.getResidence())

                .date_of_birth(individualCustomer.getDateOfBirth())
                .nationality(individualCustomer.getNationality())

                .nid_no(individualCustomer.getNidNo())
                .passport_no(individualCustomer.getPassportNo())

                .father_name(individualCustomer.getFatherName())
                .mother_name(individualCustomer.getMotherName())

                .marital_status(individualCustomer.getMaritalStatus())
                .spouse(individualCustomer.getSpouse())

                .cb_sector_code(individualCustomer.getCbSectorCode())

                .return_submission_date(
                        individualCustomer.getReturnSubmissionDate()
                )

                .sms_alert_service(
                        individualCustomer.getSmsAlertService()
                )
                .customer_permanent_address(individualCustomerPermanentAddress)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public String createIndividualCustomer(CreateIndividualCustomerDTO inDTO) throws Exception {
        try {
            if (inDTO.getNid_no().isBlank() && inDTO.getPassport_no().isBlank()) {
                throw new ResourceNotFoundException("Either Nid or passport number must be provided!");
            }
            var existsByNID = individualCustomerRepository.findById(inDTO.getNid_no()).orElse(null);
            var existsByPass = individualCustomerRepository.findById(inDTO.getPassport_no()).orElse(null);
            if (existsByNID != null && existsByPass != null) {
                throw new ResourceNotFoundException("Customer with this NID Number or Passport already exists!");
            }

            IndividualCustomer customer = new IndividualCustomer();
            customer.setId(getUUID());
            customer.setFullName(inDTO.getFull_name());
            customer.setFullName2(inDTO.getFull_name_2());
            customer.setFamilyName(inDTO.getFamily_name());
            customer.setShortName(inDTO.getShort_name());
            customer.setMnemonic(inDTO.getMnemonic());
            customer.setGender(inDTO.getGender());
            customer.setAccountOfficer(inDTO.getAccount_officer());
            customer.setSector(inDTO.getSector());
            customer.setTarget(inDTO.getTarget());
            customer.setCustomerStatus(inDTO.getCustomer_status());
            customer.setIndustry(inDTO.getIndustry());
            customer.setLanguage(inDTO.getLanguage());
            customer.setResidence(inDTO.getResidence());

            if (inDTO.getDate_of_birth().isAfter(LocalDate.now())) {
                throw new ResourceNotFoundException("Birth date can't be future!");
            }
            customer.setDateOfBirth(inDTO.getDate_of_birth());
            customer.setNationality(inDTO.getNationality());
            customer.setNidNo(inDTO.getNid_no());
            customer.setPassportNo(inDTO.getPassport_no());
            customer.setFatherName(inDTO.getFather_name());
            customer.setMotherName(inDTO.getMother_name());
            customer.setMaritalStatus(inDTO.getMarital_status());
            customer.setSpouse(inDTO.getSpouse());
            customer.setCbSectorCode(inDTO.getCb_sector_code());
            customer.setReturnSubmissionDate(inDTO.getReturn_submission_date());
            customer.setSmsAlertService(inDTO.getSms_alert_service());
            customer.setIsSanctionAMLVerified(false);
            customer.setTimestamp(LocalDateTime.now());


            //Saving permanent address
            CreateIndividualCustomerPermanentAddressDTO inCPA = inDTO.getIndividual_customer_permanent_address();
            IndividualCustomerPermanentAddress cpa = new IndividualCustomerPermanentAddress();
            cpa.setId(getUUID());
            cpa.setIndividualCustomer(customer);
            cpa.setCountry(inCPA.getCountry());
            cpa.setDivision_or_state(inCPA.getDivision_or_state());
            cpa.setDistrict(inCPA.getDistrict());
            cpa.setUpazila(inCPA.getUpazila());
            cpa.setPolice_station(inCPA.getPolice_station());
            cpa.setPost_code(inCPA.getPost_code());
            cpa.setVillage_or_area(inCPA.getVillage_or_area());
            cpa.setRoad_or_block(inCPA.getRoad_or_block());
            cpa.setHouse_or_flat_no(houseOrFlatsToString(inCPA.getHouse_or_flat_no()));
            cpa.setMobile_no(inCPA.getMobile_no());
            cpa.setPhone_number_off_1(inCPA.getPhone_number_off_1());
            cpa.setEmail_address(inCPA.getEmail_address());

            IndividualCustomer savedIndividualCustomer = individualCustomerRepository.save(customer);
            IndividualCustomerPermanentAddress customerPermanentAddress = individualCustomerPermanentAddressRepository.save(cpa);
            return savedIndividualCustomer.getId();


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<IndividualCustomerCompareResultOutDTO> verifySavedIndividualCustomerAML(String individualCustomerId) {
        try {
            if(isBlankStringOrNull(individualCustomerId)){
                throw new ResourceNotFoundException("Individual customer id can't be null or blank!");
            }
            IndividualCustomer individualCustomer = individualCustomerRepository.findById(individualCustomerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer not found!"));
            IndividualCustomerPermanentAddress customerPermanentAddress = individualCustomerPermanentAddressRepository.findByIndividualCustomer_Id(individualCustomerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer permanent address not found!"));

            IndividualCustomerCompareInDTO individualCustomerCompareInDTO = getIndividualCustomerCompareInDTO(individualCustomer, customerPermanentAddress);
            List<IndividualCustomerCompareResultOutDTO> matchingResult = sanctionComparisonService.compareIndividualData(individualCustomerCompareInDTO);

            if(!matchingResult.isEmpty()) {
                IndividualCustomerCompareResultOutDTO topMatchingResult = matchingResult.getFirst();

                //update the table
                individualCustomer.setIsSanctionAMLVerified(true);
                individualCustomerRepository.save(individualCustomer);
                //insert data into history table

                //history keeping of top score
                CustomerSanctionIndividualComparisonHistory his = new CustomerSanctionIndividualComparisonHistory();
                his.setId(getUUID());
                his.setIndividualCustomer(individualCustomer);
                his.setNameScore(topMatchingResult.getName_score());
                his.setDateOfBirthScore(topMatchingResult.getDob_score());
                his.setDocumentScore(topMatchingResult.getDoc_score());
                his.setNationalityScore(topMatchingResult.getNationality_score());
                his.setAddressScore(topMatchingResult.getAddress_score());
                his.setPlaceOfBirthScore(topMatchingResult.getPob_score());
                his.setTotalScore(topMatchingResult.getOverall_score());
                his.setMatchStatus(topMatchingResult.getSanction_match_status());
                his.setTimestamp(LocalDateTime.now());
                individualComparisonHistoryRepository.save(his);
            }
            return matchingResult;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public IndividualCustomerCompareInDTO getIndividualCustomerCompareInDTO(IndividualCustomer ic, IndividualCustomerPermanentAddress pa) {
        IndividualCustomerCompareInDTO individualCustomerCompareInDTO = new IndividualCustomerCompareInDTO();

        CustomerBasicDetailDTO basicDetailDTO = CustomerBasicDetailDTO.builder()
                .full_name(ic.getFullName()).full_name_2(ic.getFullName2()).family_name(ic.getFamilyName())
                .short_name(ic.getShortName()).mnemonic(ic.getMnemonic()).gender(ic.getGender())
                .account_officer(ic.getAccountOfficer()).sector(ic.getSector()).target(ic.getTarget())
                .customer_status(ic.getCustomerStatus()).industry(ic.getIndustry()).language(ic.getLanguage())
                .residence(ic.getResidence()).date_of_birth(ic.getDateOfBirth()).nationality(ic.getNationality())
                .nid_no(ic.getNidNo()).passport_no(ic.getPassportNo()).father_name(ic.getFatherName())
                .mother_name(ic.getMotherName()).marital_status(ic.getMaritalStatus()).spouse(ic.getSpouse())
                .cb_sector_code(ic.getCbSectorCode()).return_submission_date(ic.getReturnSubmissionDate()).sms_alert_service(ic.getSmsAlertService())
                .build();

        CustomerAMLIndividualPermanentAddressInDTO address = CustomerAMLIndividualPermanentAddressInDTO.builder()
                .country(pa.getCountry()).division_or_state(pa.getDivision_or_state()).district(pa.getDistrict())
                .upazila(pa.getUpazila()).police_station(pa.getPolice_station()).post_code(pa.getPost_code())
                .village_or_area(pa.getVillage_or_area()).road_or_block(pa.getRoad_or_block()).house_or_flat_no(stringToHouseOrFlats(pa.getHouse_or_flat_no()))
                .mobile_no(pa.getMobile_no()).phone_number_off_1(pa.getPhone_number_off_1()).email_address(pa.getEmail_address()).build();

        individualCustomerCompareInDTO.setBasic_details(basicDetailDTO);
        individualCustomerCompareInDTO.setPermanent_address(address);
        return individualCustomerCompareInDTO;
    }

    public String houseOrFlatsToString(List<String> houseOrFlats) {
        if (houseOrFlats == null || houseOrFlats.isEmpty()) {
            return null;
        }

        return String.join(stringConcatenationRegex, houseOrFlats);
    }

    public List<String> stringToHouseOrFlats(String value) {
        if (value == null || value.isBlank()) {
            return new ArrayList<>();
        }
        return Arrays.stream(value.split(stringConcatenationRegex))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }



    @Transactional(rollbackFor = Exception.class)
    public String createEntityCustomer(CreateEntityCustomerDTO inDTO) throws Exception {
        try {
            var existsByName = entityCustomerRepository.findByFullName(inDTO.getFull_name()).orElse(null);
            if (existsByName != null) {
                throw new ResourceNotFoundException("Customer with same name already exists!");
            }

            EntityCustomer customer = new EntityCustomer();
            customer.setId(getUUID());
            customer.setFullName(inDTO.getFull_name());
            customer.setAccountOfficer(inDTO.getAccount_officer());
            customer.setSector(inDTO.getSector());
            customer.setTarget(inDTO.getTarget());
            customer.setCustomerStatus(inDTO.getCustomer_status());
            customer.setIndustry(inDTO.getIndustry());
            customer.setLanguage(inDTO.getLanguage());
            customer.setResidence(inDTO.getResidence());
            customer.setCbSectorCode(inDTO.getCb_sector_code());
            customer.setReturnSubmissionDate(inDTO.getReturn_submission_date());
            customer.setSmsAlertService(inDTO.getSms_alert_service());
            customer.setIsSanctionAMLVerified(false);
            customer.setTimestamp(LocalDateTime.now());


            //Saving permanent address
            CreateEntityCustomerPermanentAddressDTO inCPA = inDTO.getEntityCustomerPermanentAddressDTO();
            EntityCustomerPermanentAddress cpa = new EntityCustomerPermanentAddress();
            cpa.setId(getUUID());
            cpa.setEntityCustomer(customer);
            cpa.setCountry(inCPA.getCountry());
            cpa.setDivision_or_state(inCPA.getDivision_or_state());
            cpa.setDistrict(inCPA.getDistrict());
            cpa.setUpazila(inCPA.getUpazila());
            cpa.setPolice_station(inCPA.getPolice_station());
            cpa.setPost_code(inCPA.getPost_code());
            cpa.setVillage_or_area(inCPA.getVillage_or_area());
            cpa.setRoad_or_block(inCPA.getRoad_or_block());
            cpa.setHouse_or_flat_no(houseOrFlatsToString(inCPA.getHouse_or_flat_no()));
            cpa.setMobile_no(inCPA.getMobile_no());
            cpa.setPhone_number_off_1(inCPA.getPhone_number_off_1());
            cpa.setEmail_address(inCPA.getEmail_address());

            EntityCustomer savedIndividualCustomer = entityCustomerRepository.save(customer);
            entityCustomerPermanentAddressRepository.save(cpa);
            return savedIndividualCustomer.getId();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public List<EntityCustomerCompareResultOutDTO> verifySavedEntityCustomerAML(String entityCustomerId) {

        try {
            if(isBlankStringOrNull(entityCustomerId)){
                throw new ResourceNotFoundException("Entity customer id can't be null or blank!");
            }
            EntityCustomer entityCustomer = entityCustomerRepository.findById(entityCustomerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer not found!"));
            EntityCustomerPermanentAddress customerPermanentAddress = entityCustomerPermanentAddressRepository.findByEntityCustomer_Id(entityCustomerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer permanent address not found!"));

            EntityCustomerCompareInDTO entityCustomerCompareInDTO = getEntityCustomerCompareInDTO(entityCustomer, customerPermanentAddress);
            List<EntityCustomerCompareResultOutDTO> matchingResult = sanctionComparisonService.compareEntityData(entityCustomerCompareInDTO);

            if(!matchingResult.isEmpty()) {
                EntityCustomerCompareResultOutDTO topMatchingResult = matchingResult.getFirst();

                //update the table
                entityCustomer.setIsSanctionAMLVerified(true);
                entityCustomerRepository.save(entityCustomer);
                //insert data into history table

                //history keeping of top score
                CustomerSanctionEntityComparisonHistory his = new CustomerSanctionEntityComparisonHistory();
                his.setId(getUUID());
                his.setEntityCustomer(entityCustomer);
                his.setNameScore(topMatchingResult.getName_score());
                his.setAddressScore(topMatchingResult.getAddress_score());
                his.setTotalScore(topMatchingResult.getOverall_score());
                his.setMatchStatus(topMatchingResult.getSanction_match_status());
                his.setTimestamp(LocalDateTime.now());
                entityComparisonHistoryRepository.save(his);
            }
            return matchingResult;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public EntityCustomerCompareInDTO getEntityCustomerCompareInDTO(EntityCustomer ic, EntityCustomerPermanentAddress pa) {
        EntityCustomerCompareInDTO entityCustomerCompareInDTO = new EntityCustomerCompareInDTO();

        EntityCustomerBasicDetailDTO basicDetailDTO = EntityCustomerBasicDetailDTO.builder()
                .full_name(ic.getFullName())
                .account_officer(ic.getAccountOfficer()).sector(ic.getSector()).target(ic.getTarget())
                .customer_status(ic.getCustomerStatus()).industry(ic.getIndustry()).language(ic.getLanguage())
                .residence(ic.getResidence())
                .cb_sector_code(ic.getCbSectorCode()).return_submission_date(ic.getReturnSubmissionDate()).sms_alert_service(ic.getSmsAlertService())
                .build();

        CustomerAMLEntityPermanentAddressInDTO address = CustomerAMLEntityPermanentAddressInDTO.builder()
                .country(pa.getCountry()).division_or_state(pa.getDivision_or_state()).district(pa.getDistrict())
                .upazila(pa.getUpazila()).police_station(pa.getPolice_station()).post_code(pa.getPost_code())
                .village_or_area(pa.getVillage_or_area()).road_or_block(pa.getRoad_or_block()).house_or_flat_no(stringToHouseOrFlats(pa.getHouse_or_flat_no()))
                .mobile_no(pa.getMobile_no()).phone_number_off_1(pa.getPhone_number_off_1()).email_address(pa.getEmail_address()).build();

        entityCustomerCompareInDTO.setBasic_details(basicDetailDTO);
        entityCustomerCompareInDTO.setPermanent_address(address);
        return entityCustomerCompareInDTO;
    }
}
