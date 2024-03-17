package com.gblue.homework.services;

import com.gblue.homework.domain.Tax;
import com.gblue.homework.domain.TaxDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class CalcTaxService {

    /**
     * Get the tax rates for a location
     *
     * @param location String
     * @return Tax
     */
    public Tax getTaxRates(String location) throws IOException, ParseException {
        List<Tax> taxRates = readTaxRates();
        return taxRates.stream().filter(item -> item.getLocation().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

    /**
     * List all locations with tax rates
     *
     * @return List<Tax>
     */
    public List<Tax> listTaxRates() throws IOException, ParseException {
        return readTaxRates();
    }

    /**
     * Calculates Net, Gross and Vat based on the input data
     *
     * @param data TaxDTO
     * @return String
     */
    public TaxDTO calcTax(TaxDTO data) {
        validateInputs(data);

        double factor = data.getVatRate() / 100;

        if (data.getNetAmount() > 0) { //calculate based on net amount
            //calc vat
            data.setVatAmount(data.getNetAmount() * factor);
            //calc gross
            data.setGrossAmount(data.getNetAmount() + data.getVatAmount());
        } else if (data.getGrossAmount() > 0) { //calculate based on gross amount
            //calc net
            data.setNetAmount(data.getGrossAmount() / (1 + factor));
            //calc vat
            data.setVatAmount(data.getGrossAmount() - data.getNetAmount());
        } else if (data.getVatAmount() > 0) { //calculate based on vat amount
            //calc net
            data.setNetAmount(data.getVatAmount() / (factor));
            //calc gross
            data.setGrossAmount(data.getNetAmount() + data.getVatAmount());
        }

        return data;
    }

    /**
     * Read the tax rates from the json file simulating a database
     *
     * @return List<Tax>
     */
    private List<Tax> readTaxRates() throws IOException, ParseException {
        ArrayList<Tax> taxList = new ArrayList<>();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        String fileName = "static/tax_location.json";

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);

        File file = null;
        if (resource != null) {
            file = new File(resource.getFile());
        }

        if (file != null) {
            FileReader reader = new FileReader(file);
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray taxLocationList;
            taxLocationList = (JSONArray) obj;

            //Iterate over taxLocation array parsing each tax object
            taxLocationList.forEach(tax -> taxList.add(Tax.parseObjTax((JSONObject) tax)));

            return taxList;
        }
        return taxList;
    }


    private void validateInputs(TaxDTO data) throws IllegalArgumentException {
        if (data.getVatRate() == 0d) {
            throw new IllegalArgumentException("Tax rate is required");
        }

        if (data.getNetAmount() == 0d && data.getGrossAmount() == 0d && data.getVatAmount() == 0d) {
            throw new IllegalArgumentException("One of net, gross or vat is required");
        }

        boolean net = data.getNetAmount() > 0d;
        boolean gross = data.getGrossAmount() > 0d;
        boolean vat = data.getVatAmount() > 0d;
        if (!(net ^ gross ^ vat)) {
            throw new IllegalArgumentException("Only one of net, gross or vat is required");
        }
    }
}
