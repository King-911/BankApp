package com.politecnico.bankapp.repository;


import com.politecnico.bankapp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CAPA DE ACCESSO A DATOS Y EXTENDS JPAREPOSITORY CREA UN CRUD AUTOMÁTICO

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNumeroIdentificacion(String numeroIdentificacion);
}
