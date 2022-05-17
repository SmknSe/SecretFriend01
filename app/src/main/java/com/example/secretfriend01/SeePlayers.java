package com.example.secretfriend01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secretfriend01.databinding.ActivitySeePlayersBinding;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import room.Game;

public class SeePlayers extends AppCompatActivity implements FlipAnimation.FlipEnd {
    private ActivitySeePlayersBinding binding;
    private ArrayList<Game> gameInGroup = new ArrayList<Game>();
    private String s;
    private int c=0;
    private boolean f = true, fl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeePlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Game game = (Game) getIntent().getSerializableExtra("game");
        Disposable disposable = DBClient.getInstance(getApplicationContext()).getAppDatabase()
                .gameDao().getAll().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Game>>() {
                    @Override
                    public void accept(List<Game> games) throws Exception {
                        for (int i=0;i<games.size();i++){
                            if (games.get(i).getGameName().equals(game.getGameName())){
                                gameInGroup.add(games.get(i));
                            }
                        }
                        binding.textGroupName.setText("\n\n"+"start"+"\n\n");
                    }
                });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Game g : gameInGroup){
                    deleteGame(g);
                }
            }
        });
        binding.textGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPlayers();
            }
        });
        binding.closeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
    }

    private void ShowPlayers(){
        if (f) {
            if (c>=gameInGroup.size()){
                finish();
                c=0;
            }
            else {
                //new FlipAnimation(SeePlayers.this,binding.textGroupName,f,gameInGroup.get(c).getPlayer1(),gameInGroup.get(c).getPlayer2());
                binding.textGroupName.setText(gameInGroup.get(c).getPlayer1());
                f = false;
            }
        }
        else{
            new FlipAnimation(SeePlayers.this,binding.textGroupName,f,gameInGroup.get(c).getPlayer1(),gameInGroup.get(c).getPlayer2());
            c++;
            f = true;
        }
    }

    private void deleteGame(final Game game) {
        class DeleteGame extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DBClient.getInstance(getApplicationContext()).getAppDatabase()
                        .gameDao()
                        .delete(game);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(SeePlayers.this, MainActivity.class));
            }
        }

        DeleteGame dg = new DeleteGame();
        dg.execute();

    }

    @Override
    public void flipEnd(TextView txt) {

    }
}