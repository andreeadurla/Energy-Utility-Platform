package ds.assignment.rpc.views.home;

import ds.assignment.rpc.dtos.UserDTO;
import ds.assignment.rpc.views.authentication.AuthenticationController;
import ds.assignment.rpc.views.historical_energy_consumption.HistoricalEnergyConsumptionController;
import ds.assignment.rpc.views.program_selection.ProgramSelectionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    private HomeView homeView;
    private UserDTO user;

    @Autowired
    private AuthenticationController authController;

    @Autowired
    private HistoricalEnergyConsumptionController historicalEnergyConsumptionController;

    @Autowired
    private ProgramSelectionController programSelectionController;

    public void initView(UserDTO user) {
        this.user = user;
        this.homeView = new HomeView(user.getUsername());
        createPanels();
        addListeners();
    }

    private void createPanels() {
        homeView.addPanel(
                "Historical Energy Consumption",
                historicalEnergyConsumptionController.getHistoricalEnergyConsumptionView(user)
        );

        homeView.addPanel(
                "Program Selection",
                programSelectionController.getProgramSelectionView(user)
        );
    }

    private void addListeners() {
        homeView.addLogoutButtonListener(e -> logout());
    }

    private void logout() {
        authController.initView();
        homeView.dispose();
    }
}
