package com.politecnico.bankapp.controller;

import com.politecnico.bankapp.model.Cliente;
import com.politecnico.bankapp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/saldo/{id}")
    public String consultarSaldo(@PathVariable Long id) {
        return clienteService.consultarSaldo(id);
    }

    @GetMapping("/buscarPorDocumento/{numeroIdentificacion}")
    public Cliente buscarPorDocumento(@PathVariable String numeroIdentificacion) {
        return clienteService.buscarPorDocumento(numeroIdentificacion);
    }

    @PostMapping("/validarClave")
    public String validarClave(@RequestParam String numeroIdentificacion,
                               @RequestParam String clave) {
        return clienteService.validarClave(numeroIdentificacion, clave);
    }

    @PutMapping("/retirar/{id}")
    public String retirar(@PathVariable Long id, @RequestParam double monto) {
        return clienteService.retirar(id, monto);
    }

    @PutMapping("/transferir/{id}")
    public String transferir(@PathVariable Long id, @RequestParam double monto,
                             @RequestParam String numeroIdentificacion) {
        return clienteService.transferir(id, monto, numeroIdentificacion);
    }

    @PostMapping("/guardar")
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardar(cliente);
    }

    @PostMapping("/cerrarSesion/{id}")
    public String cerrarSesion(@PathVariable Long id) {
        return clienteService.cerrarSesion(id);
    }
}