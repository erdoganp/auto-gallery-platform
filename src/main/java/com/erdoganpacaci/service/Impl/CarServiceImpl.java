package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.DtoCar;
import com.erdoganpacaci.dto.DtoCarUI;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Car;
import com.erdoganpacaci.model.GalleristCar;
import com.erdoganpacaci.repository.CarRepository;
import com.erdoganpacaci.repository.GalleristCarRepository;
import com.erdoganpacaci.service.CarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {


    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GalleristCarRepository galleristCarRepository;

    private Car createCar(DtoCarUI dtoCarUI) {

        Car car = new Car();
        car.setCreateTime(new Date());

        BeanUtils.copyProperties(dtoCarUI, car);

        return car;

    }

    @Override
    public DtoCar saveCar(DtoCarUI dtoCarUI) {

        DtoCar dtoCar = new DtoCar();
       Car savedCar= carRepository.save(createCar(dtoCarUI));

       BeanUtils.copyProperties(savedCar, dtoCar);
        return dtoCar;
    }

    @Override
    public List<DtoCar> getCars() {

        List<Car> cars= carRepository.findAll();

        List<DtoCar> dtoCars= new ArrayList<>();

        for (Car car : cars) {
            DtoCar dtoCar = new DtoCar();
            BeanUtils.copyProperties(car,dtoCar);
            dtoCars.add(dtoCar);
        }

        return dtoCars;

    }

    @Override
    public DtoCar updateCar(Long id, DtoCarUI dtoCarUI) {

        DtoCar dtoCar = new DtoCar();
        Optional<Car> optCar=carRepository.findById(id);
        Car dbCar=null;
        if(optCar.isEmpty()){
           throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

        }

        dbCar=optCar.get();
        BeanUtils.copyProperties(dtoCarUI,dbCar);
       Car savedCar= carRepository.save(dbCar);

        BeanUtils.copyProperties(savedCar,dtoCar);

        return dtoCar;
    }

    @Override
    public Void deleteCar(Long id) {

        Optional<Car> optionalCar=carRepository.findById(id);

        if(optionalCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

        }


        Optional<GalleristCar> optionalGalleristCar=galleristCarRepository.findWithCars(id);

        if(!optionalGalleristCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.CAR_IS_ON_SALE, id.toString()));

        }


        Car dbCar=optionalCar.get();
        carRepository.delete(dbCar);

        return null;

    }


}
