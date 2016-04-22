package com.github.oliveiradev.image_zoom.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.oliveiradev.image_zoom.lib.widget.ImageZoom;

import java.util.List;

/**
 * Created by felipe on 22/04/16.
 */
public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private List<Team> teams;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(teams.get(position));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void add(List<Team> teams){
        this.teams = teams;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView teamName;
        private ImageZoom teamLogo;

        public ViewHolder(View itemView) {
            super(itemView);

            teamName = (TextView) itemView.findViewById(R.id.team_name);
            teamLogo = (ImageZoom) itemView.findViewById(R.id.team_logo);
        }

        public void bind(Team team){
            teamName.setText(team.getName());
            teamLogo.setImageResource(team.getLogo());
        }
    }
}
