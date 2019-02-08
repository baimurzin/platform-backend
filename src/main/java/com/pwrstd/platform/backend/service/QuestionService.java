package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.model.Point;
import com.pwrstd.platform.backend.model.Question;
import com.pwrstd.platform.backend.model.Step;
import com.pwrstd.platform.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getQuestionByStep(Step step){
        return questionRepository.findByStep(step);
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public boolean isRightAnswer(Question question, Point point){
        return question.getPoints()
                .stream()
                .filter(Point::getIsRight)
                .findAny()
                .map(p -> p.getId().equals(point.getId()))
                .orElse(false);
    }

}
