package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.QuestionRequest;
import com.sst.sst_backend.dto.QuestionResponse;
import com.sst.sst_backend.entity.Exam;
import com.sst.sst_backend.entity.Question;
import com.sst.sst_backend.exception.BadRequestException;
import com.sst.sst_backend.exception.ExamNotFoundException;
import com.sst.sst_backend.exception.QuestionNotFoundException;
import com.sst.sst_backend.repository.ExamRepository;
import com.sst.sst_backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            ExamRepository examRepository
    ) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    public QuestionResponse addQuestion(Long examId, QuestionRequest request) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found with id: " + examId));

        validateCorrectAnswer(request.getCorrectAnswer());

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer().toUpperCase());
        question.setMarks(request.getMarks());
        question.setDifficultyLevel(
                request.getDifficultyLevel() != null && !request.getDifficultyLevel().isBlank()
                        ? request.getDifficultyLevel()
                        : "Easy"
        );
        question.setTopic(request.getTopic());
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);

        return mapToResponse(savedQuestion);
    }

    public List<QuestionResponse> getQuestionsByExam(Long examId) {

        if (!examRepository.existsById(examId)) {
            throw new ExamNotFoundException("Exam not found with id: " + examId);
        }

        return questionRepository
                .findByExamIdOrderByIdAsc(examId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public QuestionResponse getQuestionById(Long questionId) {
        Question question = getQuestionEntityById(questionId);
        return mapToResponse(question);
    }

    public QuestionResponse updateQuestion(Long questionId, QuestionRequest request) {

        Question question = getQuestionEntityById(questionId);

        validateCorrectAnswer(request.getCorrectAnswer());

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer().toUpperCase());
        question.setMarks(request.getMarks());
        question.setDifficultyLevel(
                request.getDifficultyLevel() != null && !request.getDifficultyLevel().isBlank()
                        ? request.getDifficultyLevel()
                        : question.getDifficultyLevel()
        );
        question.setTopic(request.getTopic());

        Question updatedQuestion = questionRepository.save(question);

        return mapToResponse(updatedQuestion);
    }

    public void deleteQuestion(Long questionId) {
        Question question = getQuestionEntityById(questionId);
        questionRepository.delete(question);
    }

    private Question getQuestionEntityById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));
    }

    private void validateCorrectAnswer(String correctAnswer) {

        if (correctAnswer == null || correctAnswer.isBlank()) {
            throw new BadRequestException("Correct answer is required");
        }

        String answer = correctAnswer.toUpperCase();

        if (!answer.equals("A") && !answer.equals("B") && !answer.equals("C") && !answer.equals("D")) {
            throw new BadRequestException("Correct answer must be A, B, C or D");
        }
    }

    private QuestionResponse mapToResponse(Question question) {

        Long examId = question.getExam() != null
                ? question.getExam().getId()
                : null;

        String examTitle = question.getExam() != null
                ? question.getExam().getTitle()
                : null;

        return new QuestionResponse(
                question.getId(),
                examId,
                examTitle,
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectAnswer(),
                question.getMarks(),
                question.getDifficultyLevel(),
                question.getTopic()
        );
    }
}