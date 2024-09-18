package kha.productsdemo.dto.converter;

import kha.productsdemo.dto.response.ShowUserAccount;
import kha.productsdemo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ConverterShowUserAccount {
    public ShowUserAccount convertFromUserToShowUserAccount(User user){

        ShowUserAccount showUserAccount = new ShowUserAccount();
        showUserAccount.setId(user.getId());
        showUserAccount.setUsername(user.getMainUserName());
        showUserAccount.setEmail(user.getEmail());
        showUserAccount.setRoles(user.getRoles());
        return showUserAccount;
    }
}
