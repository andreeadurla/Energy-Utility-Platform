package ds.assignment.rpc.views.historical_energy_consumption;

import ds.assignment.rpc.dtos.BaselineDTO;
import ds.assignment.rpc.dtos.EnergyConsumptionDTO;
import ds.assignment.rpc.views.styles.Style;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

import java.util.List;

public class HistoricalEnergyConsumptionView extends JPanel {

    private JTextField daysField = new JTextField("7");
    private JButton computeButton = new JButton("Compute");
    private JLabel dateInterval = new JLabel();

    private XYSeriesCollection datasetHistorical;
    private ChartPanel chartPanelHistorical = new ChartPanel(null);
    private JFreeChart chartHistorical;

    private XYSeriesCollection datasetAveraged;
    private ChartPanel chartPanelAveraged = new ChartPanel(null);
    private JFreeChart chartAveraged;

    private JTextArea errorArea = new JTextArea();

    public HistoricalEnergyConsumptionView() {
        initUI();
    }

    private void initUI() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets.set(5, 10, 10,10);

        //Title
        c.gridx = 0; c.gridy = 0;
        JLabel title = new JLabel("Historical Energy Consumption");
        Style.styleLabel(title, new Font("Arial", Font.ITALIC, 25));
        this.add(title, c);

        //Input Panel
        c.gridx = 0; c.gridy = 1;
        this.add(createInputPanel(), c);

        //Error area
        c.gridx = 0; c.gridy = 2;
        Style.styleErrorArea(errorArea, 28);
        this.add(errorArea, c);

        //Compute button
        c.gridx = 0; c.gridy = 3;
        Style.styleButton(computeButton, 100, 30, new Font("Arial", Font.ITALIC, 15));
        this.add(computeButton, c);

        c.insets.set(20, 10, 10,10);

        //Date interval
        c.gridx = 0; c.gridy = 4;
        Style.styleLabel(dateInterval, new Font("Arial", Font.PLAIN, 18));
        this.add(dateInterval, c);

        c.insets.set(20, 10, 10,10);

        //Charts Panel
        c.gridx = 0; c.gridy = 5;
        JPanel panel = new JPanel();
        panel.add(chartPanelHistorical);
        panel.add(chartPanelAveraged);
        this.add(panel, c);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();

        JLabel text1 = new JLabel("over ");
        Style.styleLabel(text1, new Font("Arial", Font.ITALIC, 15));
        panel.add(text1);

        Style.styleTextField(daysField, 50, 30, new Font("Arial", Font.ITALIC, 15));
        panel.add(daysField);

        JLabel text2 = new JLabel("days in the past");
        Style.styleLabel(text2, new Font("Arial", Font.ITALIC, 15));
        panel.add(text2);

        return panel;
    }

    public void createHistoricalConsumptionChart(List<EnergyConsumptionDTO> energyConsumption) {

        createHistoricalConsumptionDataset(energyConsumption);

        chartHistorical = ChartFactory.createXYLineChart(
                "",
                "Hours",
                "Energy consumption kW",
                datasetHistorical,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chartHistorical.getXYPlot();
        //plot.setRenderer(new XYLineAndShapeRenderer());
        plot.setBackgroundPaint(Color.WHITE);

        chartPanelHistorical.setChart(chartHistorical);
        chartPanelHistorical.setBorder(new EmptyBorder(0, 10, 0, 10));
    }

    private void createHistoricalConsumptionDataset(List<EnergyConsumptionDTO> energyConsumptionDTOs) {
        datasetHistorical = new XYSeriesCollection();

        for(EnergyConsumptionDTO energyConsumptionDTO: energyConsumptionDTOs) {

            XYSeries series = new XYSeries(energyConsumptionDTO.getDate());

            float[] energyConsumption = energyConsumptionDTO.getEnergyConsumption();
            for(int i = 0; i < 24; i++) {
                series.add(i, energyConsumption[i]);
            }

            datasetHistorical.addSeries(series);
        }
    }

    public void updateHistoricalConsumptionDataset(List<EnergyConsumptionDTO> energyConsumption) {
        createHistoricalConsumptionDataset(energyConsumption);
        chartHistorical.getXYPlot().setDataset(datasetHistorical);
    }

    public void createAveragedConsumptionChart(BaselineDTO baselineDTO) {

        createAveragedConsumptionDataset(baselineDTO.getAveragedEnergyConsumption());

        chartAveraged = ChartFactory.createXYLineChart(
                "",
                "Hours",
                "Energy consumption kW",
                datasetAveraged,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chartAveraged.getXYPlot();
        //plot.setRenderer(new XYLineAndShapeRenderer());
        plot.setBackgroundPaint(Color.WHITE);

        chartPanelAveraged.setChart(chartAveraged);
        chartPanelAveraged.setBorder(new EmptyBorder(0, 10, 0, 10));
    }

    private void createAveragedConsumptionDataset(float[] energyConsumption) {
        datasetAveraged = new XYSeriesCollection();

        XYSeries series = new XYSeries("baseline");

        for(int i = 0; i < 24; i++) {
            series.add(i, energyConsumption[i]);
        }

        datasetAveraged.addSeries(series);
    }

    public void updateAveragedConsumptionDataset(BaselineDTO baselineDTO) {
        if(baselineDTO == null)
            return ;

        createAveragedConsumptionDataset(baselineDTO.getAveragedEnergyConsumption());
        chartAveraged.getXYPlot().setDataset(datasetAveraged);
    }

    public void setDateInterval(String startDate, String endDate) {
        dateInterval.setText(startDate + " - " + endDate);
    }

    public int getDays() {
        return Integer.parseInt(daysField.getText());
    }

    public void addComputeButtonListener(ActionListener e) {
        computeButton.addActionListener(e);
    }

    public void setVisibleErrorArea(boolean flag) {
        errorArea.setVisible(flag);
    }

    public void setErrorArea(String message) {
        errorArea.setText(message);
    }
}
