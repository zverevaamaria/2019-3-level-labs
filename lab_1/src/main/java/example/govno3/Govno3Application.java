package example.govno3;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


@SpringBootApplication
public class Govno3Application {

	public Govno3Application() throws IOException {
	}

	public static void main(String[] args) {
		SpringApplication.run(Govno3Application.class, args);
	}
	JSONObject json = new JSONObject(IOUtils.toString(new URL("https://meduza.io/"), Charset.forName("UTF-8")));
	/*у меня есть объект json, надо его преобразовать в dom */

}
