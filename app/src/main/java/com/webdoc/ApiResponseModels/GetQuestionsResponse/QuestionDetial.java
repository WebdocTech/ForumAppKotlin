
package com.webdoc.ApiResponseModels.GetQuestionsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QuestionDetial {

    @SerializedName("questionId")
    @Expose
    private String questionId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("dateofAnswer")
    @Expose
    private String dateofAnswer;
    @SerializedName("dateofQuestion")
    @Expose
    private String dateofQuestion;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateofAnswer() {
        return dateofAnswer;
    }

    public void setDateofAnswer(String dateofAnswer) {
        this.dateofAnswer = dateofAnswer;
    }

    public String getDateofQuestion() {
        return dateofQuestion;
    }

    public void setDateofQuestion(String dateofQuestion) {
        this.dateofQuestion = dateofQuestion;
    }

}
