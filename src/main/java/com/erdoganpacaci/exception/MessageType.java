package com.erdoganpacaci.exception;

import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1004", "kayıt bulunamadı",HttpStatus.SC_NOT_FOUND),
    TOKEN_IS_EXPIRED("1005", "Tokenın süresi bitmistir", HttpStatus.SC_UNAUTHORIZED),
    GENERAL_EXCEPTION("9999", "genel bir hata olustu", HttpStatus.SC_INTERNAL_SERVER_ERROR),
    USERNAME_NOT_FOUND("1006", "usename bulunmadı",  HttpStatus.SC_NOT_FOUND),
    REFRESH_TOKEN_NOT_FOUND("1008", "refresh token bulunamadı", HttpStatus.SC_NOT_FOUND),
    REFRESH_TOKEN_IS_EXPIRED("1009", "Refresh tokenin süresi bitmistir",  HttpStatus.SC_UNAUTHORIZED),
    CURRENCY_RATES_IS_OCCURED("1010", "DOVİZ KURU ALINAMADI",  HttpStatus.SC_BAD_REQUEST),
    CUSTOMER_AMOUNT_IS_NOT_ENOUGH("1011", "musterinin parası yeterli değildir",   HttpStatus.SC_BAD_REQUEST),
    CAR_STATUS_IS_ALREADY_SALED("1012" ,"araba satılmıs göründügü için satılamaz",    HttpStatus.SC_BAD_REQUEST),
    USERNAME_OR_PASSWORD_INVALID("1007", "kullanıcı adı veya sifre hatalı",  HttpStatus.SC_UNAUTHORIZED),
    CAR_IS_ON_SALE("1013","Araba bir galeride satısta", HttpStatus.SC_BAD_REQUEST),
    GALLERIST_IS_ACTIVE("1014","Galerici suan aktif olarak satıs yapıyor", HttpStatus.SC_BAD_REQUEST),
    DATA_IS_ALREADY_USED("1015","Data suan kullanılıyor", HttpStatus.SC_CONFLICT),;

    private String code;

    private String message;

    private int status;

     MessageType (String code, String message, int status){

        this.code = code;
        this.message = message;
        this.status = status;
    }
}
