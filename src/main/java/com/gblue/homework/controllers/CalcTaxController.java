package com.gblue.homework.controllers;

import com.gblue.homework.domain.Tax;
import com.gblue.homework.domain.TaxDTO;
import com.gblue.homework.services.CalcTaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tax")
public class CalcTaxController {
    final CalcTaxService calcTaxService;

    public CalcTaxController(CalcTaxService calcTaxService) {
        this.calcTaxService = calcTaxService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Tax>> listTaxRates() {
        List<Tax> taxRates = null;
        try {
            taxRates = calcTaxService.listTaxRates();
            return ResponseEntity.ok().body(taxRates);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{location}")
    public ResponseEntity<Tax> getTaxRates(@PathVariable("location") String location) {
        System.out.println("Location: " + location);
        Tax taxRates = null;
        try {
            taxRates = calcTaxService.getTaxRates(location);
            return ResponseEntity.ok().body(taxRates);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/calc")
    public ResponseEntity<TaxDTO> calcTax(@RequestBody TaxDTO data) {
        TaxDTO tax = calcTaxService.calcTax(data);
        return ResponseEntity.ok().body(tax);
    }
}
