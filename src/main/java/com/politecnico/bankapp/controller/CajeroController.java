package com.politecnico.bankapp.controller;


import com.politecnico.bankapp.service.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    private CajeroService cajeroService;

    @GetMapping("/consultarBilletes/{id}")
    public String consultarBilletes(@PathVariable Long id) {
        return cajeroService.consultarBilletes(id);
    }

    @PutMapping("/dispensarBilletes/{id}")
    public String dispensarBilletes(@PathVariable Long id, @RequestParam double monto) {
        return cajeroService.dispensarBilletes(id, monto);
    }

}
