package com.pika.knct;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

class Server {
    private static final String server = "http://192.168.0.100:5000/";

    static String queryBuilder(String[] query, String[] data) {
        if (query.length == data.length) {
            StringBuilder url = new StringBuilder();
            url.append("?");
            for (int counter=0;counter < query.length;counter++) {
                try {
                    data[counter] = URLEncoder.encode(data[counter], "utf-8");
                } catch (Exception e) {
                    Log.d("URL_ENCODE_ERROR", e.toString());
                }
                url.append(query[counter]).append("=").append(data[counter]);
                if (counter < query.length-1) url.append("&");
            }
            return url.toString();
        }
        return "Query!=ResponseERROR";
    }

    static String urlBuilder(String action, String query) {
        return server+action+query;
    }
    
    static String getServer() {
        return server;
    }
    
    static String getResponse(String urlString) {
        HttpURLConnection connection;
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                return bufferRead(connection);
            }
        } catch (Exception e) {
            Log.d("getResponse_ERROR", e.toString());
        }
        return "";
    }

    private static String bufferRead(HttpURLConnection conn) {
        try {
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

class registerTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... urls) {
        Server.getResponse(urls[0]);
        return null;
    }
}

class postQuestionTask extends AsyncTask<String, Void, Void> {

    private WeakReference<UserViewModel> userViewModelWeakReference;
    private WeakReference<QuestionViewModel> questionViewModelWeakReference;

    postQuestionTask(UserViewModel userViewModel, QuestionViewModel questionViewModel) {
        userViewModelWeakReference = new WeakReference<>(userViewModel);
        questionViewModelWeakReference = new WeakReference<>(questionViewModel);
    }

    @Override
    protected Void doInBackground(String... urls) {
        Server.getResponse(urls[0]);
        return null;
    }
}

class postAnswerTask extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... urls) {
        Server.getResponse(urls[0]);
        return null;
    }
}

class getQuestionsTask extends AsyncTask<String, Void, JSONObject> {

    private WeakReference<UserViewModel> userViewModelWeakReference;
    private WeakReference<QuestionViewModel> questionViewModelWeakReference;

    getQuestionsTask(UserViewModel userViewModel, QuestionViewModel questionViewModel) {
        userViewModelWeakReference = new WeakReference<>(userViewModel);
        questionViewModelWeakReference = new WeakReference<>(questionViewModel);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String result = Server.getResponse(strings[0]);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (Exception e) {
            Log.i("JSON", e.toString());
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        int length = jsonObject.length();
        for (int counter = 0; counter < length; counter++) {
            try {
                JSONObject questionObject = (JSONObject) jsonObject.get(String.valueOf(counter));
                Question question = new Question();
                question.setUserId(questionObject.getString("userId"));
                question.setPostedOn(questionObject.getString("postedOn"));
                question.setQuestion(questionObject.getString("question"));
                question.setSubject(questionObject.getString("subject"));
                question.setTags(questionObject.getString("tags"));
                questionViewModelWeakReference.get().insertQuestion(question);
            } catch (Exception e) {
                Log.i("JSON", e.toString());
            }
        }
    }
}