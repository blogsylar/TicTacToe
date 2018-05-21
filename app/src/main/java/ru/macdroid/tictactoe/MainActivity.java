package ru.macdroid.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Button startButton;

    Intent intent;

    RadioGroup isCompOrUser, setWinsCount, levelOfHardness;

    RadioButton withPhone, withUser, threeWins, fiveWins, sevenWins, easy, hard;

    Boolean gameWithPc = true;

    int howMuchWins = 3;

    int hardness = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        isCompOrUser = (RadioGroup) findViewById(R.id.isCompOrUser);
        levelOfHardness = (RadioGroup) findViewById(R.id.levelOfHardness);
        setWinsCount = (RadioGroup) findViewById(R.id.setWinsCount);
        //isCompOrUser.clearCheck();
        isCompOrUser.setOnCheckedChangeListener(this);
        levelOfHardness.setOnCheckedChangeListener(this);
        setWinsCount.setOnCheckedChangeListener(this);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.withPhone:
                gameWithPc = true;
                break;
            case R.id.withUser:
                gameWithPc = false;
                break;
            case R.id.threeWins:
                howMuchWins = 3;
                break;
            case R.id.fiveWins:
                howMuchWins = 5;
                break;
            case R.id.sevenWins:
                howMuchWins = 7;
                break;
            case R.id.easy:
                hardness = 1;
                break;
            case R.id.hard:
                hardness = 2;
                break;


        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startButton:
                intent = new Intent(this, GameProcess.class);
                intent.putExtra("gameWithPc", gameWithPc);
                intent.putExtra("howMuchWins", howMuchWins);
                intent.putExtra("hardness", hardness);
                startActivity(intent);
                break;

        }

    }


    @Override
    protected void onDestroy() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalization();
        System.exit(0);
    }
}
