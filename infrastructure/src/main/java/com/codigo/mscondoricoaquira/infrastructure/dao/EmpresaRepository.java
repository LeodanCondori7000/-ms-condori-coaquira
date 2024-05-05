package com.codigo.mscondoricoaquira.infrastructure.dao;

import com.codigo.mscondoricoaquira.infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
}
