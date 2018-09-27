package com.example.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT=0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia,true,false),
            new Question(R.string.question_oceans,true,false),
            new Question(R.string.question_mideast,false,false),
            new Question(R.string.question_africa,false,false),
            new Question(R.string.question_americas,true,false),
            new Question(R.string.question_asia,true,false)
    };

    private boolean mIsCheater;
    private int mCurrentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState == null) {
            Log.d(TAG,"first onCreate()");
        }

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

       mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
       // int question = mQuestionBank[mCurrentIndex].getTextResId();
       // mQuestionTextView.setText(question);

       mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setAnswerOrNot(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);

                displayScore();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setAnswerOrNot(true);
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);

                displayScore();
            }
        });

        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
               // int question=mQuestionBank[mCurrentIndex].getTextResId();
               // mQuestionTextView.setText(question);
                mIsCheater=false;
                updataQuestion();
            }
        });

        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
               // startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });

        updataQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT) {
            if(data==null) {
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    private void updataQuestion(){
      //  Log.d(TAG,"Updating question text",new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        if(mQuestionBank[mCurrentIndex].isAnswerOrNot()==true) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId=0;

        if(mIsCheater) {
            messageResId = R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setCorrectOrNot(true);
            } else {
                messageResId = R.string.incorrect_toast;
                mQuestionBank[mCurrentIndex].setCorrectOrNot(false);
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    private void displayScore() {
        if(mCurrentIndex==mQuestionBank.length-1) {
            int num_correct = 0;
            for (int i = 0; i < mQuestionBank.length; i++) {
                if(mQuestionBank[i].isCorrectOrNot()) {
                    num_correct++;
                }
            }
            double correct_rate = num_correct*1.0/mQuestionBank.length;
            DecimalFormat df = new DecimalFormat("0.00%");
            String str = df.format(correct_rate);
            Toast.makeText(QuizActivity.this,str,Toast.LENGTH_SHORT).show();
        }
    }
}
