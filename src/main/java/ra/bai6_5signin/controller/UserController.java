package ra.bai6_5signin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.bai6_5signin.dto.request.SignInRequest;
import ra.bai6_5signin.dto.request.SignUpRequest;
import ra.bai6_5signin.dto.response.SignInResponse;
import ra.bai6_5signin.dto.response.SignUpResponse;
import ra.bai6_5signin.dto.response.UserResponse;
import ra.bai6_5signin.exception.CustomException;
import ra.bai6_5signin.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("public/user")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest) throws CustomException {
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    @PostMapping("public/user/login")
    public ResponseEntity<SignInResponse> login (@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(userService.login(signInRequest),HttpStatus.OK);
    }

    @GetMapping("/admin/user")
    public ResponseEntity<List<UserResponse>> findAll(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
}
