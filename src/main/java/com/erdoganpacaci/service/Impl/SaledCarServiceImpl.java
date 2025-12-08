package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.*;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Car;
import com.erdoganpacaci.model.CarStatusType;
import com.erdoganpacaci.model.Customer;
import com.erdoganpacaci.model.SaledCar;
import com.erdoganpacaci.repository.CarRepository;
import com.erdoganpacaci.repository.CustomerRepository;
import com.erdoganpacaci.repository.GalleristRepository;
import com.erdoganpacaci.repository.SaledCarRepository;
import com.erdoganpacaci.service.SaledCarService;
import com.erdoganpacaci.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

@Service
public class SaledCarServiceImpl implements SaledCarService {


    @Autowired
    private SaledCarRepository saledCarRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GalleristRepository galleristRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CurrencyRatesServiceImpl currencyRatesService;





    public BigDecimal convertCustomerAmountToUSD(Customer customer){

        CurrencyRatesResponse currencyRatesResponse= currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()),DateUtils.getCurrentDate(new Date()));
         BigDecimal usd=new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

        BigDecimal  customerUSDAmount=customer.getAccount().getAmount().divide(usd, 2, RoundingMode.HALF_UP);


        return customerUSDAmount;
    }

    public BigDecimal convertCarAmountToUSD(Car car){
        CurrencyRatesResponse currencyRatesResponse= currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()),DateUtils.getCurrentDate(new Date()));
        BigDecimal usd=new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

        BigDecimal  CarUsdAmount=car.getPrice().divide(usd, 2, RoundingMode.HALF_UP);
        return CarUsdAmount;
    }


    public BigDecimal remainingCustomerAmount(Customer customer, Car car){

        BigDecimal customerAmountbaseUSD;
        BigDecimal carPricebaseUSD;

        boolean customerCurrencyUSD=checkCustomerCurrencyUSD(customer.getId());




        if(!checkCustomerCurrencyUSD(customer.getId())){
            customerAmountbaseUSD=convertCustomerAmountToUSD(customer);
        }

        else {
            customerAmountbaseUSD=customer.getAccount().getAmount();
        }

        if(!checkCarCurrencyUSD(car.getId())){

            carPricebaseUSD=convertCarAmountToUSD(car);

        }
        else {
            carPricebaseUSD=car.getPrice();
        }



        BigDecimal remainingCustomerUSDAmount=customerAmountbaseUSD.subtract(carPricebaseUSD);

        if(!customerCurrencyUSD){
            CurrencyRatesResponse currencyRatesResponse=  currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()),DateUtils.getCurrentDate(new Date()));
            BigDecimal usd=new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
            return remainingCustomerUSDAmount.multiply(usd);

        }

        return remainingCustomerUSDAmount;




    }



    public boolean checkAmount(DtoSaledCarUI dtoSaledCarUI){



        Optional<Customer>optCustomer=customerRepository.findById(dtoSaledCarUI.getCustomerId());
        if(optCustomer.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoSaledCarUI.getCustomerId().toString()));

         }

        Optional<Car> optCar =carRepository.findById(dtoSaledCarUI.getCarId());
        if (optCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoSaledCarUI.getCarId().toString()));

        }


        BigDecimal customerAmountbaseUSD;
        BigDecimal carPricebaseUSD;

        if(!checkCustomerCurrencyUSD(dtoSaledCarUI.getCustomerId())){
            customerAmountbaseUSD=convertCustomerAmountToUSD(optCustomer.get());
        }

        else {
            customerAmountbaseUSD=optCustomer.get().getAccount().getAmount();
        }

        if(!checkCarCurrencyUSD(dtoSaledCarUI.getCarId())){

            carPricebaseUSD=convertCarAmountToUSD(optCar.get());

        }
        else {
            carPricebaseUSD=optCar.get().getPrice();
        }




        if(customerAmountbaseUSD.compareTo(carPricebaseUSD)==0 || customerAmountbaseUSD.compareTo(carPricebaseUSD)> 0){
            return true;
        }
        return false;

    }


    private SaledCar createSaledCar(DtoSaledCarUI dtoSaledCarUI){
        SaledCar saledCar =new SaledCar();
        saledCar.setCreateTime(new Date());

        saledCar.setCustomer(customerRepository.findById(dtoSaledCarUI.getCustomerId()).orElse(null));
        saledCar.setGallerist(galleristRepository.findById(dtoSaledCarUI.getGalleristId()).orElse(null));
        saledCar.setCar(carRepository.findById(dtoSaledCarUI.getCarId()).orElse(null));

        return saledCar;

    }

    public boolean checkCarStatus(Long carId){
       Optional<Car> optCar= carRepository.findById(carId);
       if(optCar.isPresent() && optCar.get().getCarStatusType().name().equals(CarStatusType.SALED.name())){
           return false;

       }
       return true;

    }


    public boolean checkCustomerCurrencyUSD(Long customerId){
        Optional<Customer> optCustomer=customerRepository.findById(customerId);

        String currencyType=optCustomer.get().getAccount().getCurrencyType().name();
        if(currencyType.equals("USD")){
            return true;
        }
        return false;
    }

    public boolean checkCarCurrencyUSD(Long carId){
        Optional<Car> optCar=carRepository.findById(carId);
        String currencyType=optCar.get().getCurrencyType().name();
        if(currencyType.equals("USD")){
            return true;
        }
        return false;
    }

    @Override
    public DtoSaledCar buyCar(DtoSaledCarUI dtoSaledCarUI) {

        if (!(checkCarStatus((dtoSaledCarUI.getCarId())))){
            throw new BaseException(new ErrorMessage(MessageType.CAR_STATUS_IS_ALREADY_SALED, dtoSaledCarUI.getCarId().toString()));

        }

        if(!checkAmount(dtoSaledCarUI)){
            throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH, ""));

        }



        SaledCar savedSaledCar=saledCarRepository.save(createSaledCar(dtoSaledCarUI));
        Car car =savedSaledCar.getCar();
        car.setCarStatusType(CarStatusType.SALED);
        carRepository.save(car);

        Customer customer=savedSaledCar.getCustomer();
        customer.getAccount().setAmount(remainingCustomerAmount(customer,car));
        customerRepository.save(customer);

        return toDTO(savedSaledCar);
    }

    public DtoSaledCar toDTO(SaledCar saledCar){
        DtoSaledCar dtoSaledCar=new DtoSaledCar();
        DtoCustomer dtoCustomer=new DtoCustomer();
        DtoGallerist dtoGallerist= new DtoGallerist();
        DtoAddress dtoAddress=new DtoAddress();
        DtoAddress dtoAddress2=new DtoAddress();
        DtoAccount dtoAccount=new DtoAccount();

        DtoCar dtoCar=new DtoCar();

        BeanUtils.copyProperties(saledCar, dtoSaledCar);
        BeanUtils.copyProperties(saledCar.getCustomer(),dtoCustomer);
        BeanUtils.copyProperties(saledCar.getCustomer().getAddress(), dtoAddress);
        BeanUtils.copyProperties(saledCar.getCustomer().getAccount(), dtoAccount);
        BeanUtils.copyProperties(saledCar.getGallerist(),dtoGallerist);
        BeanUtils.copyProperties(saledCar.getGallerist().getAddress(),dtoAddress2);
        BeanUtils.copyProperties(saledCar.getCar(),dtoCar);

        dtoCustomer.setAddress(dtoAddress);
        dtoCustomer.setAccount(dtoAccount);
        dtoGallerist.setAddress(dtoAddress2);

        dtoSaledCar.setCustomer(dtoCustomer);
        dtoSaledCar.setGallerist(dtoGallerist);
        dtoSaledCar.setCar(dtoCar);

        return dtoSaledCar;

    }

}
