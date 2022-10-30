package com.fortlom.administration.interfaces.controllers;
import com.fortlom.administration.application.exception.Message;
import com.fortlom.administration.application.service.AuthService;
import com.fortlom.administration.domain.adminAgreegate.entity.Admin;
import com.fortlom.administration.interfaces.dto.authetication.LoginUser;
import com.fortlom.administration.interfaces.dto.authetication.RegisterUser;
import com.fortlom.administration.interfaces.dto.authetication.jwtDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @ApiOperation(value="register",notes = "Esta consulta nos ayuda a registrar")
    @PostMapping("/register")
    public Admin registerfanatic(@Valid @RequestBody RegisterUser request, BindingResult bindingResult) throws Message {

        return  authService.register(request,bindingResult);


    }
    @ApiOperation(value="login",notes = "Esta consulta nos ayuda a logear a un usuario ya registrado, en el caso de que se utilice un usuario no registrado saldria error")
    @PostMapping("/login")
    public ResponseEntity<jwtDto>login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){

        return authService.login(loginUser,bindingResult);
    }
}
