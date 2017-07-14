package com.company.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PortedNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String msisdn;

    public PortedNumber(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortedNumber that = (PortedNumber) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return msisdn != null ? msisdn.equals(that.msisdn) : that.msisdn == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (msisdn != null ? msisdn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PortedNumber{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
