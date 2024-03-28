package com.cadeachave.cadeachave.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cadeachave.cadeachave.dtos.AuthenticationRecordDto;
import com.cadeachave.cadeachave.dtos.RegisterRecordDto;
import com.cadeachave.cadeachave.dtos.UpdateUserRecordDto;
import com.cadeachave.cadeachave.models.UserModel;
import com.cadeachave.cadeachave.services.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

     @PostMapping("/login")
     public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRecordDto data){
       return userService.login(data);
    }

    @PostMapping()
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRecordDto data){
       return userService.register(data);
    }

    @GetMapping()
    public ResponseEntity<Page<UserModel>> findAll(
         @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection,"login"));
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable(value = "id") String id){
        return userService.findById(id);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserModel> update(@PathVariable(value = "id") String id, @RequestBody @Valid UpdateUserRecordDto updateDto){
        return userService.update(updateDto, id);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id){
        return userService.delete(id);
    }
}