package com.sst.sst_backend.dto;

public class QuestionResponse {

    private Long id;
    private Long examId;
    private String examTitle;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private Integer marks;
    private String difficultyLevel;
    private String topic;

    public QuestionResponse() {
    }

    public QuestionResponse(Long id, Long examId, String examTitle, String questionText,
                            String optionA, String optionB, String optionC, String optionD,
                            String correctAnswer, Integer marks, String difficultyLevel, String topic) {
        this.id = id;
        this.examId = examId;
        this.examTitle = examTitle;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.marks = marks;
        this.difficultyLevel = difficultyLevel;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public Long getExamId() {
        return examId;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Integer getMarks() {
        return marks;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getTopic() {
        return topic;
    }
}