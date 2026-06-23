package com.politecnico.bankapp.service;

import com.politecnico.bankapp.model.Cajero;
import com.politecnico.bankapp.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService {

    @Autowired
    private CajeroRepository cajeroRepository;

    // CONSULTAR BILLETES
    public String consultarBilletes(Long id) {
        Cajero cajero = cajeroRepository.findById(id).orElseThrow();
        return "Billetes disponibles - $100k: " + cajero.getBilletesCien() +
                ", $50k: " + cajero.getBilletesCincuenta() +
                ", $20k: " + cajero.getBilletesVeinte() +
                ", $10k: " + cajero.getBilletesDiez();
    }

    // DISPENSAR BILLETES
    public String dispensarBilletes(Long id, double monto) {
        Cajero cajero = cajeroRepository.findById(id).orElseThrow();

        if (monto % 10000 != 0) {
            return "Monto debe ser múltiplo de $10,000";
        }

        int totalCash = (cajero.getBilletesCien() * 100000) +
                (cajero.getBilletesCincuenta() * 50000) +
                (cajero.getBilletesVeinte() * 20000) +
                (cajero.getBilletesDiez() * 10000);

        if (monto > totalCash) {
            return "Cajero sin fondos suficientes";
        }

        int restante = (int) monto;

        int cien = Math.min((int)(restante / 100000), cajero.getBilletesCien());
        restante -= cien * 100000;

        int cincuenta = Math.min((int)(restante / 50000), cajero.getBilletesCincuenta());
        restante -= cincuenta * 50000;

        int veinte = Math.min((int)(restante / 20000), cajero.getBilletesVeinte());
        restante -= veinte * 20000;

        int diez = Math.min((int)(restante / 10000), cajero.getBilletesDiez());
        restante -= diez * 10000;

        if (restante != 0) {
            return "No hay combinación de billetes disponible para ese monto";
        }

        cajero.setBilletesCien(cajero.getBilletesCien() - cien);
        cajero.setBilletesCincuenta(cajero.getBilletesCincuenta() - cincuenta);
        cajero.setBilletesVeinte(cajero.getBilletesVeinte() - veinte);
        cajero.setBilletesDiez(cajero.getBilletesDiez() - diez);
        cajeroRepository.save(cajero);

        return "Dispensando - $100k: " + cien + ", $50k: " + cincuenta +
                ", $20k: " + veinte + ", $10k: " + diez;
    }
}