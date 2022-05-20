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
    private double x;
    private boolean f = true, fl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeePlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        System.out.println(x);
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
                        binding.textGroupName.setText("начать");
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
                if(fl){
                    if (c!=0) fl = false;
                    ShowPlayers();
                }
            }
        });
        binding.closeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.getRoot().setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == R.id.offScreenLike) {
                    fl = true;
                    motionLayout.setProgress(0f);
                    motionLayout.setTransition(R.id.rest,R.id.like);
                    binding.textGroupName.setText(gameInGroup.get(c).getPlayer1());
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });
    }

    private void ShowPlayers(){
            if (f) {
                if (c >= gameInGroup.size()) {
                    finish();
                    c = 0;
                } else {
                    if (c != 0) {
                        binding.textGroupName2.setVisibility(View.VISIBLE);
                        binding.textGroupName2.setText(gameInGroup.get(c).getPlayer1());
                        binding.getRoot().transitionToState(R.id.like);
                        binding.textGroupName2.setVisibility(View.INVISIBLE);
                    } else {
                        binding.textGroupName.setText(gameInGroup.get(c).getPlayer1());
                    }
                    f = false;
                }
            } else {
                new FlipAnimation(SeePlayers.this, binding.textGroupName, f, gameInGroup.get(c).getPlayer1(), gameInGroup.get(c).getPlayer2());
                c++;
                f = true;
                fl = true;
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
                Toast.makeText(getApplicationContext(), "удалено", Toast.LENGTH_LONG).show();
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