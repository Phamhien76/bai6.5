package ra.bai6_5signin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private boolean sex;
    private String phone;
    private Date birthDay;
}
