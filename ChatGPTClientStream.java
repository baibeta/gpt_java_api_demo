import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;
import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.*;


public class ChatGPTClientStream {
	private static final String API_URL = "https://api.openai.com/v1/chat/completions";

	private static String getPrompt(int number) {
		System.out.println(number + ". Please input your question:");
		Scanner sc=new Scanner(System.in);  
		return sc.nextLine();
	}

    public static void main(String[] args) throws Exception {
        String apiKey = "YOUR_API_KEY begin with sk-xxx";
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.10.8", 7890));
        OkHttpClient client = new OkHttpClient.Builder().proxy(proxy).build();

		JSONObject json = new JSONObject();
		json.put("model", "gpt-3.5-turbo");
		json.put("temperature", 0.4f);
		json.put("stream", true);
		JSONArray messages = new JSONArray();
		JSONObject sysMsg = new JSONObject();
		sysMsg.put("role", "system");
		sysMsg.put("content", "You are a helpful assistant.");
		messages.put(sysMsg);


		for(int i = 1; i < 4; i++) {
			String prompt = getPrompt(i);
			JSONObject promptMsg = new JSONObject();
			promptMsg.put("role", "user");
			promptMsg.put("content", prompt);
			messages.put(promptMsg);
			json.put("messages", messages);

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(json.toString(), mediaType);
			Request request = new Request.Builder()
					.url(API_URL)
					.post(body)
					.addHeader("Authorization", "Bearer " + apiKey)
					.addHeader("Content-Type", "application/json")
					.build();
			Response response = client.newCall(request).execute();
			

			BufferedReader reader = new BufferedReader(response.body().charStream());
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("data: ") && !line.startsWith("data: [DONE]")) {
					line = line.substring(6);

					JSONObject jsonObject = new JSONObject(line);
					JSONArray choicesArray = jsonObject.getJSONArray("choices");
					if (choicesArray.length() > 0) {
						JSONObject deltaObject = choicesArray.getJSONObject(0).getJSONObject("delta");
						if (deltaObject.has("content")) {
							String content = deltaObject.getString("content");
							System.out.print(content);
						}
					}
				}
			}
			System.out.println("");

		}
		System.out.println("Finish demo!");
    }
}