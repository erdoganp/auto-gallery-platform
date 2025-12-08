package com.erdoganpacaci.service.Impl;


import com.erdoganpacaci.dto.AuthRequest;
import com.erdoganpacaci.dto.AuthResponse;
import com.erdoganpacaci.dto.DtoUser;
import com.erdoganpacaci.dto.RefreshTokenRequest;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.jwt.JWTService;
import com.erdoganpacaci.model.RefreshToken;
import com.erdoganpacaci.model.User;
import com.erdoganpacaci.repository.RefreshTokenRepository;
import com.erdoganpacaci.repository.UserRepository;
import com.erdoganpacaci.service.AuthenticationService;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;



    private User createUser(AuthRequest input){

        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(input.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));

        return user;

    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 *60 *4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        return refreshToken;

    }

    @Override
    public DtoUser register(AuthRequest input) {
        DtoUser dtoUser = new DtoUser();

       User savedUser=userRepository.save(createUser(input));

        BeanUtils.copyProperties(savedUser,dtoUser);

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {


       try{
           UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword());

          Authentication authentication= authenticationProvider.authenticate(authenticationToken);


           if(!authentication.isAuthenticated()){
               System.out.println("NOOOOOO");
           }

           Optional<User> optUser= userRepository.findByUsername(input.getUsername());

        String accessToken=jwtService.generateToken(optUser.get());

       RefreshToken savedRefreshToken= refreshTokenRepository.save(createRefreshToken(optUser.get()));


       return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());
       }catch (Exception e){

           throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));



       }
    }

    public boolean isValidRefrshToken(Date expiredDate){
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {

       Optional<RefreshToken> optRefreshToken= refreshTokenRepository.findByRefreshToken(input.getRefreshToken());

       if(optRefreshToken.isEmpty()){
           throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, input.getRefreshToken()));

       }
       if(!isValidRefrshToken(optRefreshToken.get().getExpiredDate())){

           throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, input.getRefreshToken()));


       }

       User user=optRefreshToken.get().getUser();

       String accessToken=jwtService.generateToken(user);
      RefreshToken savedRefreshToken= refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());
    }
}
