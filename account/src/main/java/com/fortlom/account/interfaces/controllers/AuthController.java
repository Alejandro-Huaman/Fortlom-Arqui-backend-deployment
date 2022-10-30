package com.fortlom.account.interfaces.controllers;
import com.fortlom.account.application.exception.Message;
import com.fortlom.account.application.service.AuthService;
import com.fortlom.account.interfaces.dto.authetication.*;
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

    @ApiOperation(value="registerfanatic",notes = "Esta consulta nos ayuda a registrar a un fanatico")
    @PostMapping("/fanatic")
    public ResponseEntity<?> registerfanatic(@Valid @RequestBody NewFanatic request, BindingResult bindingResult) throws Message {

        return  authService.registerfanatic(request,bindingResult);


    }
    @ApiOperation(value="registerartist",notes = "Esta consulta nos ayuda a registrar a un artista")
    @PostMapping("/artist")
    public ResponseEntity<?> registerartist(@Valid @RequestBody NewArtist request, BindingResult bindingResult) throws Message {

        return  authService.registerartist(request,bindingResult);


    }
    @PostMapping("/admin")
    public ResponseEntity<?> registeradmin(@Valid @RequestBody RegisterUser request, BindingResult bindingResult) throws Message {

        return  authService.registeradmin(request,bindingResult);


    }
    @ApiOperation(value="login",notes = "Esta consulta nos ayuda a logear a un usuario ya registrado, en el caso de que se utilice un usuario no registrado saldria error")
    @PostMapping("/login")
    public ResponseEntity<jwtDto>login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){

        return authService.login(loginUser,bindingResult);
    }
}
