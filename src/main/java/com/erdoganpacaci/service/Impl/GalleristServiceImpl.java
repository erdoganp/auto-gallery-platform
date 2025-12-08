package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.DtoAddress;
import com.erdoganpacaci.dto.DtoGallerist;
import com.erdoganpacaci.dto.DtoGalleristUI;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Address;
import com.erdoganpacaci.model.Car;
import com.erdoganpacaci.model.Gallerist;
import com.erdoganpacaci.model.GalleristCar;
import com.erdoganpacaci.repository.AddressRepository;
import com.erdoganpacaci.repository.GalleristCarRepository;
import com.erdoganpacaci.repository.GalleristRepository;
import com.erdoganpacaci.service.GalleristService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GalleristServiceImpl implements GalleristService {


    @Autowired
    private GalleristRepository galleristRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GalleristCarRepository galleristCarRepository;

    private Gallerist createGallerist(DtoGalleristUI dtoGalleristUI){

        Optional<Address> optAddress=addressRepository.findById(dtoGalleristUI.getAddressId());

        if(optAddress.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristUI.getAddressId().toString()));

        }

        Gallerist gallerist = new Gallerist();
        gallerist.setCreateTime(new Date());

        BeanUtils.copyProperties(dtoGalleristUI,gallerist);
        gallerist.setAddress(optAddress.get());
        return gallerist;
    }



    @Override
    public DtoGallerist saveGallerist(DtoGalleristUI dtoGalleristUI) {

        DtoGallerist dtoGallerist = new DtoGallerist();
        DtoAddress dtoAddress = new DtoAddress();

        Optional<Gallerist> optionalGallerist =galleristRepository.findWithAddress(dtoGalleristUI.getAddressId());
        if(!optionalGallerist.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.DATA_IS_ALREADY_USED, "addressid : " +dtoGalleristUI.getAddressId().toString()));

        }

       Gallerist savedGallerist= galleristRepository.save(createGallerist(dtoGalleristUI));

       BeanUtils.copyProperties(savedGallerist,dtoGallerist);
       BeanUtils.copyProperties(savedGallerist.getAddress(),dtoAddress);
       dtoGallerist.setAddress(dtoAddress);
        return dtoGallerist;
    }

    @Override
    public DtoGallerist updateGallerist(Long id, DtoGalleristUI dtoGalleristUI) {

        DtoGallerist dtoGallerist =new DtoGallerist();
        DtoAddress dtoAddress = new DtoAddress();

        Optional<Gallerist> optionalGallerist = galleristRepository.findById(id);

        if(optionalGallerist.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

        }

        Gallerist gallerist = optionalGallerist.get();
        Address address= optionalGallerist.get().getAddress();

        BeanUtils.copyProperties(dtoGalleristUI,gallerist);
        gallerist.setAddress(address);

       Gallerist dbGallerist= galleristRepository.save(gallerist);
       BeanUtils.copyProperties(dbGallerist,dtoGallerist);
       BeanUtils.copyProperties(dbGallerist.getAddress(),dtoAddress);

       dtoGallerist.setAddress(dtoAddress);

       return dtoGallerist;
    }

    @Override
    public Void deleteGallerist(Long id) {

        Optional<Gallerist> optionalGallerist=galleristRepository.findById(id);

        if(optionalGallerist.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

        }


        Optional<GalleristCar> optionalGalleristCar=galleristCarRepository.findWithGallerist(id);

        if(!optionalGalleristCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.GALLERIST_IS_ACTIVE, id.toString()));

        }


        Gallerist dbGallerist=optionalGallerist.get();
        galleristRepository.delete(dbGallerist);

        return null;

    }
}
