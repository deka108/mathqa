package dekauliya.fyp.mathqa;

import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Models.Question;
import io.github.kexanie.library.MathView;

@EActivity(R.layout.question_detail)
public class Blank2ActivityTesting extends AppCompatActivity {

    @Extra("questionExtra")
    Question question;

    @ViewById(R.id.qd_question_title) TextView questionTitle;
    @ViewById(R.id.qd_question_latex) MathView questionContent;
    @ViewById(R.id.qd_question_ratingbar) RatingBar difficulty;
    @ViewById(R.id.qd_question_concept) TextView questionConcept;

    @AfterViews
    void setView(){
        questionTitle.setText("Question #" + question.getId());
        questionContent.setText(question.getContent());
        difficulty.setNumStars(5);
//        difficulty.setMax(10);
//        difficulty.setStepSize(0.5f);
        difficulty.setRating(Float.parseFloat(question.getDifficulty_level()));
    }
}
