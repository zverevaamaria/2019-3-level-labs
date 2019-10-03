package example.govno3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.


@SpringBootApplication
public class Govno3Application {

	public static void main(String[] args) {
		SpringApplication.run(Govno3Application.class, args);
	}

	public Govno3Application() throws IOException {
		//convert to the url
		String meduzaURL = "https://meduza.io/";
		URL url = new URL (meduzaURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		//conver to json obj
		JsonParser ourparser = new JsonParser();
		JsonElement root = ourparser.parse(new InputStreamReader((InputStream)request.getContent()));
		JsonObject rootobj = root.getAsJsonObject();
	}

}
