package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.response.ShowUserResponse;
import kha.productsdemo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ConverterShowUserResponse {
    public ShowUserResponse convertFromUserToShowUserResponse(User user){
        ShowUserResponse showUserResponse = new ShowUserResponse();
        showUserResponse.setId(user.getId());
        showUserResponse.setUsername(user.getUsername());
        showUserResponse.setEmail(user.getEmail());
        showUserResponse.setRoles(user.getRoles());
        return showUserResponse;
    }
}
