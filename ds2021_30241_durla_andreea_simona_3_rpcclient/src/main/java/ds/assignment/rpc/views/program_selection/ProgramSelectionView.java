package ds.assignment.rpc.views.program_selection;

import ds.assignment.rpc.dtos.DeviceDTO;
import ds.assignment.rpc.dtos.ProgramSelectionDTO;
import ds.assignment.rpc.views.styles.Style;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ProgramSelectionView extends JPanel {

    private JComboBox devices = new JComboBox();
    private JTextField daysField = new JTextField("7");
    private JTextField programDurationField = new JTextField("3");
    private JButton computeButton = new JButton("Compute");
    private JTextArea errorArea1 = new JTextArea();
    private JTextArea errorArea2 = new JTextArea();
    private JLabel dateInterval = new JLabel();
    private XYSeriesCollection dataset;
    private ChartPanel chartPanel = new ChartPanel(null);
    private JFreeChart chart;

    public ProgramSelectionView() {
        initUI();
    }

    private void initUI() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets.set(5, 10, 20,10);

        c.gridwidth = 2;
        //Title
        c.gridx = 0; c.gridy = 0;
        JLabel title = new JLabel("Program Selection");
        Style.styleLabel(title, new Font("Arial", Font.ITALIC, 25));
        this.add(title, c);

        c.insets.set(5, 10, 10,10);

        //Input Panel 1
        c.gridx = 0; c.gridy = 1;
        this.add(createInputPanel1(), c);

        //Error area 1
        c.gridx = 0; c.gridy = 2;
        Style.styleErrorArea(errorArea1, 28);
        this.add(errorArea1, c);

        c.gridwidth = 1;
        //ComboBox Device
        c.gridx = 0; c.gridy = 3;
        Style.styleComboBox(devices, 400, 30, new Font("Arial", Font.ITALIC, 15));
        this.add(devices, c);

        //Input Panel 2
        c.gridx = 1; c.gridy = 3;
        this.add(createInputPanel2(), c);

        c.gridwidth = 2;
        //Compute button
        c.gridx = 0; c.gridy = 4;
        Style.styleButton(computeButton, 100, 30, new Font("Arial", Font.ITALIC, 15));
        this.add(computeButton, c);

        c.insets.set(20, 10, 10,10);

        //Date interval
        c.gridx = 0; c.gridy = 5;
        Style.styleLabel(dateInterval, new Font("Arial", Font.PLAIN, 18));
        this.add(dateInterval, c);
        
        //Chart
        c.gridx = 0; c.gridy = 6;
        this.add(chartPanel, c);
    }

    private JPanel createInputPanel1() {
        JPanel panel = new JPanel();

        JLabel text1 = new JLabel("Baseline over ");
        Style.styleLabel(text1, new Font("Arial", Font.ITALIC, 15));
        panel.add(text1);

        Style.styleTextField(daysField, 50, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(daysField);

        JLabel text2 = new JLabel("days in the past");
        Style.styleLabel(text2, new Font("Arial", Font.ITALIC, 15));
        panel.add(text2);

        return panel;
    }

    private JPanel createInputPanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0; c.gridy = 0;
        c.insets.set(5, 10, 5,2);
        JLabel text1 = new JLabel("Program duration: ");
        Style.styleLabel(text1, new Font("Arial", Font.ITALIC, 15));
        panel.add(text1, c);

        c.gridx = 1; c.gridy = 0;
        c.insets.set(5, 2, 5,2);
        Style.styleTextField(programDurationField, 50, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(programDurationField, c);

        c.gridx = 2; c.gridy = 0;
        c.insets.set(5, 2, 5,2);
        JLabel text2 = new JLabel("hours");
        Style.styleLabel(text2, new Font("Arial", Font.ITALIC, 15));
        panel.add(text2, c);

        //Error area2
        c.gridwidth = 3; c.gridx = 0; c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        Style.styleErrorArea(errorArea2, 24);
        panel.add(errorArea2, c);

        return panel;
    }

    public void createChart(ProgramSelectionDTO programSelectionDTO) {

        createDataset(programSelectionDTO.getAveragedEnergyConsumption(),
                programSelectionDTO.getEstimatedConsumption());

        chart = ChartFactory.createXYLineChart(
                "",
                "Hours",
                "Energy consumption kW",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        //plot.setRenderer(new XYLineAndShapeRenderer());
        plot.setBackgroundPaint(Color.WHITE);

        chartPanel.setChart(chart);
    }

    private void createDataset(float[] averagedEnergyConsumption,
                                             float[] estimatedConsumption) {

        dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Averaged energy consumption");
        for(int i = 0; i < 24; i++) {
            series1.add(i, averagedEnergyConsumption[i]);
        }
        dataset.addSeries(series1);

        XYSeries series2 = new XYSeries("Estimated energy consumption");
        for(int i = 0; i < 24; i++) {
            series2.add(i, estimatedConsumption[i]);
        }
        dataset.addSeries(series2);
    }

    public void updateDataset(ProgramSelectionDTO programSelectionDTO) {
        if(programSelectionDTO == null)
            return ;

        if(chart == null) {
            createChart(programSelectionDTO);
        } else {
            createDataset(programSelectionDTO.getAveragedEnergyConsumption(),
                    programSelectionDTO.getEstimatedConsumption());
            chart.getXYPlot().setDataset(dataset);
        }
    }

    public void insertDevicesInComboBox(List<DeviceDTO> deviceDTOs) {
        deviceDTOs.forEach(d -> devices.addItem(d));
        devices.setSelectedIndex(0);
    }

    public void clearComboBox() {
        devices.removeAllItems();
    }

    public int getProgramDuration() {
        return Integer.parseInt(programDurationField.getText());
    }

    public int getDays() {
        return Integer.parseInt(daysField.getText());
    }

    public DeviceDTO getSelectedDevice() {
        return (DeviceDTO)devices.getSelectedItem();
    }

    public void addComputeButtonListener(ActionListener e) {
        computeButton.addActionListener(e);
    }

    public void setVisibleErrorArea1(boolean flag) {
        errorArea1.setVisible(flag);
    }

    public void setErrorArea1(String message) {
        errorArea1.setText(message);
    }

    public void setVisibleErrorArea2(boolean flag) {
        errorArea2.setVisible(flag);
    }

    public void setErrorArea2(String message) {
        errorArea2.setText(message);
    }

    public void setDateInterval(String startDate, String endDate) {
        dateInterval.setText(startDate + " - " + endDate);
    }
}
