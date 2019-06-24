import org.json.JSONException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainWindow extends JFrame {
    private String[] localisationStrings = { "Warsaw", "Berlin", "London", "Paris", "Rome", "Barcelona", "Toledo", "Madrid", "Hamburg", "Houston" };
    private String id;
    private JLabel Title;
    private JPanel rootPanel;
    private JLabel A_Localisation;
    private JLabel LocalisationB;
    private JLabel LocalisationC;
    private JComboBox comboBox1;
    private JButton checkWeatherButton;
    private JLabel LabelD;
    private JLabel Place;
    private JLabel weather;
    private JLabel Ask;
    private JCheckBox checkBox1;
    private JButton showLastDataButton;
    private JLabel oldDataField;
    private JTextField textField1;
    private JsonHandler handler = new JsonHandler();
    private String Weather;
    private String Selected_localisation;
    private Bean data = new Bean();
    private DBHandler dbhandler = new DBHandler();
    private int num = 0;
    private String oldData;

    public MainWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dbhandler.deleteAllEntries();
                dbhandler.shutdown();

            }
        });
        //Initialisation
        add(rootPanel);
        dbhandler.createConnection();
        setTitle("Weather App");
        setSize(600,500);
        Arrays.sort(localisationStrings);
        for (int i = 0; i < localisationStrings.length; i++){
            comboBox1.addItem(localisationStrings[i]);
        }
        Place.setText((String) comboBox1.getSelectedItem());
        checkWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Selected_localisation = (String)comboBox1.getSelectedItem();
                Place.setText(Selected_localisation);
                switch (Selected_localisation) {
                    case "London":
                        id = "2643743";
                        Weather = "Ok";
                        break;
                    case "Warsaw":
                        id = "756135";
                        Weather = "Ok";
                        break;
                    case "Barcelona":
                        id = "3128760";
                        Weather = "Ok";
                        break;
                    case "Berlin":
                        id = "2950159";
                        Weather = "Ok";
                        break;
                    case "Paris":
                        id = "2988507";
                        Weather = "Ok";
                        break;
                    case "Rome":
                        id = "6539761";
                        Weather = "Ok";
                        break;
                    case "Toledo":
                        id = "2510409";
                        Weather = "Ok";
                        break;
                    case "Hamburg":
                        id = "2911298";
                        Weather = "Ok";
                        break;
                    case "Houston":
                        id = "4699066";
                        Weather = "Ok";
                        break;
                    default:
                        Weather = "Unknown localisation";
                        break;
                }
                if (!Weather.equals("Unknown localisation")) {
                    try {
                        Weather = handler.getWeather(id);
                    } catch (IOException | JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                weather.setText(Weather);
                getData(data);
                if (data.isAskSave()){
                    dbhandler.insertEntry(num, Weather);
                }
                num ++;
            }
        });
        getData(data);
        showLastDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldData = dbhandler.selectWeatherEntries();
                oldDataField.setText(oldData);
                dbhandler.deleteAllEntries();
            }
        });
    }

    public void setData(Bean data) {
        checkBox1.setSelected(data.isAskSave());
    }

    public void getData(Bean data) {
        data.setAskSave(checkBox1.isSelected());
    }
}
