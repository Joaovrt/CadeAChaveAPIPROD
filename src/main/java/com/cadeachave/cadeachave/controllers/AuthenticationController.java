package com.cadeachave.cadeachave.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cadeachave.cadeachave.configuration.security.TokenService;
import com.cadeachave.cadeachave.dtos.AuthenticationDto;
import com.cadeachave.cadeachave.dtos.LoginResponseDto;
import com.cadeachave.cadeachave.dtos.RegisterDto;
import com.cadeachave.cadeachave.exceptions.ResourceBadRequestException;
import com.cadeachave.cadeachave.exceptions.ResourceNotFoundException;
import com.cadeachave.cadeachave.models.ProfessorModel;
import com.cadeachave.cadeachave.models.UserModel;
import com.cadeachave.cadeachave.repositories.ProfessorRepository;
import com.cadeachave.cadeachave.repositories.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @Autowired ProfessorRepository professorRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) throw new ResourceBadRequestException("Usuario ja cadastrado.");
        ProfessorModel professor = null;
        if(data.professor_id()!=null){
            professor = professorRepository.findById(data.professor_id()).orElseThrow(()-> new ResourceNotFoundException("Nenhum professor encontrado com esse id: "+data.professor_id()));
            if(this.repository.findByProfessor(professor) != null) throw new ResourceBadRequestException("Professor ja cadastrado como usuario.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role(), professor);

        UserModel userCreated =  this.repository.save(newUser);
        userCreated.setPassword(null);
        return ResponseEntity.ok().body(userCreated);
    }
}
