package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.*;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Car;
import com.erdoganpacaci.model.Gallerist;
import com.erdoganpacaci.model.GalleristCar;
import com.erdoganpacaci.repository.CarRepository;
import com.erdoganpacaci.repository.GalleristCarRepository;
import com.erdoganpacaci.repository.GalleristRepository;
import com.erdoganpacaci.service.GalleristCarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GalleristCarServiceImpl implements GalleristCarService {

    @Autowired
    private GalleristCarRepository galleristCarRepository;


    @Autowired
    private GalleristRepository galleristRepository;

    @Autowired
    private CarRepository carRepository;


    private GalleristCar createGalleristCar(DtoGalleristCarUI dtoGalleristCarUI) {

        Optional<Gallerist> optGallerist=galleristRepository.findById(dtoGalleristCarUI.getGalleristId());

        if(optGallerist.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarUI.getGalleristId().toString()));

        }

        Optional<Car> optCar=carRepository.findById(dtoGalleristCarUI.getCarId());
        if(optCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarUI.getCarId().toString()));

        }

        GalleristCar galleristCar = new GalleristCar();
        galleristCar.setCreateTime(new Date());
        galleristCar.setGallerist(optGallerist.get());
        galleristCar.setCar(optCar.get());
        return galleristCar;
    }


    @Override
    public DtoGalleristCar saveGalleristCar(DtoGalleristCarUI dtoGalleristCarUI) {
        DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
        DtoGallerist   dtoGallerist = new DtoGallerist();
        DtoCar dtoCar =new DtoCar();

        DtoAddress dtoAddress =new DtoAddress();

        GalleristCar savedGalleristCar=galleristCarRepository.save(createGalleristCar(dtoGalleristCarUI));

        BeanUtils.copyProperties(savedGalleristCar,dtoGalleristCar);
        BeanUtils.copyProperties(savedGalleristCar.getGallerist(),dtoGallerist);
        BeanUtils.copyProperties(savedGalleristCar.getGallerist().getAddress(),dtoAddress);

        BeanUtils.copyProperties(savedGalleristCar.getCar(),dtoCar);

        dtoGallerist.setAddress(dtoAddress);
        dtoGalleristCar.setGallerist(dtoGallerist);
        dtoGalleristCar.setCar(dtoCar);

        return dtoGalleristCar;
    }

    @Override
    public List<DtoGalleristCar> getGalleristCar() {
        List<DtoGalleristCar> dtoGalleristCars= new ArrayList<>();
        List<GalleristCar> galleristCars=galleristCarRepository.findAll();
        for(GalleristCar galleristCar:galleristCars){
            DtoCar dtoCar=new DtoCar();
            DtoGallerist dtoGallerist=new DtoGallerist();
            DtoGalleristCar dtoGalleristCar=new DtoGalleristCar();
            BeanUtils.copyProperties(galleristCar,dtoGalleristCar);
            BeanUtils.copyProperties(galleristCar.getCar(),dtoCar);
            BeanUtils.copyProperties(galleristCar.getGallerist(),dtoGallerist);
            dtoGalleristCar.setCar(dtoCar);
            dtoGalleristCar.setGallerist(dtoGallerist);
            dtoGalleristCars.add(dtoGalleristCar);
        }
        return dtoGalleristCars;

    }

    @Override
    public DtoGalleristCar getGalleristCarById(Long id) {


        DtoGalleristCar dtoGalleristCar=new DtoGalleristCar();
        DtoGallerist dtoGallerist=new DtoGallerist();
        DtoCar dtoCar=new DtoCar();

       Optional<GalleristCar> optGalleristCar= galleristCarRepository.findById(id);
       if(optGalleristCar.isEmpty()){
           throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

       }

       GalleristCar galleristCar=new GalleristCar();
       galleristCar.setCreateTime(new Date());
       galleristCar.setCar(optGalleristCar.get().getCar());
       galleristCar.setGallerist(optGalleristCar.get().getGallerist());
       BeanUtils.copyProperties(galleristCar,dtoGalleristCar);
       BeanUtils.copyProperties(galleristCar.getGallerist(),dtoGallerist);
       BeanUtils.copyProperties(galleristCar.getCar(),dtoCar);

       dtoGalleristCar.setGallerist(dtoGallerist);
       dtoGalleristCar.setCar(dtoCar);

        return dtoGalleristCar;
    }
}
