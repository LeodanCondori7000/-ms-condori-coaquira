package com.codigo.mscondoricoaquira.infrastructure.dao;

import com.codigo.mscondoricoaquira.infrastructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {
}
