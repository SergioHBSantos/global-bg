package com.gblue.homework.domain;

import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Tax {
    public String location;
    public double[] taxRates;

    public static Tax parseObjTax(JSONObject tax) {
        String taxLocation = (String) tax.get("location");

        JSONArray taxRateJson = (JSONArray) tax.get("taxRates");
        double[] taxRate = new double[taxRateJson.size()];
        for (int i = 0; i < taxRateJson.size(); i++) {
            taxRate[i] = Double.parseDouble(taxRateJson.get(i).toString());
        }
        Tax taxObj = new Tax(taxLocation, taxRate);

        return taxObj;
    }
}
