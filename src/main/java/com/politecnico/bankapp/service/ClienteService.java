package com.politecnico.bankapp.service;

import com.politecnico.bankapp.model.Cliente;
import com.politecnico.bankapp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> sessionTimer;

    // BUSCAR CLIENTE POR DOCUMENTO
    public Cliente buscarPorDocumento(String numeroIdentificacion) {
        return clienteRepository.findByNumeroIdentificacion(numeroIdentificacion);
    }

    // VALIDAR CLAVE
    public String validarClave(String numeroIdentificacion, String clave) {
        Cliente cliente = clienteRepository.findByNumeroIdentificacion(numeroIdentificacion);

        if (cliente == null) {
            return "Cliente no encontrado";
        }

        if (cliente.isBloqueado()) {
            return "Cliente bloqueado. Espere 10 minutos";
        }

        if (cliente.getClave().equals(clave)) {
            cliente.setSesionActiva(true);
            cliente.setIntentosFallidos(0);
            clienteRepository.save(cliente);
            iniciarTimer(cliente.getId());
            return "Acceso concedido";
        } else {
            cliente.setIntentosFallidos(cliente.getIntentosFallidos() + 1);

            if (cliente.getIntentosFallidos() >= 3) {
                cliente.setBloqueado(true);
                clienteRepository.save(cliente);

                scheduler.schedule(() -> {
                    cliente.setBloqueado(false);
                    cliente.setIntentosFallidos(0);
                    clienteRepository.save(cliente);
                }, 10, TimeUnit.MINUTES);

                return "Cliente bloqueado por 3 intentos fallidos";
            }

            clienteRepository.save(cliente);
            return "Clave incorrecta. Intento " + cliente.getIntentosFallidos() + " de 3";
        }
    }

    // INICIAR/REINICIAR TIMER DE SESIÓN
    private void iniciarTimer(Long id) {
        if (sessionTimer != null && !sessionTimer.isDone()) {
            sessionTimer.cancel(false);
        }
        sessionTimer = scheduler.schedule(() -> {
            cerrarSesion(id);
        }, 180, TimeUnit.SECONDS);
    }

    // CONSULTAR SALDO
    public String consultarSaldo(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        if (!cliente.isSesionActiva()) {
            return "Sesión no activa";
        }
        iniciarTimer(id);
        return "Tu saldo es: " + cliente.getSaldoCuenta();
    }

    // RETIRAR
    public String retirar(Long id, double monto) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        if (!cliente.isSesionActiva()) {
            return "Sesión no activa";
        }
        iniciarTimer(id);

        if (cliente.getSaldoCuenta() < monto) {
            return "Fondos insuficientes";
        }

        cliente.setSaldoCuenta(cliente.getSaldoCuenta() - monto);
        clienteRepository.save(cliente);
        return "Tu nuevo saldo es: " + cliente.getSaldoCuenta();
    }

    // TRANSFERIR
    public String transferir(Long id, double monto, String numeroIdentificacion) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        if (!cliente.isSesionActiva()) {
            return "Sesión no activa";
        }
        iniciarTimer(id);

        if (cliente.getSaldoCuenta() < monto) {
            return "Fondos insuficientes";
        }

        Cliente destino = clienteRepository.findByNumeroIdentificacion(numeroIdentificacion);
        if (destino == null) {
            return "Cliente no encontrado";
        }

        cliente.setSaldoCuenta(cliente.getSaldoCuenta() - monto);
        clienteRepository.save(cliente);
        destino.setSaldoCuenta(destino.getSaldoCuenta() + monto);
        clienteRepository.save(destino);

        return "Transferencia exitosa. Tu nuevo saldo es: " + cliente.getSaldoCuenta();
    }

    // GUARDAR
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // CERRAR SESIÓN
    public String cerrarSesion(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        cliente.setSesionActiva(false);
        clienteRepository.save(cliente);
        return "Sesión cerrada";
    }
}