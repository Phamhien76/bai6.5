package ra.bai6_5signin.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.bai6_5signin.dto.request.SignInRequest;
import ra.bai6_5signin.dto.request.SignUpRequest;
import ra.bai6_5signin.dto.response.SignInResponse;
import ra.bai6_5signin.dto.response.SignUpResponse;
import ra.bai6_5signin.dto.response.UserResponse;
import ra.bai6_5signin.exception.CustomException;
import ra.bai6_5signin.model.ERoles;
import ra.bai6_5signin.model.Roles;
import ra.bai6_5signin.model.User;
import ra.bai6_5signin.repository.RoleRepository;
import ra.bai6_5signin.repository.UserRepository;
import ra.bai6_5signin.security.jwt.JwtProvider;
import ra.bai6_5signin.security.principle.CustomUserDetail;
import ra.bai6_5signin.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) throws CustomException {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role -> {
            switch (role){
                case "admin":
                    setRoles.add(roleRepository.findByName(ERoles.ROLE_ADMIN)
                            .orElseThrow(()-> new RuntimeException("Không tồn tại quền Admin")));
                    break;
                case "moderator":
                    setRoles.add(roleRepository.findByName(ERoles.ROLE_MODERATOR)
                            .orElseThrow(()->new RuntimeException("Không tồn tại quền Admin")));
                case "user":
                    setRoles.add(roleRepository.findByName(ERoles.ROLE_USER)
                            .orElseThrow(()->new RuntimeException("Không tồn tại quền Admin")));
            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRoles(setRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        if (userRepository.existsByUserName(signUpRequest.getUserName())){
            throw new CustomException ("UserName đã tồn tại vui lòng nhập lại");
        }
        User userCreated = userRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);
        List<String> listUserRoles = new ArrayList<>();
        userCreated.getListRoles().stream().forEach(roles -> {
            listUserRoles.add(roles.getName().name());
        });
        signUpResponse.setListRoles(listUserRoles);
        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        Authentication authentication = null;
        try {
            authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()));
        }catch (Exception e){
            throw new RuntimeException("UserName or Password incorrect");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetail);
        String refreshToken = jwtProvider.generateRefreshToken(userDetail);
        return new SignInResponse(
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getEmail(),
                userDetail.getFullName(),
                userDetail.getAuthorities(),
                accessToken,refreshToken);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> listUser = userRepository.findAll();
        List<UserResponse> listUserResponse = listUser.stream()
                .map(users -> modelMapper.map(users,UserResponse.class)).collect(Collectors.toList());
        return listUserResponse;
    }
}
