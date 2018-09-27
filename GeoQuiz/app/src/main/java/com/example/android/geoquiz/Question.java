package com.example.android.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswerOrNot;
    private boolean mCorrectOrNot;

    public Question(int textResId,boolean answerTrue,boolean answerOrNot) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswerOrNot=answerOrNot;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswerOrNot() {
        return mAnswerOrNot;
    }

    public void setAnswerOrNot(boolean answerOrNot) {
        mAnswerOrNot = answerOrNot;
    }

    public boolean isCorrectOrNot() {
        return mCorrectOrNot;
    }

    public void setCorrectOrNot(boolean correctOrNot) {
        mCorrectOrNot = correctOrNot;
    }
}
