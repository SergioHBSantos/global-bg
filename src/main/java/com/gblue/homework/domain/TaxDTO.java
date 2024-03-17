package com.gblue.homework.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TaxDTO {
    private double netAmount;
    private double vatAmount;
    private double grossAmount;
    private double vatRate;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TaxDTO taxDTO = (TaxDTO) obj;
        return netAmount == taxDTO.netAmount && vatAmount == taxDTO.vatAmount && grossAmount == taxDTO.grossAmount && vatRate == taxDTO.vatRate;
    }
}
