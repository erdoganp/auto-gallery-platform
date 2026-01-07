package com.erdoganpacaci.controller.impl;

import com.erdoganpacaci.controller.RestBaseController;
import com.erdoganpacaci.controller.RestCarController;
import com.erdoganpacaci.controller.RootEntity;
import com.erdoganpacaci.dto.DtoCar;
import com.erdoganpacaci.dto.DtoCarUI;
import com.erdoganpacaci.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/car")
public class RestCarControllerImpl extends RestBaseController implements RestCarController {


    @Autowired
    private CarService carService;

    @PostMapping("/save")
    @Override
    public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarUI dtoCarUI) {
        return ok(carService.saveCar(dtoCarUI));
    }

    @GetMapping("/list")
    @Override
    public RootEntity<List<DtoCar>> getCars() {
        return ok(carService.getCars());
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoCar> updateCar(@Valid @PathVariable(value = "id")Long id,
                                        @RequestBody DtoCarUI dtoCarUI) {
        return ok(carService.updateCar(id,dtoCarUI));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<Void> deleteCar(@PathVariable (value = "id") Long id) {

        return ok(carService.deleteCar(id)) ;
    }


}
