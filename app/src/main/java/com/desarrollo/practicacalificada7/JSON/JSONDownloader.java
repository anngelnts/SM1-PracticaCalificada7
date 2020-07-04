package com.desarrollo.practicacalificada7.JSON;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.desarrollo.practicacalificada7.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class JSONDownloader extends AsyncTask<String, Integer, String> {

    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    public JSONDownloader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle("Búsqueda de libro");
        progressDialog.setMessage("Buscando... por favor espera");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        return download(strings[0]);
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        progressDialog.dismiss();

        if(jsonData!=null){
            JSONParser jsonParser = new JSONParser(mainActivity, jsonData);
            jsonParser.execute();
        }else {
            Toast.makeText(mainActivity, "No se encontro resultados", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    private String download(String endPoint)
    {
        try {
            HttpURLConnection connection = Connector.connect(endPoint);
            assert connection != null;
            if(connection.getResponseCode() == 200)
            {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder jsonData = new StringBuilder();
                //read
                while ((line=bufferedReader.readLine()) != null){
                    jsonData.append(line).append("\n");
                }
                //close resources
                bufferedReader.close();
                inputStream.close();
                //return json
                return jsonData.toString();
            }
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
