package com.smartiecards.game;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/23/2018.
 */

public class ItemAnswers implements Serializable {


    String answer_id = "";
    String question_number = "";
    String is_correct = "";
    String answer = "";


    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public String getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(String question_number) {
        this.question_number = question_number;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
