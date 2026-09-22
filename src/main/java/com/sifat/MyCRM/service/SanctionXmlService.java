package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.USSanctionAddressDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionAliasDTO;
import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDOBDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDocDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualPOBDataDTO;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SanctionXmlService {


    public USSanctionListDataOutDTO parseXmlFile(InputStream inputStream)
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inputStream);

        document.getDocumentElement().normalize();

        List<USSanctionIndividualDataDTO> individuals =
                parseIndividuals(document);

        List<USSanctionEntityDataDTO> entities =
                parseEntities(document);

        return new USSanctionListDataOutDTO(
                individuals,
                entities
        );
    }

    private List<USSanctionIndividualDataDTO> parseIndividuals(
            Document document) {

        List<USSanctionIndividualDataDTO> individuals = new ArrayList<>();

        NodeList nodes = document.getElementsByTagName("INDIVIDUAL");

        for (int i = 0; i < nodes.getLength(); i++) {

            Element element = (Element) nodes.item(i);

            USSanctionIndividualDataDTO individual = new USSanctionIndividualDataDTO();

            individual.setData_id(getText(element, "DATAID"));
            individual.setVersion_no(getText(element, "VERSIONNUM"));
            individual.setFirst_name(getText(element, "FIRST_NAME"));
            individual.setSecond_name(getText(element, "SECOND_NAME"));
            individual.setThird_name(getText(element, "THIRD_NAME"));
            individual.setFourth_name(getText(element, "FOURTH_NAME"));

            individual.setUn_list_type(getText(element, "UN_LIST_TYPE"));
            individual.setReference_number(getText(element, "REFERENCE_NUMBER"));
            individual.setListed_on(getText(element, "LISTED_ON"));
            individual.setName_original_script(getText(element, "NAME_ORIGINAL_SCRIPT"));
            individual.setComments1(getText(element, "COMMENTS1"));
            individual.setHas_interpol_link(getText(element, "HAS_INTERPOL_LINK"));
            individual.setInterpol_link(getText(element, "INTERPOL_LINK"));

            individual.setDesignation(getLinearChildrenValuesOfOneElement(
                            element,
                            "DESIGNATION",
                            "VALUE"));

            individual.setNationality(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "NATIONALITY",
                            "VALUE"));

            individual.setList_type(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LIST_TYPE",
                            "VALUE"));

            individual.setLast_day_updated(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LAST_DAY_UPDATED",
                            "VALUE"));

            individual.setLast_reviewed_on(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LAST_REVIEWED_ON",
                            "VALUE"));

            individual.setIndividual_alias(getAliases(element,"INDIVIDUAL"));
            individual.setIndividual_address(getAddress(element,"INDIVIDUAL"));

            individual.setTitle(getLinearChildrenValuesOfOneElement(
                    element,
                    "TITLE",
                    "VALUE"));

            individual.setIndividual_date_of_birth(getIndividualDOB(element));
            individual.setIndividual_place_of_birth(getIndividualPOB(element));
            individual.setIndividual_document(getIndividualDOC(element));

            individuals.add(individual);
        }

        return individuals;
    }



    private List<USSanctionEntityDataDTO> parseEntities(Document document) {

        List<USSanctionEntityDataDTO> entities = new ArrayList<>();

        NodeList nodes = document.getElementsByTagName("ENTITY");

        for (int i = 0; i < nodes.getLength(); i++) {

            Element element = (Element) nodes.item(i);

            USSanctionEntityDataDTO entity = new USSanctionEntityDataDTO();

            entity.setData_id(getText(element, "DATAID"));
            entity.setVersion_no(getText(element, "VERSIONNUM"));
            entity.setFirst_name(getText(element, "FIRST_NAME"));

            entity.setUn_list_type(getText(element, "UN_LIST_TYPE"));
            entity.setReference_number(getText(element, "REFERENCE_NUMBER"));
            entity.setListed_on(getText(element, "LISTED_ON"));
            entity.setName_original_script(getText(element, "NAME_ORIGINAL_SCRIPT"));
            entity.setComments1(getText(element, "COMMENTS1"));
            entity.setHas_interpol_link(getText(element, "HAS_INTERPOL_LINK"));
            entity.setInterpol_link(getText(element, "INTERPOL_LINK"));

            entity.setList_type(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LIST_TYPE",
                            "VALUE"));

            entity.setLast_day_updated(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LAST_DAY_UPDATED",
                            "VALUE"));


            entity.setLast_reviewed_on(
                    getLinearChildrenValuesOfOneElement(
                            element,
                            "LAST_REVIEWED_ON",
                            "VALUE"));

            entity.setEntity_alias(getAliases(element,"ENTITY"));

            entity.setEntity_address(getAddress(element,"ENTITY"));
            entities.add(entity);
        }

        return entities;
    }


    private String getText(Element element,
            String tagName) {

        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return "";
        }
        String value = nodes.item(0)
                        .getTextContent()
                        .trim();

        return value.isEmpty() ? "" : value;
    }


    private List<String> getLinearChildrenValuesOfOneElement(Element parentElement,
                                       String elementTag,
                                       String childTag){
        List<String> values = new ArrayList<>();
        NodeList foundElements = parentElement.getElementsByTagName(elementTag);
        if(foundElements.getLength()==0){
            return values;
        }

        Element innerParentElement = (Element) foundElements.item(0);
        NodeList children = innerParentElement.getElementsByTagName(childTag);

        for (int i = 0; i < children.getLength(); i++) {
            String value = children.item(i)
                    .getTextContent()
                    .trim();

            if (!value.isEmpty()) {
                values.add(value);
            }
        }
        return values;
    }

    private List<USSanctionAliasDTO> getAliases(Element individual, String customerType) {
        List<USSanctionAliasDTO> aliases = new ArrayList<>();

        NodeList nodes = individual.getElementsByTagName(String.format(customerType+"_ALIAS"));

        for (int i = 0; i < nodes.getLength(); i++) {
            Element alias = (Element) nodes.item(i);
            USSanctionAliasDTO sanctionAliasDTO = new USSanctionAliasDTO();
            sanctionAliasDTO.setQuality(getText(alias, "QUALITY"));
            sanctionAliasDTO.setAlias_name(getText(alias, "ALIAS_NAME"));
            aliases.add(sanctionAliasDTO);
        }
        return aliases;
    }

    private List<USSanctionAddressDataDTO> getAddress(Element individual, String customerType) {
        List<USSanctionAddressDataDTO> aliases = new ArrayList<>();

        NodeList nodes = individual.getElementsByTagName(String.format(customerType+"_ADDRESS"));

        for (int i = 0; i < nodes.getLength(); i++) {
            Element alias = (Element) nodes.item(i);

            USSanctionAddressDataDTO addressDataDTO = new USSanctionAddressDataDTO();
            addressDataDTO.setStreet(getText(alias, "STREET"));
            addressDataDTO.setCity(getText(alias, "CITY"));
            addressDataDTO.setZip_code(getText(alias, "ZIP_CODE"));
            addressDataDTO.setState_province(getText(alias, "STATE_PROVINCE"));
            addressDataDTO.setCountry(getText(alias, "COUNTRY"));
            addressDataDTO.setNote(getText(alias, "NOTE"));

            aliases.add(addressDataDTO);
        }
        return aliases;
    }

    private List<USSanctionIndividualDocDTO> getIndividualDOC(Element individual) {
        List<USSanctionIndividualDocDTO> docDTOS = new ArrayList<>();
        NodeList nodes = individual.getElementsByTagName("INDIVIDUAL_DOCUMENT");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element alias = (Element) nodes.item(i);

            USSanctionIndividualDocDTO docDTO = new USSanctionIndividualDocDTO();
            docDTO.setType_of_document(getText(alias, "TYPE_OF_DOCUMENT"));
            docDTO.setType_of_document2(getText(alias, "TYPE_OF_DOCUMENT2"));
            docDTO.setNumber(getText(alias, "NUMBER"));
            docDTO.setIssuing_country(getText(alias, "ISSUING_COUNTRY"));
            docDTO.setDate_of_issue(getText(alias, "DATE_OF_ISSUE"));
            docDTO.setDate_of_expiry(getText(alias, "DATE_OF_EXPIRY"));
            docDTO.setCity_of_issue(getText(alias, "CITY_OF_ISSUE"));
            docDTO.setCountry_of_issue(getText(alias, "COUNTRY_OF_ISSUE"));
            docDTO.setNote(getText(alias, "NOTE"));

            docDTOS.add(docDTO);
        }
        return docDTOS;
    }



    private List<USSanctionIndividualPOBDataDTO> getIndividualPOB(Element individual) {
        List<USSanctionIndividualPOBDataDTO> docDTOS = new ArrayList<>();
        NodeList nodes = individual.getElementsByTagName("INDIVIDUAL_PLACE_OF_BIRTH");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element alias = (Element) nodes.item(i);

            USSanctionIndividualPOBDataDTO docDTO = new USSanctionIndividualPOBDataDTO();
            docDTO.setCity(getText(alias, "CITY"));
            docDTO.setState_province(getText(alias, "STATE_PROVINCE"));
            docDTO.setCountry(getText(alias, "COUNTRY"));
            docDTOS.add(docDTO);
        }
        return docDTOS;
    }
    // ---ok

    private List<USSanctionIndividualDOBDTO> getIndividualDOB(Element individual) {
        List<USSanctionIndividualDOBDTO> docDTOS = new ArrayList<>();
        NodeList nodes = individual.getElementsByTagName("INDIVIDUAL_DATE_OF_BIRTH");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element alias = (Element) nodes.item(i);

            USSanctionIndividualDOBDTO docDTO = new USSanctionIndividualDOBDTO();
            docDTO.setType_of_date(getText(alias, "TYPE_OF_DATE"));
            docDTO.setDate(getText(alias, "DATE"));
            docDTO.setFrom_year(getText(alias, "FROM_YEAR"));
            docDTO.setTo_year(getText(alias, "TO_YEAR"));
            docDTO.setYear(getText(alias, "YEAR"));
            docDTO.setNote(getText(alias, "NOTE"));

            docDTOS.add(docDTO);
        }
        return docDTOS;
    }
}
