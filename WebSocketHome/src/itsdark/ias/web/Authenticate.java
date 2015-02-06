package itsdark.ias.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Authenticate {

	static String jsessionID, ltValue, ticket, phpSessID;
	static String username = "yashasvi.girdhar@students.iiit.ac.in",
			password = "psptgood";

	public static void main(String[] args) {

		String coursesUrl = "https://login.iiit.ac.in/cas/login?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php";
		ProxySelector.setDefault(null);
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL myURL = new URL(coursesUrl);
			System.out.println(myURL);
			HttpsURLConnection myURLConnection = (HttpsURLConnection) myURL
					.openConnection();
			myURLConnection.setRequestProperty("Host", "login.iiit.ac.in");
			myURLConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0");
			myURLConnection
					.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			myURLConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			myURLConnection.setRequestProperty("Accept-Encoding",
					"gzip, deflate");
			myURLConnection.setRequestProperty("Connection", "keep-alive");

			// Map<String, List<String>> map =
			// myURLConnection.getHeaderFields();
			// for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			// System.out.println("Key : " + entry.getKey() + " ,Value : "
			// + entry.getValue());
			// }

			jsessionID = myURLConnection.getHeaderField("Set-Cookie");
			jsessionID = jsessionID.split(";")[0].split("=")[1];
			System.out.println("jsessionID : " + jsessionID);
			print_content(myURLConnection, 1);
			myURLConnection.disconnect();

			String ticketUrl = "https://login.iiit.ac.in/cas/login;jsessionid="
					+ jsessionID
					+ "?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php";
			String urlParameters = "username=" + username + "&password="
					+ password + "&lt=" + ltValue + "&_eventId=" + "submit";
			myURL = new URL(ticketUrl);
			myURLConnection = (HttpsURLConnection) myURL.openConnection();
			myURLConnection.setRequestProperty("Cookie", "JSESSIONID="
					+ jsessionID);
			myURLConnection.setRequestProperty("Host", "login.iiit.ac.in");
			myURLConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0");
			myURLConnection
					.setRequestProperty(
							"Referer",
							"https://login.iiit.ac.in/cas/login?service=http%3A%2F%2Fcourses.iiit.ac.in%2FEdgeNet%2Fhome.php");
			myURLConnection.setRequestProperty("Connection", "keep-alive");
			myURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			myURLConnection.setRequestMethod("POST");
			myURLConnection.setDoOutput(true);
			// myURLConnection.setDoInput(true);
			DataOutputStream wr = new DataOutputStream(
					myURLConnection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			ticket = myURLConnection.getHeaderField("Location").split("\\?")[1]
					.split("=")[1];
			System.out.println("ticket : " + ticket);
			// print_content(myURLConnection, 2);
			myURLConnection.disconnect();

			String phpurl = "http://courses.iiit.ac.in/EdgeNet/home.php?ticket="
					+ ticket;
			myURL = new URL(phpurl);
			HttpURLConnection newConnection;
			newConnection = (HttpURLConnection) myURL.openConnection();
			newConnection.setRequestProperty("Host", "courses.iiit.ac.in");
			newConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (X11; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0");
			newConnection.setRequestProperty("Connection", "keep-alive");

			Map<String, List<String>> map3 = newConnection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map3.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " ,Value : "
						+ entry.getValue());
			}
			phpSessID = newConnection.getHeaderField("Set-Cookie").split(";")[0]
					.split("=")[1];
			System.out.println("phpSessID : " + phpSessID);
			// print_marks(newConnection);
			newConnection.disconnect();

//			String marksUrl = "http://courses.iiit.ac.in/EdgeNet/marks.php?select=1329&page=0";
//			myURL = new URL(marksUrl);
//			newConnection = (HttpURLConnection) myURL.openConnection();
//			newConnection
//					.setRequestProperty("Cookie", "PHPSESSID=" + phpSessID);
//			newConnection.setRequestProperty("Host", "courses.iiit.ac.in");
//			print_marks(newConnection);
		} catch (IOException | KeyManagementException
				| NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}
	} };

	private static void print_content(HttpsURLConnection con, int opt) {
		if (con != null) {
			try {
				// System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String input;
				while ((input = br.readLine()) != null) {
					switch (opt) {
					case 1:
						if (input.contains("lt")) {
							// System.out.println(input);
							ltValue = input.split(" ")[3].split("=")[1]
									.replace("\"", "");
							System.out.println("ltValue : " + ltValue);
						}
						break;
					case 2:
						System.out.println(input);
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void print_marks(HttpURLConnection con) {
		if (con != null) {
			try {
				// System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
