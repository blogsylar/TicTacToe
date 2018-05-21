package ru.macdroid.tictactoe;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameProcess extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    int makeMove = 1; // попеременная передача хода
    int p1Count = 0; // изначальная инициализация переменной счетчика побед
    int p2Count = 0; // изначальная инициализация переменной счетчика побед
    int howMuchWins = 3; // до какого счета идет игра
    int checkWinCountPlayer1; // переменная для проверки до кторого счета идет игра
    int checkWinCountPlayer2; // переменная для проверки до кторого счета идет игра
    int someRandomInt; // и нициализация рандомного хода
    int hardRandomInt; // инициализация рандомного хода на сложном уровне
    int hardness = 1;

    Boolean ifWins = false;  // проверка, если есть победа в раунде, ходы блокируются
    Boolean gameWithPc = true;
    Boolean isPlayerMove = true;
    Boolean isRoundWin = false; // проверка, выигран ли раунд или игра
    Boolean noSwitchMove = true; // отмена перемены хода

    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btPause, startScreen;

    TextView player1Count, player2Count, winsCount;

    Switch themeWindow, switchMove;

    LinearLayout mainWorkSpace;

    Random r, hardR;

    Intent backToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_process);

        /**
         * Инициализация кнопок
         */

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt7 = (Button) findViewById(R.id.bt7);
        bt8 = (Button) findViewById(R.id.bt8);
        bt9 = (Button) findViewById(R.id.bt9);
        startScreen = (Button) findViewById(R.id.startScreen);
        btPause = (Button) findViewById(R.id.btPause) ;
        //battery = (TextView) findViewById(R.id.battery);
        player1Count = (TextView) findViewById(R.id.player1Count);
        player2Count = (TextView) findViewById(R.id.player2Count);
        winsCount = (TextView) findViewById(R.id.winsCount);
        switchMove = (Switch) findViewById(R.id.switchMove);
        themeWindow = (Switch) findViewById(R.id.themeWindow);
        mainWorkSpace = (LinearLayout) findViewById(R.id.mainWorkSpace);

        switchMove.setChecked(true);
        switchMove.setOnCheckedChangeListener(this);

        themeWindow.setChecked(false);
        themeWindow.setOnCheckedChangeListener(this);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        btPause.setOnClickListener(this);
        startScreen.setOnClickListener(this);

        /**
         * Принимаю данные из стартовой активности
         */

        Intent intent = getIntent();
        gameWithPc = intent.getBooleanExtra("gameWithPc", true); // принимаю из стартовой активности режим игры с кем
        //noSwitchMove = intent.getBooleanExtra("noSwitchMove", true); // принимаю из стартовой активности отключение перемены хода
        howMuchWins = intent.getIntExtra("howMuchWins", 3);
        hardness = intent.getIntExtra("hardness", 1);


        //player2Count.setCompoundDrawables(null, getResources().getDrawable(R.drawable.ic_phone_iphone_black_24dp), null, null);

        if (makeMove == 2 && gameWithPc) { // невозможность сделать ход когда ходит комп
            btDisable();
        }

       // player1.setText("Player 1: " + P1name);
       // player2.setText("Player 2: " + P2name);

