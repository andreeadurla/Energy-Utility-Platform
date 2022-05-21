package ds.assignment.rpc.views.authentication;

import ds.assignment.rpc.dtos.UserDTO;
import ds.assignment.rpc.exceptions.ResourceNotFoundException;
import ds.assignment.rpc.services.AuthenticateService;
import ds.assignment.rpc.validators.Validator;
import ds.assignment.rpc.views.home.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {

    private AuthenticationView view;

    @Autowired
    private AuthenticateService auth;

    @Autowired
    private HomeController homeController;

    public void initView() {
        this.view = new AuthenticationView();

        addListeners();
    }

    private void addListeners() {
        view.addSubmitButtonListener(e -> validateAccount());
    }

    private void validateAccount() {
        String username = view.getUsername();
        String password = view.getPassword();

        try {
            Validator.isEmptyOrNull(username);
            Validator.isEmptyOrNull(password);

            UserDTO user = auth.authenticate(username, password);
            for(String role : user.getRoles()) {
                if(role.equals("CLIENT")) {
                    homeController.initView(user);
                    view.dispose();
                }
            }

            throw new ResourceNotFoundException("Wrong username or password");

        } catch(RuntimeException e) {
            view.setErrorArea(e.getMessage());
            view.setVisibleErrorArea(true);
        }
    }
}
