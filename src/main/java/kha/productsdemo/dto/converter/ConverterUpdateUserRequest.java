package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.request.UpdateUserRequest;
import kha.productsdemo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ConverterUpdateUserRequest {
    public UpdateUserRequest convertFromUserToUpdateUserRequest(User user){
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail(user.getEmail());
        updateUserRequest.setUsername(user.getMainUserName());
        return updateUserRequest;
    }

    public User convertFromUpdateUserRequestToUser(UpdateUserRequest updateUserRequest, User user){
        user.setEmail(updateUserRequest.getEmail());
        user.setUsername(updateUserRequest.getUsername());
        return user;
    }
}
