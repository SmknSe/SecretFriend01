package com.example.secretfriend01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.secretfriend01.databinding.ActivityAddGameBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import room.Game;

public class AddGame extends AppCompatActivity {

    ActivityAddGameBinding binding;
    public String groupName;
    public Integer groupCount;
    private ArrayList<String> players = new ArrayList<String>();
    private HashMap<String,String> pool = new HashMap<String,String>();
    private int i=0,j=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName = binding.groupNameTxt.getText().toString();
                if (!(groupName.equals(""))){
                    updateFragment();
                }
                else{
                    binding.groupNameTxt.setError("Необходимо заполнить!");
                }
            }
        });
    }

    private void updateFragment() {
        binding.label.setText("Введите количество:");
        if (binding.groupNameTxt.length()>0){
            binding.groupNameTxt.getText().clear();
        }
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(binding.groupNameTxt.getText().toString().equals("")) && isNumeric(binding.groupNameTxt.getText().toString())){
                    groupCount = Integer.parseInt(binding.groupNameTxt.getText().toString());
                    if (!(groupCount<=0 || groupCount%2!=0))
                        updateFragment2();
                    else if (groupCount%2!=0) binding.groupNameTxt.setError("Необходимо четное число!");
                }
                else if (binding.groupNameTxt.getText().toString().equals("")){
                    binding.groupNameTxt.setError("Необходимо заполнить!");
                }
                else {
                    binding.groupNameTxt.setError("Необходимо четное число!");
                }
            }
        });
    }

    private void updateFragment2() {
        binding.label.setText("Введите первую половину игроков:(еще "+ (groupCount/2-i)+")");
        if (binding.groupNameTxt.length()>0){
            binding.groupNameTxt.getText().clear();
        }
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = binding.groupNameTxt.getText().toString();
                if (!(s.equals(""))){
                    players.add(s);
                    i++;
                    updateFragment2();
                }
                if ((groupCount/2-i)==0) updateFragment3();
            }
        });
    }

    private void updateFragment3() {
        if (groupCount-i-j==0){
            saveGame();
        }
        binding.label.setText("Введите вторую половину игроков:(еще "+ (groupCount-i-j)+")");
        if (binding.groupNameTxt.length()>0){
            binding.groupNameTxt.getText().clear();
        }
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = binding.groupNameTxt.getText().toString();
                if (!(s.equals(""))){
                    int r = (int) (Math.random() * players.size());
                    pool.put(players.get(r),s);
                    players.remove(r);
                    j++;
                    updateFragment3();
                }
                System.out.println(pool);
            }
        });
    }

    public void saveGame(){
        class SaveTask extends AsyncTask<Void,Void,Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                Game game = new Game();
                game.setGameName(groupName);
                for (Map.Entry<String,String> entry:pool.entrySet()) {
                    game.setPlayer1(entry.getKey());
                    game.setPlayer2(entry.getValue());

                    DBClient.getInstance(getApplicationContext()).getAppDatabase().gameDao().insert(game);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }
        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
