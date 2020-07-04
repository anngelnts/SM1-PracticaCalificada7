package com.desarrollo.practicacalificada7.JSON;

import android.os.AsyncTask;

import com.desarrollo.practicacalificada7.MainActivity;
import com.desarrollo.practicacalificada7.Models.BookModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser extends AsyncTask<String, Void, Boolean> {

    private MainActivity mainActivity;

    private ArrayList<BookModel> books = new ArrayList<>();
    private String jsonData;

    JSONParser(MainActivity mainActivity, String jsonData) {
        this.mainActivity = mainActivity;
        this.jsonData = jsonData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        if(isParsed){
            mainActivity.setBooks(books);
        }else{
            books.clear();
            mainActivity.setBooks(books);
        }
    }

    private Boolean parse()
    {
        //clear list
        books.clear();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                StringBuilder authors = new StringBuilder();
                if(volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    for (int j = 0; j < authorsArray.length(); j++) {
                        authors.append(authorsArray.getString(j));
                        if (authorsArray.length() - 1 > j) authors.append(", ");
                    }
                }

                //strings
                String id = item.getString("id");
                String title = volumeInfo.getString("title");
                String publisher = volumeInfo.has("publisher") ? volumeInfo.getString("publisher") : "";
                String thumbnail = "";
                if(volumeInfo.has("imageLinks")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    thumbnail = imageLinks.getString("thumbnail");
                }

                //model
                BookModel book = new BookModel();
                book.setId(id);
                book.setTitle(title);
                book.setAuthors(authors.toString());
                book.setPublisher(publisher);
                book.setThumbnail(thumbnail);
                books.add(book);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
