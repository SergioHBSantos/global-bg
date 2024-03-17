package com.gblue.homework;

import com.gblue.homework.domain.TaxDTO;
import com.gblue.homework.services.CalcTaxService;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalcTaxService {

    CalcTaxService service;

    @Test
    @DisplayName("Calculation test")
    public void testCalc() {
        service = new CalcTaxService();
        assertNotNull(service, "Service should be available");

        TaxDTO taxDTO = new TaxDTO(100, 0,0,20);
        assertEquals(100, taxDTO.getNetAmount(), "The net amount should be 100");

        TaxDTO taxDTOResult = new TaxDTO(100, 20,120,20);
        assertEquals(taxDTOResult,service.calcTax(taxDTO), "Value calculation should work");
    }

    @Test
    @DisplayName("Get tax rates from location")
    public void testGetTaxRates() throws IOException, ParseException {
        service = new CalcTaxService();
        assertNotNull(service, "Service should be available");

        String location = "London";
        assertNull(service.getTaxRates(location), "Location should not be available");
        location = "Austria";
        assertNotNull(service.getTaxRates(location), "Location should be available");
    }

    @Test
    @DisplayName("List all tax rates")
    public void testListTaxRates() throws IOException, ParseException {
        service = new CalcTaxService();
        assertNotNull(service, "Service should be available");

        assertTrue(service.listTaxRates().size() > 0, "Tax rates should be available");
    }

    @Test
    @DisplayName("Invalid input test")
    public void testInvalidInput() {
        service = new CalcTaxService();
        assertNotNull(service, "Service should be available");

        TaxDTO taxDTO = new TaxDTO(10, 10,0,20);
        assertThrows(IllegalArgumentException.class, () -> service.calcTax(taxDTO), "2 input values should throw an exception");

        TaxDTO taxDTOTax = new TaxDTO(10, 0,0,0);
        assertThrows(IllegalArgumentException.class, () -> service.calcTax(taxDTOTax), "Vat tax rate = 0 should throw an exception");

    }



}
