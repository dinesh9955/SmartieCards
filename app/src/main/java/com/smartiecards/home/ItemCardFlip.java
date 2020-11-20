package com.smartiecards.home;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/8/2018.
 */

public class ItemCardFlip implements Serializable{

    int number = 0;
    String id = "";
    String cardid = "";
    String formula = "";
    String answer = "";
    String dat = "";
    String subjectid = "";
    String star_status = "";
    String fontsize = "";
    String formulaimage = "";
    String answerimage = "";
    String answerimage2 = "";

    public String getAnswerimage2() {
        return answerimage2;
    }

    public void setAnswerimage2(String answerimage2) {
        this.answerimage2 = answerimage2;
    }

    public String getFormulaimage() {
        return formulaimage;
    }

    public void setFormulaimage(String formulaimage) {
        this.formulaimage = formulaimage;
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

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
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

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
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
