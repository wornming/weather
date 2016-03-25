package com.wornming.weather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;

				try {
					URL url = new URL(address);

					connection = (HttpURLConnection) url.openConnection();
					// ���ö�ȡ��ʽ
					connection.setRequestMethod("GET");
					// ���ðٶ�apikey apikey-key
					connection.setRequestProperty("apikey", "ef852560cb3595c794008dbb51fa3921");
					connection.setReadTimeout(8000);
					connection.setConnectTimeout(8000);

					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
					StringBuilder response = new StringBuilder();

					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
						response.append("\r\n");
					}
					// ����ȡ������Ϣ��ص�onFinish����
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener != null) {
						listener.onError(e);
					}
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
