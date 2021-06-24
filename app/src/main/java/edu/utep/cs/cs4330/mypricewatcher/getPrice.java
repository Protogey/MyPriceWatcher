package edu.utep.cs.cs4330.mypricewatcher;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class getPrice extends AsyncTask<String,Void,String> {
    private String cssQuery = null;
    private String price;
    @Override
    protected String doInBackground(String...url) {
        try {
            String[] split = url[0].trim().split("\\.");
            //works for walmart
            if(split[1].equals("walmart")){
                Document document = Jsoup.connect(url[0]).get();
                cssQuery = document.select("span[class=display-inline-block-xs prod-PaddingRight--xs valign-top]").text();
                cssQuery = cssQuery.replace("$", " ");
                String[] hold = cssQuery.trim().split("\\s+");
                hold[0].replace(" ","");
                price = hold[0];
            }
            //works for sams
            if(split[1].equals("samsclub")){
                Document document = Jsoup.connect(url[0]).get();
                cssQuery = document.select("span[class=Price-group]").text();
                cssQuery = cssQuery.replace("current price: ","");
                cssQuery = cssQuery.replace("$", " ");
                String[] hold = cssQuery.trim().split("\\s+");
                hold[0].replace(" ","");
                price = hold[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }
}
