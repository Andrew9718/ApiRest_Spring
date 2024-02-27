package com.api.crud.controllers;


import com.api.crud.models.UserModel;
import com.api.crud.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ArrayList<UserModel> getUsers(){
        return this.userServices.getUsers();
    }

    @PostMapping
    public UserModel saveUser(@RequestParam("foto") MultipartFile foto, @RequestParam("firstName") String firstname,@RequestParam("lastName") String lastname, @RequestParam("email") String email ) {
        UserModel user = new UserModel();
        user.setFirsName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);

        if (foto != null && !foto.isEmpty()) {
            try {
                user.setFoto(foto.getBytes()); // Guarda la imagen como un arreglo de bytes en el modelo
            } catch (IOException e) {
                throw new RuntimeException("Error al leer la imagen");
            }
        }


        return userServices.saveUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable long id){
        return this.userServices.getById(id);
    }

    @PutMapping(path = "/{id}")
    public UserModel updateUserById(@RequestParam(name = "foto", required = false) MultipartFile foto,  @RequestParam("firstName") String firstname,@RequestParam("lastName") String lastname, @RequestParam("email") String email , @PathVariable("id") long id) {
        UserModel nuevo = new UserModel();
        nuevo.setFirsName(firstname);
        nuevo.setLastName(lastname);
        nuevo.setEmail(email);

        if (foto != null && !foto.isEmpty()) {
            try {
                nuevo.setFoto(foto.getBytes()); // Guarda la imagen como un arreglo de bytes en el modelo
            } catch (IOException e) {
                throw new RuntimeException("Error al leer la imagen");
            }
        }
        return userServices.updateById(nuevo, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userServices.deleteUser(id);

        if (ok){
            return "Eliminado Correctamente"+ id;
        }else{
            return "No ha sido Eliminado";
        }
    }


}
