package com.isur.backend.app.service;

import com.github.dozermapper.core.Mapper;
import com.isur.backend.app.dto.UserAuthenticationDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    @Qualifier("userRepo")
    UserRepository repository;

    @Autowired
    private Mapper mapper;

    public CompletableFuture<UserAuthenticationDTO> generateToken(UserDTO userDTO){

        CompletableFuture<UserAuthenticationDTO> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Optional<User> optionalUser = repository.findByDniAndPassword(userDTO.getDni(), userDTO.getPassword());

                    optionalUser.orElseThrow(UserNotExistsException::new);
                    User user = optionalUser.get();
                    if(user.getStatus().equals(Status.INACTIVE)) throw new InactiveUserException();

                    String token =TokenUtil.createJWT("1",null,user.getDni().toString(), user.getRol().toString(),60000L);
                    return new UserAuthenticationDTO(user.getFullName(),user.getRol(), "Bearer" ,token);

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

    public CompletableFuture<Page<User>> findAll() {
        CompletableFuture<Page<User>> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Page<User> page = repository.findAll(Pageable.ofSize(50));
                    return page;
                });

        return  completableFuture;

    }


}
