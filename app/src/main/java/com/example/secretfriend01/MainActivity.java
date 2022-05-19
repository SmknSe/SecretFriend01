package com.example.secretfriend01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.secretfriend01.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import room.Game;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> games_names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerviewGames.setLayoutManager(new LinearLayoutManager(this));
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                Bundle b = new Bundle();
                b.putStringArrayList("names",games_names);
                intent.putExtra("bundle",b);
                startActivity(intent);
            }
        });
        getGames();
    }

    private void getGames(){
        Disposable disposable = DBClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .gameDao()
                .getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Game>>() {
                    @Override
                    public void accept(List<Game> games) throws Exception {
                        for (int i=0;i<games.size();i++){
                            games_names.add(games.get(i).getGameName());
                        }
                        GameAdapter adapter = new GameAdapter(MainActivity.this,games);
                        binding.recyclerviewGames.setAdapter(adapter);
                    }
                });
    }

}