package az.freelance.work.foodshop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank
    @Size(min = 2,max = 150)
    private String fullName;

    @NotBlank
    @Size(max = 12)
    private String phoneNumber;

    @Size(max = 500)
    private String address;

    @Size(min = 4,max = 50)
    private String password;

    @Size(max = 100)
    private String deviceId;

}
