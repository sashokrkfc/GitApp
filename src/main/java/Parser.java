import jssc.SerialPort;
import jssc.SerialPortList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.usb4java.DeviceList;

import javax.usb.*;
import java.io.*;
import java.net.URL;

public class Parser {

    public static Document getPage() throws IOException {
        String url = "https://sinoptik.ua/погода-киев";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }


    public static void main(String[] args) throws IOException, UsbException {
        String time="",p="",wing="",temp="";

        Document page = getPage();
        Element tableweather = page.select("div[id=mainContentBlock]").first();
        Elements names = tableweather.select("div[id=bd1]");
        Elements detailweathertitl = tableweather.select("div[class=titles]");
        Elements detailweather = tableweather.select("table[class=weatherDetails]");


        System.out.println("Время суток: ");
        for (Element detailweathers:detailweather) {
             time = detailweather.select("tr[class=gray time]").text();
            System.out.println(time);
        }
        System.out.println("Температура: ");
        for (Element detailweathers:detailweather) {
             temp = detailweather.select("tr[class=temperature]").text();
            System.out.println(temp);
        }

        System.out.println("Давление");
        for (Element detailweathers:detailweather) {
             p = detailweather.select("tr[class=gray]").text();
           p = p.substring(0,31);
            System.out.println(p);
        }
        System.out.println("Ветер");
        for (Element detailweathers:detailweather) {
             wing = detailweather.select("tr[class=gray]").text();
             wing = wing.substring(31,63);
            System.out.println(wing);
        }

        File weatherToDay = new File("WeatherToDay.txt");

        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(weatherToDay, false)));
        writer.write(names.text()+ "\n" + "Время: " + time+ "\n" + "Температура: " + temp +
                "\n" + "Давление: " + p + "\n" + "Ветер: " + wing);
        writer.flush();
        writer.close();


//
//        String[] portNames = SerialPortList.getPortNames();
//        for(int i = 0; i < portNames.length; i++){
//            System.out.println(portNames[i]);
//        }
//
//    }

}}
//class DumpDevices {
//    /**
//     * Dumps the specified USB device to stdout.
//     *
//     * @param device The USB device to dump.
//     */
//
//
//    private static void dumpDevice(final UsbDevice device) {
//        // Dump information about the device itself
//        System.out.println(device);
//        final UsbPort port = device.getParentUsbPort();
//        if (port != null) {
//            System.out.println("Connected to port: " + port.getPortNumber());
//            System.out.println("Parent: " + port.getUsbHub());
//        }
//    }
//}

