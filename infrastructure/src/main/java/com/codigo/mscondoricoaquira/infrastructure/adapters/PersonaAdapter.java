package com.codigo.mscondoricoaquira.infrastructure.adapters;

import com.codigo.mscondoricoaquira.domain.aggregates.constants.Constant;
import com.codigo.mscondoricoaquira.domain.aggregates.dto.PersonaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.dto.ReniecDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.PersonaRequest;
import com.codigo.mscondoricoaquira.domain.ports.out.PersonaServiceOut;
import com.codigo.mscondoricoaquira.infrastructure.client.ClientReniec;
import com.codigo.mscondoricoaquira.infrastructure.dao.EmpresaRepository;
import com.codigo.mscondoricoaquira.infrastructure.dao.PersonaRepository;
import com.codigo.mscondoricoaquira.infrastructure.entity.EmpresaEntity;
import com.codigo.mscondoricoaquira.infrastructure.entity.PersonaEntity;
import com.codigo.mscondoricoaquira.infrastructure.mapper.PersonaMapper;
import com.codigo.mscondoricoaquira.infrastructure.redis.RedisService;
import com.codigo.mscondoricoaquira.infrastructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {
    private final PersonaRepository personaRepository;
    private final EmpresaRepository empresaRepository;
    private final ClientReniec clientReniec;
    private final RedisService redisService;
    @Value("${api.token}")
    private String tokenApi;
    @Override
    public PersonaDto crearPersonaOut(PersonaRequest personaRequest) {
        PersonaEntity personaEntity = getEntity(personaRequest,false, null);
        return PersonaMapper.fromEntity(personaRepository.save(personaEntity));
    }
    private PersonaEntity getEntity(PersonaRequest personaRequest, boolean actualizar, Long id){
        ReniecDto reniecDto = getExecReniec(personaRequest.getNumeroDocumento());
        PersonaEntity entity = new PersonaEntity();
        entity.setNumeroDocumento(reniecDto.getNumeroDocumento());
        entity.setNombre(reniecDto.getNombres());
        entity.setApellido(reniecDto.getApellidoPaterno() + ", " + reniecDto.getApellidoMaterno());
        entity.setTipoDocumento(reniecDto.getTipoDocumento());
        Optional<EmpresaEntity> optionalEmpresa = empresaRepository.findById(personaRequest.getEmpresa_id());
        if (optionalEmpresa.isPresent()) {
            entity.setEmpresa(optionalEmpresa.get());
        } else {
            throw new RuntimeException("Empresa with ID: " + personaRequest.getEmpresa_id() + " not found");
        }

        entity.setEstado(Constant.STATUS_ACTIVE);

        if(actualizar){
            Optional<PersonaEntity> ancientEntityOp = personaRepository.findById(id);
            entity.setFechaCreacion(ancientEntityOp.get().getFechaCreacion());
            entity.setUsuarioCrea(ancientEntityOp.get().getUsuarioCrea());
            entity.setId(id);
            entity.setUsuarioModifica(Constant.USU_ADMIN);
            entity.setFechaModificacion(getTimestamp());
            entity.setEmail(personaRequest.getEmail());
            entity.setTelefono(personaRequest.getTelefono());
            entity.setDireccion(personaRequest.getDireccion());
        } else {
            entity.setUsuarioCrea(Constant.USU_ADMIN);
            entity.setFechaCreacion(getTimestamp());
        }
        return entity;
    }
    private ReniecDto getExecReniec(String numDoc){
        String authorization = "Bearer " + tokenApi;
        return clientReniec.getInfoReniec(numDoc,authorization);
    }
    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }
    @Override
    public Optional<PersonaDto> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENERPERSONA + id);
        if(redisInfo != null){
            PersonaDto personaDto = Util.convertirDesdeString(redisInfo,PersonaDto.class);
            return Optional.of(personaDto);
        } else {

            Optional<PersonaEntity> optionalPersonaEntity = personaRepository.findById(id);
            if (optionalPersonaEntity.isPresent()) {
                PersonaDto personaDto = PersonaMapper.fromEntity(optionalPersonaEntity.get());
                String dataForRedis = Util.convertirAString(personaDto);
                redisService.saveInRedis(Constant.REDIS_KEY_OBTENERPERSONA + id,dataForRedis,600);
                return Optional.of(personaDto);
            } else {
                throw new NoSuchElementException("No Persona found with ID: " + id);
            }
        }
    }
    @Override
    public List<PersonaDto> buscarTodosOut() {
        List<PersonaEntity> entidades = personaRepository.findAll();
        return entidades.stream()
                //.map(PersonaMapper::fromEntity)
                .map(entity -> PersonaMapper.fromEntity(entity))
                .collect(Collectors.toList());
    }
    @Override
    public PersonaDto actualizarOut(Long id, PersonaRequest personaRequest) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        if(datoExtraido.isPresent()){
            PersonaEntity personaEntity = getEntity(personaRequest,true, id);
            PersonaDto personaDto = PersonaMapper.fromEntity(personaRepository.save(personaEntity));

            String dataForRedis = Util.convertirAString(personaDto);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENERPERSONA + id, dataForRedis, 600);

            return personaDto;
        } else {
            throw new RuntimeException();
        }
    }
    @Override
    public PersonaDto eliminarOut(Long id) {
        Optional<PersonaEntity> datoExtraido = personaRepository.findById(id);
        if(datoExtraido.isPresent()){

            PersonaEntity personaEntity = datoExtraido.get();
            personaEntity.setEstado(0); // Mark as deleted (adjust based on your logic)
            personaEntity.setUsuarioBorra(Constant.USU_ADMIN);
            personaEntity.setFechaBorrado(getTimestamp());
            personaRepository.save(personaEntity);

            redisService.deleteKey(Constant.REDIS_KEY_OBTENERPERSONA + id);

            return PersonaMapper.fromEntity(personaEntity);
        } else {
            throw new RuntimeException();
        }
    }
}