//        /**
//         * Уровень заряда аккумулятора
//         */
//
//
//        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//        int Batt = ((level / scale) * 100);
//
//        battery.setText(Batt + "%"); // уровень заряда аккумуллятора

        winsCount.setText("до " + howMuchWins + " побед " + hardness); // надпись на счетчике до которого числа побед

        colorOfCount();

    }

    /**
     * Проверка на общую победу
     */


    public void checkWinsCount() {

        checkWinCountPlayer1 = howMuchWins - p1Count;
        checkWinCountPlayer2 = howMuchWins - p2Count;

        if (checkWinCountPlayer1 == 0 && checkWinCountPlayer2 != 0) {
            Toast.makeText(this, "Игрок 1 выиграл битву! ПОЗДРАВЛЯЕМ!!!", Toast.LENGTH_LONG).show();
            isRoundWin = true;
            p1Count = 0;
            p2Count = 0;
            player1Count.setText("" + p1Count);
            player2Count.setText("" + p2Count);
        }

        if (checkWinCountPlayer2 == 0 && checkWinCountPlayer1 != 0) {
            Toast.makeText(this, "Игрок 2 выиграл битву! ПОЗДРАВЛЯЕМ!!!", Toast.LENGTH_LONG).show();
            isRoundWin = true;
            p1Count = 0;
            p2Count = 0;
            player1Count.setText("" + p1Count);
            player2Count.setText("" + p2Count);
        }

    }

    /**
     * Проверка на заполнение трех подряд
     */

    public void checkGame() {
        checkWinner(bt1, bt2, bt3);
        checkWinner(bt4, bt5, bt6);
        checkWinner(bt7, bt8, bt9);
        checkWinner(bt1, bt4, bt7);
        checkWinner(bt2, bt5, bt8);
        checkWinner(bt3, bt6, bt9);
        checkWinner(bt1, bt5, bt9);
        checkWinner(bt3, bt5, bt7);

        colorOfCount();
    }

    public void checkWinner(Button wn1, Button wn2, Button wn3) {

        if (wn1.getText().toString() == wn2.getText().toString() && wn1.getText().toString() == wn3.getText().toString() && wn1.getText().toString() == "X") {
            btDisable();
            Toast.makeText(this, "Игрок 1 побеждает в бою!", Toast.LENGTH_SHORT).show();
            wn1.setBackground(getResources().getDrawable(R.drawable.x_win));
            wn2.setBackground(getResources().getDrawable(R.drawable.x_win));
            wn3.setBackground(getResources().getDrawable(R.drawable.x_win));
            p1Count++;
            player1Count.setText("" + p1Count);
            checkWinsCount();
            ifWins = true;
            sleepAfterWinsOrNothing();
            //Log.d("happy", " " + isPlayerMove);

        }

        else if (wn1.getText().toString() == wn2.getText().toString() && wn1.getText().toString() == wn3.getText().toString() && wn1.getText().toString() == "O") {
            btDisable();
            Toast.makeText(this, "Игрок 2 побеждает в бою!", Toast.LENGTH_SHORT).show();
            wn1.setBackground(getResources().getDrawable(R.drawable.o_win));
            wn2.setBackground(getResources().getDrawable(R.drawable.o_win));
            wn3.setBackground(getResources().getDrawable(R.drawable.o_win));
            p2Count++;
            player2Count.setText("" + p2Count);
            checkWinsCount();
            ifWins = true;
            sleepAfterWinsOrNothing();
        }

        /**
         * Проверка на ньчью
         */

        else if (
                bt1.getText().toString() != "" &&
                bt2.getText().toString() != "" &&
                bt3.getText().toString() != "" &&
                bt4.getText().toString() != "" &&
                bt5.getText().toString() != "" &&
                bt6.getText().toString() != "" &&
                bt7.getText().toString() != "" &&
                bt8.getText().toString() != "" &&
                bt9.getText().toString() != "" &&
                wn1.getText().toString() != wn2.getText().toString() &&
                wn1.getText().toString() != wn3.getText().toString() &&
                !ifWins
                ) {
            Toast.makeText(this, "НИЧЬЯ", Toast.LENGTH_LONG).show();
            sleepAfterWinsOrNothing();
        }
    }

    /**
     * Рандомный ход компьютера. Легкий уровень
     */

    public void randomMoveEasy() {

        ArrayList<View> arrayOfViews = new ArrayList<>();

        arrayOfViews.add(bt1);
        arrayOfViews.add(bt2);
        arrayOfViews.add(bt3);
        arrayOfViews.add(bt4);
        arrayOfViews.add(bt5);
        arrayOfViews.add(bt6);
        arrayOfViews.add(bt7);
        arrayOfViews.add(bt8);
        arrayOfViews.add(bt9);

        r = new Random();

        someRandomInt = r.nextInt(arrayOfViews.size());

        switch (someRandomInt) {
            case 1:
                if      ((bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 2:
                if      ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 3:
                if      ((bt3.getText().toString().equals("") ) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("") ) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("") ) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("") ) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("") ) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("") ) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("") ) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("") ) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 4:
                if      ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 5:
                if      ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 6:
                if      ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 7:
                if      ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 8:
                if      ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                break;

            case 9:
                if      ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                break;

            default:
                if      ((bt9.getText().toString().equals("")) && (makeMove == 2)) {bt9.performClick();makeMove = 1;}
                else if ((bt2.getText().toString().equals("")) && (makeMove == 2)) {bt2.performClick();makeMove = 1;}
                else if ((bt3.getText().toString().equals("")) && (makeMove == 2)) {bt3.performClick();makeMove = 1;}
                else if ((bt4.getText().toString().equals("")) && (makeMove == 2)) {bt4.performClick();makeMove = 1;}
                else if ((bt5.getText().toString().equals("")) && (makeMove == 2)) {bt5.performClick();makeMove = 1;}
                else if ((bt6.getText().toString().equals("")) && (makeMove == 2)) {bt6.performClick();makeMove = 1;}
                else if ((bt7.getText().toString().equals("")) && (makeMove == 2)) {bt7.performClick();makeMove = 1;}
                else if ((bt8.getText().toString().equals("")) && (makeMove == 2)) {bt8.performClick();makeMove = 1;}
                break;

        }

    }

    /**
     * Рандомный ход компьютера. Сложный уровень
     */

    public void randomMoveHard() {

        ArrayList<View> arrayOfViews = new ArrayList<>();

        arrayOfViews.add(bt1);
        arrayOfViews.add(bt2);
        arrayOfViews.add(bt3);
        arrayOfViews.add(bt4);
        arrayOfViews.add(bt5);
        arrayOfViews.add(bt6);
        arrayOfViews.add(bt7);
        arrayOfViews.add(bt8);
        arrayOfViews.add(bt9);

        hardR = new Random();

        hardRandomInt = hardR.nextInt(arrayOfViews.size());

        switch (hardRandomInt) {
            case 1:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 2:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 3:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 4:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 5:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 6:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 7:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 8:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            case 9:
                if      ((bt4.getText().toString().equals("X") && bt7.getText().toString().equals("X") && bt1.getText().toString().equals("")) && (makeMove == 2)) {bt1.performClick();makeMove = 1;}

                break;

            default:

                break;
        }

    }


    /**
     * При победе или ничьей сбрасывает ход
     */

    public void reset() {
        makeMove = 1;
        bt1.setText("");
        bt2.setText("");
        bt3.setText("");
        bt4.setText("");
        bt5.setText("");
        bt6.setText("");
        bt7.setText("");
        bt8.setText("");
        bt9.setText("");
        bt1.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt2.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt3.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt4.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt5.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt6.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt7.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt8.setBackground(getResources().getDrawable(R.drawable.transparent));
        bt9.setBackground(getResources().getDrawable(R.drawable.transparent));
        btEnable();
        ifWins = false;
        colorOfCount();
    }

    /**
     * Кнопки активируются и деактивируются
     */

    public void btEnable() {
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        bt3.setEnabled(true);
        bt4.setEnabled(true);
        bt5.setEnabled(true);
        bt6.setEnabled(true);
        bt7.setEnabled(true);
        bt8.setEnabled(true);
        bt9.setEnabled(true);

        if (gameWithPc) { // если установлен свитч режима с компом
            if (!isPlayerMove) {isPlayerMove = true;}
            else if (isPlayerMove) {isPlayerMove = false;}
        }

        if (!isPlayerMove) { // если переменная в значении не игрок
            if (noSwitchMove) {
                switchMove();
            }

        }
    }

    private void colorOfCount() {
        if (makeMove == 1) {
            player1Count.setTextColor(Color.parseColor("#000000"));
            player2Count.setTextColor(Color.parseColor("#aaaaaa"));
        } else if (makeMove == 2) {
            player2Count.setTextColor(Color.parseColor("#000000"));
            player1Count.setTextColor(Color.parseColor("#aaaaaa"));
        }
    }

    public void btDisable() {
        bt1.setEnabled(false);
        bt2.setEnabled(false);
        bt3.setEnabled(false);
        bt4.setEnabled(false);
        bt5.setEnabled(false);
        bt6.setEnabled(false);
        bt7.setEnabled(false);
        bt8.setEnabled(false);
        bt9.setEnabled(false);
    }

    /**
     * Смена хода
     */

    public void switchMove() {

            makeMove = 2;
            sleepBeforeMove();

        //Log.d("happy", "МОжно делать ход " + isPlayerMove);
    }

    /**
     * Задержка перед ходом компьютера
     */

    public void sleepBeforeMove() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (hardness == 1) {
                            randomMoveEasy();
                        } else if (hardness == 2) {
                            randomMoveHard();
                        }
                    }
                });
            }
        }.start();
    }

    /**
     * Задержка перед сбросом игры после победы или ничьей
     */


    public void sleepAfterWinsOrNothing() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                });
            }
        }.start();
    }


    /**
     * Ходы
     */


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt1:
                colorOfCount();
                if (bt1.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt1.setText("X");
                        bt1.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        // если стоит чек на ИИ, то выполняется рандом
                        checkGame();

                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }

                    } else if (makeMove == 2) {
                        bt1.setText("O");
                        bt1.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }

                break;

            case R.id.bt2:
                colorOfCount();
                if (bt2.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt2.setText("X");
                        bt2.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins  && gameWithPc) {
                            sleepBeforeMove();
                        }

                    } else if (makeMove == 2) {
                        bt2.setText("O");
                        bt2.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();

                    }
                }
                break;

            case R.id.bt3:
                colorOfCount();
                if (bt3.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt3.setText("X");
                        bt3.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }
                    } else if (makeMove == 2) {
                        bt3.setText("O");
                        bt3.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt4:
                colorOfCount();
                if (bt4.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt4.setText("X");
                        bt4.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }
                    } else if (makeMove == 2) {
                        bt4.setText("O");
                        bt4.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt5:
                colorOfCount();
                if (bt5.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt5.setText("X");
                        bt5.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }
                    } else if (makeMove == 2) {
                        bt5.setText("O");
                        bt5.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt6:
                colorOfCount();
                if (bt6.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt6.setText("X");
                        bt6.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }
                    } else if (makeMove == 2) {
                        bt6.setText("O");
                        bt6.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt7:
                colorOfCount();
                if (bt7.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt7.setText("X");
                        bt7.setBackground(getResources().getDrawable(R.drawable.x_min));

                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }

                    } else if (makeMove == 2) {
                        bt7.setText("O");
                        bt7.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt8:
                colorOfCount();
                if (bt8.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt8.setText("X");
                        bt8.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }

                    } else if (makeMove == 2) {
                        bt8.setText("O");
                        bt8.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.bt9:
                colorOfCount();
                if (bt9.getText().toString().equals("")) {
                    if (makeMove == 1) {
                        bt9.setText("X");
                        bt9.setBackground(getResources().getDrawable(R.drawable.x_min));
                        makeMove = 2;
                        checkGame();
                        if (!ifWins && gameWithPc) {
                            sleepBeforeMove();
                        }

                    } else if (makeMove == 2) {
                        bt9.setText("O");
                        bt9.setBackground(getResources().getDrawable(R.drawable.o_min));
                        makeMove = 1;
                        checkGame();
                    }
                }
                break;

            case R.id.btPause:
                reset();
                break;

            case R.id.startScreen:
                noSwitchMove = false;
                backToStart = new Intent(this, MainActivity.class);
                startActivity(backToStart);
                finish();
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (switchMove.isChecked()) {
            noSwitchMove = true;
        }

        if (!switchMove.isChecked()) {
            noSwitchMove = false;
        }

        if (themeWindow.isChecked()) {
            mainWorkSpace.setBackground(getResources().getDrawable(R.drawable.bgpaper));
        }

        if (!themeWindow.isChecked()) {
            mainWorkSpace.setBackground(getResources().getDrawable(R.drawable.bg_workspace));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
