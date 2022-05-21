package ds.assignment.rpc.views.program_selection;

import ds.assignment.rpc.dtos.DeviceDTO;
import ds.assignment.rpc.dtos.ProgramSelectionDTO;
import ds.assignment.rpc.dtos.UserDTO;
import ds.assignment.rpc.services.interfaces.IDeviceServiceRpc;
import ds.assignment.rpc.services.interfaces.IEnergyConsumptionServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Controller
public class ProgramSelectionController {

    private ProgramSelectionView view;
    private UserDTO user;

    @Autowired
    private IDeviceServiceRpc deviceService;

    @Autowired
    private IEnergyConsumptionServiceRpc energyConsumptionService;

    public ProgramSelectionView getProgramSelectionView(UserDTO user) {
        this.user = user;
        initView();
        return view;
    }

    private void initView() {
        view = new ProgramSelectionView();

        addListeners();
        insertDevicesInComboBox();
    }

    private void addListeners() {
        view.addComputeButtonListener(e -> computeEnergyConsumption());
    }

    private void insertDevicesInComboBox() {
        List<DeviceDTO> deviceDTOs = deviceService.findDevicesByClientId(user.getClientId());
        if(deviceDTOs != null && !deviceDTOs.isEmpty())
            view.insertDevicesInComboBox(deviceDTOs);
    }

    private void computeEnergyConsumption() {
        DeviceDTO device = view.getSelectedDevice();
        int duration = view.getProgramDuration();
        int days = view.getDays();

        if(days <= 0) {
            view.setErrorArea1("Number of days must be a positive number");
            view.setVisibleErrorArea1(true);
            return ;
        }

        view.setVisibleErrorArea1(false);

        if(duration <= 0 || duration > 24) {
            view.setErrorArea2("Program duration must be between 1 and 24");
            view.setVisibleErrorArea2(true);
            return ;
        }

        view.setVisibleErrorArea2(false);

        ProgramSelectionDTO programSelectionDTO = getProgramBestStartingTime(
                user.getClientId(),
                device.getId(),
                days,
                duration
        );

        view.setDateInterval(
                programSelectionDTO.getStartTime(),
                programSelectionDTO.getEndTime()
        );

        view.updateDataset(programSelectionDTO);
    }

    private ProgramSelectionDTO getProgramBestStartingTime(UUID clientId, UUID deviceId, int days, int duration) {
        ProgramSelectionDTO programSelection = energyConsumptionService.getProgramBestStartingTime(
                clientId,
                deviceId,
                days,
                duration,
                TimeZone.getDefault()
        );

        return programSelection;
    }
}
