package az.freelance.work.foodshop.service;

import az.freelance.work.foodshop.dto.UserSummary;
import az.freelance.work.foodshop.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .phoneNumber(userPrincipal.getPhoneNumber())
                .deviceId(userPrincipal.getDeviceId())
                .address(userPrincipal.getAddress())
                .fullName(userPrincipal.getFullName())
                .build();
    }

}
