package com.github.oliveiradev.image_zoom.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int NUM_COLUNMS = 2;

    private RecyclerView recyclerTeams;
    private RecyclerView.LayoutManager layoutManager;
    private TeamsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerTeams = (RecyclerView) findViewById(R.id.teams_recycler);

        recyclerTeams.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,NUM_COLUNMS);
        adapter = new TeamsAdapter();
        recyclerTeams.setLayoutManager(layoutManager);
        recyclerTeams.setAdapter(adapter);

        addItemsOnAdapter();
    }

    private void addItemsOnAdapter() {
        List<Team> teams = new LinkedList<>();
        teams.add(Team.Factory.create("Barcelona",R.mipmap.barca));
        teams.add(Team.Factory.create("Real Madrid",R.mipmap.realmadrid));
        teams.add(Team.Factory.create("Bayer de Munique",R.mipmap.bayer));
        teams.add(Team.Factory.create("Borussia Dortmund",R.mipmap.dortmund));
        adapter.add(teams);
    }
}
