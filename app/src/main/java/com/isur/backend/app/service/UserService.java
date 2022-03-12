package com.isur.backend.app.service;

import com.isur.backend.app.dto.UserDTO;
import com.isur.backend.app.exception.CreatedUserException;
import com.isur.backend.app.exception.InactiveUserException;
import com.isur.backend.app.exception.UserNotExistsException;
import com.isur.backend.app.model.User;
import com.isur.backend.app.repository.UserRepository;
import com.isur.backend.app.utils.Status;
import com.isur.backend.app.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    @Qualifier("userRepo")
    UserRepository repository;

    @Autowired
    private Mapper mapper;

    public CompletableFuture<String> generateToken(UserDTO userDTO){

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Optional<User> optionalUser = repository.findByDniAndPassword(userDTO.getDni(), userDTO.getPassword());

                    optionalUser.orElseThrow(UserNotExistsException::new);
                    User user = optionalUser.get();
                    if(user.getStatus().equals(Status.INACTIVE)) throw new InactiveUserException();
                    return TokenUtil.createJWT("1",null,user.getDni().toString(), user.getRol().toString(),2000L);
                }
        );

        return completableFuture;
    }

    public CompletableFuture<User> createUser(UserDTO userDTO) {

        CompletableFuture<User> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Optional<User> userOpt = repository.findByDni(userDTO.getDni());
                    if (userOpt.isPresent()) throw new CreatedUserException();
                    User userAccount = new User();
                    mapper.map(userDTO,userAccount);

                    return repository.save(userAccount);
                }
        );

        return completableFuture;
    }
}
