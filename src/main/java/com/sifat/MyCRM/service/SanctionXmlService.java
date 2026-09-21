package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.SanctionPersonHelperDTO;
import com.sifat.MyCRM.dto.external.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SanctionXmlService {

    public List<SanctionPersonHelperDTO> parseXmlFileTest(InputStream inputStream) throws Exception {

        List<SanctionPersonHelperDTO> people = new ArrayList<>();

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inputStream);

        document.getDocumentElement().normalize();

        NodeList personNodes = document.getElementsByTagName("person");

        for (int i = 0; i < personNodes.getLength(); i++) {

            Node node = personNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element person = (Element) node;

                String name = getValue(person, "name");

                String dob = getValue(person, "dateOfBirth");

                String nationality = getValue(person, "nationality");

                SanctionPersonHelperDTO sanctionPersonHelperDTO =
                        new SanctionPersonHelperDTO(
                                name,
                                dob,
                                nationality
                        );
                people.add(sanctionPersonHelperDTO);
            }
        }
        return people;
    }

    private String getValue(Element element, String tagName) {

        NodeList nodes = element.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        return nodes.item(0)
                .getTextContent()
                .trim();
    }

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

            individual.setDataId(
                    getText(element, "DATAID"));

            individual.setFirstName(
                    getText(element, "FIRST_NAME"));

            individual.setSecondName(
                    getText(element, "SECOND_NAME"));

            individual.setThirdName(
                    getText(element, "THIRD_NAME"));

            individual.setFourthName(
                    getText(element, "FOURTH_NAME"));

            individual.setUnListType(
                    getText(element, "UN_LIST_TYPE"));

            individual.setReferenceNumber(
                    getText(element, "REFERENCE_NUMBER"));

            individual.setListedOn(
                    getText(element, "LISTED_ON"));

            individual.setComments(
                    getText(element, "COMMENTS1"));

            individual.setNationality(
                    getNestedValue(
                            element,
                            "NATIONALITY",
                            "VALUE"));

            individual.setDateOfBirth(
                    getNestedValue(
                            element,
                            "INDIVIDUAL_DATE_OF_BIRTH",
                            "DATE"));

            individual.setBirthCountry(
                    getNestedValue(
                            element,
                            "INDIVIDUAL_PLACE_OF_BIRTH",
                            "COUNTRY"));

            HashMap<String, List<String>> individualAlias = getIndividualAliases(
                    element,
                    "INDIVIDUAL_ALIAS");



            individual.setLow_quality_alias(String.join(", ", individualAlias.get("low")));
            individual.setGood_quality_alias(String.join(", ", individualAlias.get("good")));

            individuals.add(individual);
        }

        return individuals;
    }

    private List<USSanctionEntityDataDTO> parseEntities(
            Document document) {

        List<USSanctionEntityDataDTO> entities =
                new ArrayList<>();

        NodeList nodes =
                document.getElementsByTagName("ENTITY");

        for (int i = 0; i < nodes.getLength(); i++) {

            Element element =
                    (Element) nodes.item(i);

            USSanctionEntityDataDTO entity =
                    new USSanctionEntityDataDTO();

            entity.setDataId(
                    getText(element, "DATAID"));

            entity.setFirstName(
                    getText(element, "FIRST_NAME"));

            entity.setUnListType(
                    getText(element, "UN_LIST_TYPE"));

            entity.setReferenceNumber(
                    getText(element, "REFERENCE_NUMBER"));

            entity.setListedOn(
                    getText(element, "LISTED_ON"));

            entity.setComments(
                    getText(element, "COMMENTS1"));

            entity.setAliases(
                    getEntityAliases(
                            element,
                            "ENTITY_ALIAS"));

            entities.add(entity);
        }

        return entities;
    }

    private String getText(
            Element parent,
            String tagName) {

        NodeList nodes =
                parent.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        String value = nodes.item(0)
                        .getTextContent()
                        .trim();

        return value.isEmpty() ? null : value;
    }

    private String getNestedValue(
            Element parent,
            String parentTag,
            String childTag) {

        NodeList parents =
                parent.getElementsByTagName(parentTag);

        if (parents.getLength() == 0) {
            return null;
        }

        Element parentElement =
                (Element) parents.item(0);

        return getText(
                parentElement,
                childTag);
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
