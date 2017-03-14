package com.example.lcassimiro.thinkfast;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    Button startButton;
    Button sairButton;
    Button playAgainButton;
    Button chooseAnswer;
    TextView resultTextView;
    TextView pointsTextView;
    TextView sumTextView;
    TextView timerTextView;
    TextView levelTxtView;
    EditText editText;
    RelativeLayout gameRelativeLayout;
    CountDownTimer countDownTimer;
    Button btSobre;

    int tempoRestante;

    int level;

    int score = 0;
    int numberOfQuestions = 0;
    int a;
    int b;
    int typeRandom;


    String signalSelect;

    String[] signal = {"+","x","/"};



    public void controlaTempo(int addTempo){
        countDownTimer = new CountDownTimer(addTempo+100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + "s"));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Você teve "+score+" acerto"+ (score>1 ? "s" : "")+" em "+numberOfQuestions+" desafio"+(numberOfQuestions>1 ? "s" : "")+"");
                resultTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                chooseAnswer.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                levelTxtView.setVisibility(View.INVISIBLE);
                pointsTextView.setVisibility(View.INVISIBLE);
                sumTextView.setVisibility(View.INVISIBLE);
                btSobre.setVisibility(View.VISIBLE);
                sairButton.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            }
        }.start();

    }


    public void playAgain(View view){

        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        score = 0;
        level = 0;
        numberOfQuestions = 0;

        playAgainButton.setVisibility(View.INVISIBLE);

        gameRelativeLayout.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        levelTxtView.setVisibility(View.VISIBLE);
        pointsTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        chooseAnswer.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        sairButton.setVisibility(View.INVISIBLE);

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        levelTxtView.setText("");

        btSobre.setVisibility(View.INVISIBLE);

        editText.setText("");
        generateQuestion();

        if(numberOfQuestions==0){
            tempoRestante = 10000;
        }

        controlaTempo(tempoRestante);
    }



    public void generateQuestion(){

        Random random = new Random();

        if(numberOfQuestions%5 == 0){
            level+=10;
        }

        a = random.nextInt(level);
        b = random.nextInt(level);

        typeRandom = random.nextInt(3);

        signalSelect = signal[typeRandom];


        StringBuilder sb = new StringBuilder();

        if(typeRandom==2){
            if(b>a){
                sb.append(Integer.toString(b)+" "+signalSelect+" "+Integer.toString(a));
            }else{
                sb.append(Integer.toString(a)+" "+signalSelect+" "+Integer.toString(b));
            }

        }else{
            sb.append(Integer.toString(a)+" "+signalSelect+" "+Integer.toString(b));
        }

        sumTextView.setText(sb.toString());
        levelTxtView.setText("Level "+level/10);
    }

    public void msg(String msg){
        Toast.makeText(getApplicationContext(),msg.toString(),Toast.LENGTH_SHORT).show();
    }


    public void chooseAnswer(View view){

        if(!String.valueOf(editText.getText()).isEmpty()) {

            String value = editText.getText().toString();

            int currentTime = Integer.parseInt(timerTextView.getText().toString().split("s")[0]);

            if (typeRandom == 0) {
                if (Integer.parseInt(value) == a + b) {
                    score++;
                    msg("Correct!!");
                    currentTime = ((currentTime * 1000) + 5000);
                } else {
                    msg("Wrong!!");
                    currentTime = ((currentTime * 1000) - 2000);
                }
            } else if(typeRandom == 1){
                if (Integer.parseInt(value) == a * b) {
                    score++;
                    currentTime = ((currentTime * 1000) + 5000);
                    msg("Correct!!");
                } else {
                    msg("Wrong!!");
                    currentTime = ((currentTime * 1000) - 2000);
                }
            }else{
                if(b>a){
                    if(a==0){
                        if(Integer.parseInt(value) == a){
                            score++;
                            currentTime = ((currentTime * 1000) + 5000);
                            msg("Correct!!");
                        }
                    }else
                    if(Integer.parseInt(value) == (b / a)){
                        score++;
                        currentTime = ((currentTime * 1000) + 5000);
                        msg("Correct!!");
                    }else{
                        msg("Wrong!!");
                        currentTime = ((currentTime * 1000) - 2000);
                    }
                }else{
                    if(b==0){
                        if(Integer.parseInt(value) == b){
                            score++;
                            currentTime = ((currentTime * 1000) + 5000);
                            msg("Correct!!");
                        }
                    }else
                    if(Integer.parseInt(value) == a / b){
                        score++;
                        currentTime = ((currentTime * 1000) + 5000);
                        msg("Correct!!");
                    }else{
                        msg("Wrong!!");
                        currentTime = ((currentTime * 1000) - 2000);
                    }
                }
            }

            editText.setText("");

            numberOfQuestions++;
            pointsTextView.setText(score + "/" + numberOfQuestions);
            generateQuestion();

            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            controlaTempo(currentTime);
        }

      }

    public void closeApp(View view){
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.playAgain);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        resultTextView = (TextView)findViewById(R.id.resultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);

        playAgainButton = (Button)findViewById(R.id.playAgain);
        chooseAnswer = (Button)findViewById(R.id.chooseAnswer);

        sairButton = (Button)findViewById(R.id.btSair);

        editText = (EditText)findViewById(R.id.editText);
        editText.setFocusable(true);

        levelTxtView = (TextView)findViewById(R.id.txtLevel);

        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);

        btSobre = (Button)findViewById(R.id.btSobre);
        btSobre.setVisibility(View.VISIBLE);

        /*
        Abre a activity numa nova janela
        Intent intent = new Intent(getBaseContext(),SobreActivity.class);
        startActivity(intent);
         */


        //Abre a activity na Popup
        btSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.activity_sobre, null);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);


                //Botão para fechar a popup
                Button btnDismiss = (Button)popupView.findViewById(R.id.btFechar);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});


                popupWindow.showAsDropDown(btSobre, 50, -30);

            }});

        editText.setOnEditorActionListener ( new TextView . OnEditorActionListener () {
             @Override
             public boolean onEditorAction ( TextView v , int actionId , KeyEvent event ) {

                 if ( actionId == EditorInfo . IME_ACTION_SEND ) {

                     chooseAnswer(editText);
                    }
                 //return true - teclado sempre fica ativo
                 return true ;
                 }
             }) ;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
