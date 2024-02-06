package ra.bai6_5signin.service;

import ra.bai6_5signin.dto.request.SignInRequest;
import ra.bai6_5signin.dto.request.SignUpRequest;
import ra.bai6_5signin.dto.response.SignInResponse;
import ra.bai6_5signin.dto.response.SignUpResponse;
import ra.bai6_5signin.dto.response.UserResponse;
import ra.bai6_5signin.exception.CustomException;
import ra.bai6_5signin.model.User;

import java.util.List;

public interface UserService {
SignUpResponse register(SignUpRequest signUpRequest) throws CustomException;
SignInResponse login (SignInRequest signInRequest);
List<UserResponse> findAll();

}
