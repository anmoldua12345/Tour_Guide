package com.example.joginderpal.torist_guide_one;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 22-01-2017.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    List<String> li;
    List<String> li1,li2;
   // TextView tx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // String loc=getIntent().getExtras().getString("locat");
      //  tx= (TextView) findViewById(R.id.text3);

        new doit().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menusearchone);
        SearchView searchView= (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("pass","1");
                intent.putExtra("text",query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    public class doit extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                li=new ArrayList<>();
                li1=new ArrayList<>();
                li2=new ArrayList<>();
                Document doc= Jsoup.connect("http://www.tourism-of-india.com/things-to-do/").get();
                Elements e1=doc.getElementsByTag("div");
                for (Element e2: e1){

                    String cla=e2.attr("class");
                    if (cla.equals("col-md-3 col-sm-4 all-themes")){

                        Element a=e2.getElementsByTag("a").first();
                        String href=a.attr("href");
                        li.add(href);
                        Element img=a.getElementsByTag("img").first();
                        String data=img.attr("data-original");
                        li1.add(data);
                        Element span=a.getElementsByTag("span").first();
                        String text=span.text();
                        li2.add(text);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // tx.setText(li2.get(0));

            recyclerView= (RecyclerView) findViewById(R.id.rvactivitymain);
            layoutManager=new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            adapter=new RecyclerAdapter(li,li1,li2,MainActivity.this);
            recyclerView.setAdapter(adapter);


        }
    }



}
