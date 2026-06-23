package com.politecnico.bankapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// @Entity  CREAR AUTOMÁTICAMENTE LA TABLA EN SPRING
@Entity

public class Cajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //GENERAMOS LOS GETTER Y SETTER DE ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private int billetesDiez;
    private int billetesVeinte;
    private int billetesCincuenta;
    private int billetesCien;

    // GENERAMOS GETTERS Y SETTERS


    public int getBilletesDiez() {
        return billetesDiez;
    }

    public void setBilletesDiez(int billetesDiez) {
        this.billetesDiez = billetesDiez;
    }

    public int getBilletesVeinte() {
        return billetesVeinte;
    }

    public void setBilletesVeinte(int billetesVeinte) {
        this.billetesVeinte = billetesVeinte;
    }

    public int getBilletesCincuenta() {
        return billetesCincuenta;
    }

    public void setBilletesCincuenta(int billetesCincuenta) {
        this.billetesCincuenta = billetesCincuenta;
    }

    public int getBilletesCien() {
        return billetesCien;
    }

    public void setBilletesCien(int billetesCien) {
        this.billetesCien = billetesCien;
    }
}
