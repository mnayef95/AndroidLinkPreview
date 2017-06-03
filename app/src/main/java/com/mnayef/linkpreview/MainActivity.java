package com.mnayef.linkpreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mnayef.library.callback.Callback;
import com.mnayef.library.http.task.LinkPreviewTask;
import com.mnayef.library.model.Link;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new LinksAdapter(getData()));

        new LinkPreviewTask(this, "https://www.github.com", new Callback() {
            @Override
            public void onSuccess(Link link) {
                // Use link object to get url data.
            }

            @Override
            public void onFailed() {
                // Handle network error
            }

            @Override
            public void onMalformedUrl() {
                // Handle malformed url
            }
        });
    }

    public List<String> getData() {
        List<String> links = new ArrayList<>();
        links.add("https://github.com/mnayef/AutoAdapter");
        links.add("https://githubdafadfasdfsadf.com");
        links.add("github");
        return links;
    }
}
