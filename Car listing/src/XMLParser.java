import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.*;
import java.io.*;

public class XMLParser {

	public static void main(String[] args) throws Exception {
		String url = "http://www.pazar3.mk/%D0%9E%D0%B3%D0%BB%D0%B0%D1%81%D0%B8/%D0%A6%D0%B5%D0%BB%D0%B0-%D0%9C%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D0%B8%D1%98%D0%B0/"
				+ "%D0%90%D0%B2%D1%82%D0%BE%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D0%B8";

		Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
		Elements container = doc.select("div[class='groups-count']");
		Elements cars = container.select("a");
		StringBuilder text = new StringBuilder();
		Writer writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream("car-listings.txt"),"utf-8"));
		for (Element element : cars) {
			System.out.println(element.text() + ":");
			writer.write(element.text());
			System.lineSeparator();
			String link = "http://www.pazar3.mk/" + URLEncoder.encode(element.attr("href"), "UTF-8");
			link = link.replace("+", "%20");
			Document dox = Jsoup.parse(new URL(link).openStream(), "UTF-8", link);
			Elements container_cars = dox.select("div[class='result-content']");
			Elements car_listing = container_cars.select("a[class='Link_vis']");
			for (Element header : car_listing) {
				System.out.println("  " + header.text() );
				writer.write("  " + header.text());
				System.lineSeparator();
			}
		}
		
	}
}