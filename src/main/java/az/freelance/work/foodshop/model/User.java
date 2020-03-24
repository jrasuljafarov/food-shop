package az.freelance.work.foodshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "phone_number"
        })
})
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 150)
    @Column(name = "full_name")
    private String fullName;

    @NaturalId
    @NotBlank
    @Size(max = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 500)
    @Column(name = "address")
    private String address;

    @NotBlank
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @Size(max = 100)
    @Column(name = "device_id")
    private String deviceId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(max = 150) String fullName,
                @NotBlank @Size(max = 12) String phoneNumber,
                @Size(max = 500) String address,
                @NotBlank @Size(max = 100) String password,
                @Size(max = 100) String deviceId) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.deviceId = deviceId;
    }
}
