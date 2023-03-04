package dev.miguel.backendmobile.service;

import dev.miguel.backendmobile.domain.dto.MobileDTO;
import dev.miguel.backendmobile.domain.entity.MobileEntity;
import dev.miguel.backendmobile.respository.MobileRespository;
import dev.miguel.backendmobile.utils.MobileMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MobileService {

    private final MobileRespository mobileRespository;
    private final MobileMapper mobileMapper;

    public MobileService(MobileRespository mobileRespository, MobileMapper mobileMapper){
        this.mobileRespository = mobileRespository;
        this.mobileMapper = mobileMapper;
    }

    public void save(MobileDTO mobileDTO){
        mobileRespository.save(mobileMapper.dtoToEntity(mobileDTO));
    }

    public List<MobileDTO> findAll() {
        List<MobileEntity> entities = mobileRespository.findAll();
        List<MobileDTO> mobileDTOList = new ArrayList<>();
        entities.forEach( entity -> mobileDTOList.add(mobileMapper.entityToDto(entity)));
        return mobileDTOList;
    }
}
