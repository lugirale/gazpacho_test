package com.gazpacho.lgramir.gazpacho_test;

import java.util.ArrayList;

public class Singleton {

    private static Singleton mInstance = null;
    //private static Singleton mInstance = new Singleton();

    private String mAnswerSize;
    private String mAnswerSpice;
    private String mAnswerSalt;
    private String mAnswerTakaway;
    private String mAnswerTradicional;
    private ArrayList<String> mIngredients;

    public static Singleton getInstance() {
        if(mInstance == null)
        {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    private Singleton() {
    }

    public String getAnswerSalt() {
        return mAnswerSalt;
    }

    public void setAnswerSalt(String answerSalt) {
        mAnswerSalt = answerSalt;
    }

    public String getAnswerSize() {
        return mAnswerSize;
    }

    public void setAnswerSize(String answerSize) {
        mAnswerSize = answerSize;
    }

    public String getAnswerSpice() {
        return mAnswerSpice;
    }

    public void setAnswerSpice(String answerSpice) {
        mAnswerSpice = answerSpice;
    }

    public String getAnswerTakaway() {
        return mAnswerTakaway;
    }

    public void setAnswerTakaway(String answerTakaway) {
        mAnswerTakaway = answerTakaway;
    }

    public ArrayList<String> getIngredients() {

        return mIngredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        mIngredients = ingredients;
    }

    public String getAnswerTradicional() {
        return mAnswerTradicional;
    }

    public void setAnswerTradicional(String answerTradicional) {
        mAnswerTradicional = answerTradicional;
    }
}
