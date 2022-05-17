package com.example.secretfriend01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import room.Game;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private Context mCtx;
    private List<Game> gameList;
    private HashSet<String> gameSet = new HashSet<>();
    private List<Integer> pos = new ArrayList<>();

    public GameAdapter(Context mCtx, List<Game> gameList) {
        this.mCtx = mCtx;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_games,parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        while (gameSet.contains(game.getGameName())){
            position+=1;
            game = gameList.get(position);
        }
        pos.add(position);
        if (!gameSet.contains(game.getGameName())){
            gameSet.add(game.getGameName());
            holder.textViewGameName.setText(game.getGameName());
        }
    }

    @Override
    public int getItemCount() {
        HashSet<String> s = new HashSet<>();
        for (Game g: gameList){
            s.add(g.getGameName());
        }
        return s.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewGameName;

        public GameViewHolder(View itemView){
            super(itemView);

            textViewGameName = itemView.findViewById(R.id.textViewGameName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Game game = gameList.get(pos.get(getAdapterPosition()));
            Intent intent = new Intent(mCtx,SeePlayers.class);
            intent.putExtra("game",game);
            mCtx.startActivity(intent);
        }
    }
}
