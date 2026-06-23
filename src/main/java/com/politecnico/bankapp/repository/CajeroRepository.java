package com.politecnico.bankapp.repository;


import com.politecnico.bankapp.model.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// CAPA DE ACCESSO A DATOS Y EXTENDS JPAREPOSITORY CREA UN CRUD AUTOMÁTICO

@Repository
public interface CajeroRepository extends JpaRepository<Cajero, Long>  {
}
