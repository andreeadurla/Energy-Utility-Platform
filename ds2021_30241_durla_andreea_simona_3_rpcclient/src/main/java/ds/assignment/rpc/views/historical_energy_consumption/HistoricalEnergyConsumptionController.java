package ds.assignment.rpc.views.historical_energy_consumption;

import ds.assignment.rpc.dtos.BaselineDTO;
import ds.assignment.rpc.dtos.EnergyConsumptionDTO;
import ds.assignment.rpc.dtos.UserDTO;
import ds.assignment.rpc.services.interfaces.IEnergyConsumptionServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Controller
public class HistoricalEnergyConsumptionController {

    private HistoricalEnergyConsumptionView view;
    private UserDTO user;

    @Autowired
    private IEnergyConsumptionServiceRpc energyConsumptionService;

    public HistoricalEnergyConsumptionView getHistoricalEnergyConsumptionView(UserDTO user) {
        this.user = user;
        initView();
        return view;
    }

    private void initView() {
        view = new HistoricalEnergyConsumptionView();

        addListeners();
        createHistoricalConsumptionChart();
        createAveragedConsumptionChart();
    }

    private void addListeners() {
        view.addComputeButtonListener(e -> computeEnergyConsumption());
    }

    private void createHistoricalConsumptionChart() {
        view.createHistoricalConsumptionChart(
                getHistoricalEnergyConsumption(
                        user.getClientId(),
                        7
                )
        );
    }

    private void createAveragedConsumptionChart() {
        BaselineDTO baselineDTO = getAveragedEnergyConsumption(
                user.getClientId(),
                7
        );
        view.setDateInterval(baselineDTO.getStartDate(), baselineDTO.getEndDate());
        view.createAveragedConsumptionChart(baselineDTO);
    }

    private void computeEnergyConsumption() {
        int days = view.getDays();

        if(days <= 0) {
            view.setErrorArea("Number of days must be a positive number");
            view.setVisibleErrorArea(true);
            return ;
        }

        view.setVisibleErrorArea(false);

        view.updateHistoricalConsumptionDataset(getHistoricalEnergyConsumption(
                user.getClientId(),
                days
        ));

        BaselineDTO baselineDTO = getAveragedEnergyConsumption(user.getClientId(), days);
        view.setDateInterval(baselineDTO.getStartDate(), baselineDTO.getEndDate());
        view.updateAveragedConsumptionDataset(baselineDTO);
    }

    private List<EnergyConsumptionDTO> getHistoricalEnergyConsumption(UUID clientId, int days) {
        List<EnergyConsumptionDTO> energyConsumption = energyConsumptionService.getHistoricalEnergyConsumption(
                clientId,
                days,
                TimeZone.getDefault()
        );

        return energyConsumption;
    }

    private BaselineDTO getAveragedEnergyConsumption(UUID clientId, int days) {
        BaselineDTO baselineDTO = energyConsumptionService.getAveragedEnergyConsumption(
                clientId,
                days,
                TimeZone.getDefault()
        );

        return baselineDTO;
    }
}
