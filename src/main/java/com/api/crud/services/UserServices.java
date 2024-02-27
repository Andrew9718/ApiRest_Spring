package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class UserServices {

    @Autowired
    IUserRepository userRepository;

    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }


    public Optional<UserModel>getById(long id){
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel nuevos, long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        user.setFirsName(nuevos.getFirsName());
        user.setLastName(nuevos.getLastName());
        user.setEmail(nuevos.getEmail());
        user.setFoto(nuevos.getFoto());

        return userRepository.save(user);
    }

    public Boolean deleteUser(Long id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
