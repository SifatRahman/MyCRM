package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
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

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        Document document =
                builder.parse(inputStream);

        document.getDocumentElement().normalize();

        List<USSanctionIndividualDataDTO> individuals =
                parseIndividuals(document);
//
//        List<USSanctionEntityDataDTO> entities =
//                parseEntities(document);
        List<USSanctionEntityDataDTO> entities = new ArrayList<>();

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

            individual.setDesignation(getNestedListValues(
                            element,
                            "DESIGNATION",
                            "VALUE"));

            individual.setNationality(
                    getNestedListValues(
                            element,
                            "NATIONALITY",
                            "VALUE"));

            individual.setList_type(
                    getNestedListValues(
                            element,
                            "LIST_TYPE",
                            "VALUE"));

            individual.setLast_day_updated(
                    getNestedListValues(
                            element,
                            "LAST_DAY_UPDATED",
                            "VALUE"));


            individual.setLast_reviewed_on(
                    getNestedListValues(
                            element,
                            "NATIONALITY",
                            "VALUE"));

            individual.setIndividual_alias(
                    getIndividualAliases(
                            element,
                            "INDIVIDUAL_DATE_OF_BIRTH",
                            "DATE"));


            individual.setIndividual_address(
                    getNestedValue(
                            element,
                            "INDIVIDUAL_PLACE_OF_BIRTH",
                            "COUNTRY"));
            individual.setTitle(getNestedListValues(
                    element,
                    "TITLE",
                    "VALUE"));
            individual.setIndividual_date_of_birth();
            individual.setIndividual_place_of_birth();
            individual.setIndividual_document();



//            individual.setLow_quality_alias(String.join(", ", individualAlias.get("low")));
//            individual.setGood_quality_alias(String.join(", ", individualAlias.get("good")));

            individuals.add(individual);
        }

        return individuals;
    }

//    private List<USSanctionEntityDataDTO> parseEntities(
//            Document document) {
//
//        List<USSanctionEntityDataDTO> entities =
//                new ArrayList<>();
//
//        NodeList nodes =
//                document.getElementsByTagName("ENTITY");
//
//        for (int i = 0; i < nodes.getLength(); i++) {
//
//            Element element =
//                    (Element) nodes.item(i);
//
//            USSanctionEntityDataDTO entity =
//                    new USSanctionEntityDataDTO();
//
//            entity.setDataId(
//                    getText(element, "DATAID"));
//
//            entity.setFirstName(
//                    getText(element, "FIRST_NAME"));
//
//            entity.setUnListType(
//                    getText(element, "UN_LIST_TYPE"));
//
//            entity.setReferenceNumber(
//                    getText(element, "REFERENCE_NUMBER"));
//
//            entity.setListedOn(
//                    getText(element, "LISTED_ON"));
//
//            entity.setComments(
//                    getText(element, "COMMENTS1"));
//
//            entity.setAliases(
//                    getEntityAliases(
//                            element,
//                            "ENTITY_ALIAS"));
//
//            entities.add(entity);
//        }
//
//        return entities;
//    }

    private String getText(
            Element parent,
            String tagName) {

        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            return "";
        }
        String value = nodes.item(0)
                        .getTextContent()
                        .trim();

        return value.isEmpty() ? "" : value;
    }

    private String getNestedFirstValue(
            Element parent,
            String parentTag,
            String childTag) {

        NodeList parents = parent.getElementsByTagName(parentTag);
        if (parents.getLength() == 0) {
            return "";
        }
        Element parentElement = (Element) parents.item(0);

        return getText(
                parentElement,
                childTag);
    }

    private List<String> getNestedListValues(Element individualElement,
                                       String parentTag,
                                       String childTag){
        List<String> values = new ArrayList<>();
        NodeList parents = individualElement.getElementsByTagName(parentTag);
        if(parents.getLength()==0){
            return values;
        }

        Element innerParentElement = (Element) parents.item(0);
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

    private HashMap<String,List<String>> getIndividualAliases(Element parent, String aliasTag) {
        HashMap<String,List<String>> aliases = new HashMap<>();
        List<String> aliasesGood = new ArrayList<>();
        List<String> aliasesLow = new ArrayList<>();

        NodeList nodes = parent.getElementsByTagName(aliasTag);

        for (int i = 0; i < nodes.getLength(); i++) {

            Element alias = (Element) nodes.item(i);
            String aliasQuality = getText(alias, "QUALITY");
            String aliasName = getText(alias, "ALIAS_NAME");

            if (aliasQuality != null) {
                if(aliasQuality.equals("Good")){
                    aliasesGood.add(aliasName);
                }else{
                    aliasesLow.add(aliasName);
                }
            }
        }
        aliases.put("Good",aliasesGood);
        aliases.put("Low",aliasesLow);
        return aliases;
    }

    private List<String> getEntityAliases(
            Element parent,
            String aliasTag) {

        List<String> aliases = new ArrayList<>();
        NodeList nodes = parent.getElementsByTagName(aliasTag);

        for (int i = 0; i < nodes.getLength(); i++) {

            Element alias = (Element) nodes.item(i);
            String aliasName = getText(alias, "ALIAS_NAME");

            if (aliasName != null) {
                aliases.add(aliasName);
            }
        }

        return aliases;
    }
}
