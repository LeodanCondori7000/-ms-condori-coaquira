package com.codigo.mscondoricoaquira.infrastructure.adapters;

import com.codigo.mscondoricoaquira.domain.aggregates.constants.Constant;
import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.codigo.mscondoricoaquira.domain.aggregates.dto.SunatDto;
import com.codigo.mscondoricoaquira.domain.aggregates.request.EmpresaRequest;
import com.codigo.mscondoricoaquira.domain.ports.out.EmpresaServiceOut;
import com.codigo.mscondoricoaquira.infrastructure.client.ClientSunat;
import com.codigo.mscondoricoaquira.infrastructure.dao.EmpresaRepository;
import com.codigo.mscondoricoaquira.infrastructure.entity.EmpresaEntity;
import com.codigo.mscondoricoaquira.infrastructure.mapper.EmpresaMapper;
import com.codigo.mscondoricoaquira.infrastructure.redis.RedisService;
import com.codigo.mscondoricoaquira.infrastructure.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class EmpresaAdapter implements EmpresaServiceOut {
    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;
    private final RedisService redisService;
    @Value("${api.token}")
    private String tokenApi;
    @Override
    public EmpresaDto crearEmpresaOut(EmpresaRequest empresaRequest) {
        EmpresaEntity empresaEntity = this.getEntity(empresaRequest,false,null);
        return EmpresaMapper.fromEntity(empresaRepository.save(empresaEntity));
    }
    private EmpresaEntity getEntity(EmpresaRequest empresaRequest, boolean actualizar,Long id){
        SunatDto sunatDto = this.getExecSunat(empresaRequest.getNumeroDocumento());
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setRazonSocial(sunatDto.getRazonSocial());
        empresaEntity.setTipoDocumento(sunatDto.getTipoDocumento());
        empresaEntity.setNumeroDocumento(sunatDto.getNumeroDocumento());
        if(sunatDto.getEstado() != "ACTIVO"){
            empresaEntity.setEstado(0);
        }
        empresaEntity.setEstado(Constant.STATUS_ACTIVE);
        empresaEntity.setCondicion(sunatDto.getCondicion());
        empresaEntity.setDireccion(sunatDto.getDireccion());
        empresaEntity.setDistrito(sunatDto.getDistrito());
        empresaEntity.setProvincia(sunatDto.getProvincia());
        empresaEntity.setDepartamento(sunatDto.getDepartamento());
        empresaEntity.setEsAgenteRetencion(sunatDto.isEsAgenteRetencion());

        if(actualizar){
            empresaEntity.setId(id);
            empresaEntity.setUsuarioModifica(Constant.USU_ADMIN);
            empresaEntity.setFechaModificacion(getTimestamp());
            empresaEntity.setDireccion(empresaRequest.getDireccion());
            empresaEntity.setDistrito(empresaRequest.getDistrito());
            empresaEntity.setProvincia(empresaRequest.getProvincia());
            empresaEntity.setDepartamento(empresaRequest.getDepartamento());
        } else {
            empresaEntity.setFechaCreacion(getTimestamp());
            empresaEntity.setUsuarioCrea(Constant.USU_ADMIN);
        }
        return empresaEntity;
    }
    private SunatDto getExecSunat(String numDoc){
        String authorization = "Bearer " + tokenApi;
        return clientSunat.getInfoSunat(numDoc,authorization);
    }
    private Timestamp getTimestamp(){
        long currenTime = System.currentTimeMillis();
        return new Timestamp(currenTime);
    }
    @Override
    public Optional<EmpresaDto> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENEREMPRESA + id);

        if(redisInfo != null){
            EmpresaDto empresaDto = Util.convertirDesdeString(redisInfo,EmpresaDto.class);
            return Optional.of(empresaDto);
        } else {
            EmpresaDto empresaDto = EmpresaMapper.fromEntity(empresaRepository.findById(id).get());
            String dataForRedis = Util.convertirAString(empresaDto);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENEREMPRESA + id,dataForRedis,600);
            return Optional.of(empresaDto);
        }
    }
    @Override
    public List<EmpresaDto> buscarTodosOut() {
        return empresaRepository.findAll()
                .stream()
                .map(EmpresaMapper::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public EmpresaDto actualizarOut(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> dato = empresaRepository.findById(id);
        if(dato.isPresent()){
            EmpresaEntity empresaEntity = getEntity(empresaRequest, true, id);
            empresaEntity.setUsuarioCrea(dato.get().getUsuarioCrea());
            empresaEntity.setFechaCreacion(dato.get().getFechaCreacion());

            EmpresaEntity savedEntity = empresaRepository.save(empresaEntity);

            if (savedEntity != null) {
                EmpresaDto empresaDto = EmpresaMapper.fromEntity(savedEntity);
                String dataForRedis = Util.convertirAString(empresaDto);
                redisService.saveInRedis(Constant.REDIS_KEY_OBTENEREMPRESA + id, dataForRedis, 600);

                // redisService.deleteKey(Constant.REDIS_KEY_OBTENERPERSONA + "*");

                Set<String> personaKeys = redisService.getKeysByPattern(Constant.REDIS_KEY_OBTENERPERSONA + "*");
                for (String personaKey : personaKeys) {
                    redisService.deleteKey(personaKey);
                }
            }

            return EmpresaMapper.fromEntity(savedEntity);
        } else {
            System.out.println("Here i am! else");
            throw new RuntimeException();
        }
    }
    @Override
    public EmpresaDto eliminarOut(Long id) {
        Optional<EmpresaEntity> dato = empresaRepository.findById(id);
        if(dato.isPresent()){

            EmpresaEntity empresaEntity = dato.get();
            empresaEntity.setEstado(0);
            empresaEntity.setUsuarioBorra(Constant.USU_ADMIN);
            empresaEntity.setFechaBorrado(getTimestamp());
            empresaRepository.save(empresaEntity);

            redisService.deleteKey(Constant.REDIS_KEY_OBTENEREMPRESA + id);
            // Invalidate related Persona data (soft invalidation)
            Set<String> personaKeys = redisService.getKeysByPattern(Constant.REDIS_KEY_OBTENERPERSONA + "*");
            for (String personaKey : personaKeys) {
                redisService.deleteKey(personaKey);
            }

            return EmpresaMapper.fromEntity(empresaEntity);
        } else {
            throw new RuntimeException();
        }
    }
    //tbd
    private void inspectObject(Object obj) {
        if (obj == null) {
            System.out.println("Object is null");
            return;
        }

        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);
                System.out.println("Field: " + fieldName + ", Value: " + fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
