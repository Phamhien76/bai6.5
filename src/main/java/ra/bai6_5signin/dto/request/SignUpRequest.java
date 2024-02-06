package ra.bai6_5signin.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignUpRequest {
    @NotNull(message = "Tên danh mục không được null")
    private String userName;
    @Length(min = 6,message = "Password từ 6 ký tự trở lên")
    @Pattern(regexp = "(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,20}", message = "Password cần có ký tự số, chữ thường, chữ hoa và 1 trong các ký tự @#$%!")
    private String password;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message = "Email không đúng định dạng")
    private String email;
    private String fullName;
    private boolean sex;
    @Pattern(regexp = "^(070|080|090)-\\d{4}-\\d{4}$",message = "Không đúng định dạng số điện thoại")
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDay;
    private List<String> listRoles;

}
