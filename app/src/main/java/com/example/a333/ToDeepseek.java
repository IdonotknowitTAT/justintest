package com.example.a333;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.Protocol;

public class ToDeepseek {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static Context appContext;
    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }
    /**
     * 调用 DeepSeek 聊天接口（必须在子线程中调用）
     * @param userMsg   用户输入的问题
     * @return          模型的回复文本
     * @throws IOException   网络或 API 返回错误
     * @throws JSONException 解析响应JSON失败
     */
    public static String callDeepSeek(String userMsg) throws IOException, JSONException {
        SharedPreferences sp = appContext.getSharedPreferences("DailyDate", Context.MODE_PRIVATE);
        String apiKey = sp.getString("deepseekapi", "");
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))//强制HTTP/1.1，避免HTTP/2在部分模拟器上卡死
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();// 构造请求体
        JSONObject body = new JSONObject();
        body.put("model", "deepseek-v4-flash");          // 推荐使用 deepseek-chat 模型
        body.put("stream", false);

        JSONArray messages = new JSONArray();
        JSONObject msg = new JSONObject();
        msg.put("role", "user");
        msg.put("content", userMsg);
        messages.put(msg);
        body.put("messages", messages);

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(body.toString(), JSON))
                .build();

        // 发送请求并获取响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "无详情";
                throw new IOException("请求失败: " + response.code() + ", " + errorBody);
            }

            String json = response.body().string();
            JSONObject resObj = new JSONObject(json);
            JSONObject message = resObj.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message");//获取消息对象
            String content = message.optString("content", "");//答案，字段缺失时返回空串
            String reasoning = message.optString("reasoning_content", "");//思考过程，普通模型没有此字段，需兜底
            boolean showReasoning = sp.getBoolean("showreasoning", false);//是否展示思考过程
            if (showReasoning && !reasoning.isEmpty()) {
                return "【思考过程】\n" + reasoning + "\n\n【回答】\n" + content;
            }
            return content;
        }
    }
}