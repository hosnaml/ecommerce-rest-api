package com.hosnaml.store.controllers;

import com.hosnaml.store.dtos.ChangePasswordRequest;
import com.hosnaml.store.dtos.UpdateUserRequest;
import com.hosnaml.store.dtos.UserDto;
import com.hosnaml.store.dtos.RegisterUserRequest;
import com.hosnaml.store.mappers.UserMapper;
import com.hosnaml.store.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public Iterable<UserDto> getAllUsers(
            @Valid @RequestHeader(name = "x-auth-token", required = false) String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort
    ) {
        // Service already validates allowed sort fields, but we mimic earlier guard to keep behavior stable
        if(!Set.of("name", "email").contains(sort)){
            sort = "name";
        }
        return userService.listUsers(sort).stream().map(userMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        var user = userService.createUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, @Valid @RequestBody UpdateUserRequest request){
        var user = userService.updateUser(id, request);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable(name = "id") Long id){
        boolean deleted = userService.deleteUser(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<UserDto> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request){
        try {
            var user = userService.changePassword(id, request);
            if(user == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userMapper.toDto(user));
        } catch (SecurityException se){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
