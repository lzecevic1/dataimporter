package com.company.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class PortedNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.PROTECTED)
    private Integer id;

    private String msisdn;
    private String fileName;

    public PortedNumber(String msisdn, String fileName) {
        this.msisdn = msisdn;
        this.fileName = fileName;
    }
}
