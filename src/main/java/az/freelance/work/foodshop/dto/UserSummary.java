package az.freelance.work.foodshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSummary {

    private String fullName;
    private String phoneNumber;
    private String address;
    private String deviceId;

}