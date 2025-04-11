import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
Creates a URL for requesting the weather.
Uses a DOM parser to work with XML.
Gets the temperature in degrees Celsius.
Outputs the result to the console.
Handles possible exceptions.
@author Setaniel
@version 1.1.6
*/

public class WeatherReader {

    static void showWeather(){
        try {

            // Get URL for request
            String apiUrl = "https://api.weatherapi.com/v1/current.xml?key=f087c7e5eca544ae85d135815250904&q="
                    + GeoLocationFinder.getCity() + "&aqi=no";
            URL url = new URL(apiUrl);

            // Creating a factory for XML parsing
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // We receive the document
            Document doc = builder.parse(url.openStream());

            // We get a node with a temperature
            NodeList tempNode = doc.getElementsByTagName("feelslike_c");


            if (tempNode.getLength() > 0) {
                float temp = Float.parseFloat(tempNode.item(0).getTextContent());
                if (temp > 0) {
                    Utility.getInstance().getWeatherLabelHot().setText(temp + "°");
                } else {
                    Utility.getInstance().getWeatherLabelCold().setText(temp + "°");
                }

            } else {
                Utility.getInstance().getWeatherLabelGrey().setText("- °");
            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}