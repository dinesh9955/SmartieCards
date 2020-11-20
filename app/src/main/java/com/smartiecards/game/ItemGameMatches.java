package com.smartiecards.game;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/21/2018.
 */

public class ItemGameMatches implements Serializable{

    String id = "";
    String gsubid = "";
    String gtopicid = "";
    String formula = "";
//    String answer = "";
    String dat = "";
    String questionType = "";

    int isColor = 0;

    public int getIsColor() {
        return isColor;
    }

    public void setIsColor(int isColor) {
        this.isColor = isColor;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGsubid() {
        return gsubid;
    }

    public void setGsubid(String gsubid) {
        this.gsubid = gsubid;
    }

    public String getGtopicid() {
        return gtopicid;
    }

    public void setGtopicid(String gtopicid) {
        this.gtopicid = gtopicid;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

//    public String getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }
}
