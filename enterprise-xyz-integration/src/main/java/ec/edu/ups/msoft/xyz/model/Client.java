package ec.edu.ups.msoft.xyz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Client {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String channel;
}
