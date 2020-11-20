package com.smartiecards.game;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/16/2018.
 */

public class ItemGameShuffle implements Serializable{

    int number = 0;
    String id = "";
    String subject_id = "";
    String topic_id = "";
    String question = "";
    String answer = "";
    String dat = "";
    String star_status = "";
    String fontsize = "";
    String questionimage = "";
    String answerimage = "";
    String answerimage2 = "";

    public String getAnswerimage2() {
        return answerimage2;
    }

    public void setAnswerimage2(String answerimage2) {
        this.answerimage2 = answerimage2;
    }

    public String getQuestionimage() {
        return questionimage;
    }

    public void setQuestionimage(String questionimage) {
        this.questionimage = questionimage;
    }

    public String getAnswerimage() {
        return answerimage;
    }

    public void setAnswerimage(String answerimage) {
        this.answerimage = answerimage;
    }

    public String getFontsize() {
        return fontsize;
    }

    public void setFontsize(String fontsize) {
        this.fontsize = fontsize;
    }

    public String getStar_status() {
        return star_status;
    }

    public void setStar_status(String star_status) {
        this.star_status = star_status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }
}
