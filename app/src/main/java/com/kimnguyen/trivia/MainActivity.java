package com.kimnguyen.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kimnguyen.trivia.data.QuestionBank;
import com.kimnguyen.trivia.model.Question;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView questionTextView;
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;
    Button answerButton4;

    ImageButton nextButton;
    ImageButton prevButton;

    private int currentQuestionIndex = 0;

    private List<Question> questionList;

    private List<String> answerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionList = new QuestionBank().getQuestions(questionArrayList -> {
            Question currentQuestion = questionArrayList.get(currentQuestionIndex);
            String escapedQuestion = StringEscapeUtils.unescapeHtml4(currentQuestion.getQuestion());
            questionTextView.setText(escapedQuestion);
            Log.d("currentQuestion", "onCreate: " + currentQuestion.toString());
            answerList = new ArrayList<>();
            answerList.add(currentQuestion.getCorrectAnswer());
            answerList.addAll(currentQuestion.getIncorrectAnswers());
            Collections.shuffle(answerList);
            resetButtonColor();
            int numberOfAnswers = answerList.size();
            for (int i = 0; i < numberOfAnswers; i++) {
                switch (i) {
                    case 0:
                        answerButton1.setText(answerList.get(i));
                        break;
                    case 1:
                        answerButton2.setText(answerList.get(i));
                        break;
                    case 2:
                        answerButton3.setText(answerList.get(i));
                        break;
                    case 3:
                        answerButton4.setText(answerList.get(i));
                        break;
                }
            }
        });

        questionTextView = findViewById(R.id.question_textview);
        answerButton1 = findViewById(R.id.button_answer1);
        answerButton2 = findViewById(R.id.button_answer2);
        answerButton3 = findViewById(R.id.button_answer3);
        answerButton4 = findViewById(R.id.button_answer4);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.previous_button);


        nextButton.setOnClickListener(v -> {
            Log.d("questionListSize", "onCreate: size of question list" + questionList.size());
            if (questionList.size() > 0) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                updateAnswer();
                resetButtonColor();
                stopShakeAnimation();
            }
        });

        prevButton.setOnClickListener(v -> {
            if (questionList.size() > 0 && currentQuestionIndex > 0) {
                currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                updateQuestion();
                updateAnswer();
                resetButtonColor();
                stopShakeAnimation();
            }
        });

        answerButton1.setOnClickListener(v -> {
            String chosenAnswer = answerButton1.getText().toString();
            boolean isCorrect = checkAnswer(chosenAnswer);
            if (isCorrect) {
                answerButton1.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_dark_default_color_secondary_variant, getTheme()));
            }
        });

        answerButton2.setOnClickListener(v -> {
            String chosenAnswer = answerButton2.getText().toString();
            boolean isCorrect = checkAnswer(chosenAnswer);
            if (isCorrect) {
                answerButton2.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_dark_default_color_secondary_variant, getTheme()));
            }
        });

        answerButton3.setOnClickListener(v -> {
            String chosenAnswer = answerButton3.getText().toString();
            boolean isCorrect = checkAnswer(chosenAnswer);
            if (isCorrect) {
                answerButton3.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_dark_default_color_secondary_variant, getTheme()));
            }
        });

        answerButton4.setOnClickListener(v -> {
            String chosenAnswer = answerButton4.getText().toString();
            boolean isCorrect = checkAnswer(chosenAnswer);
            if (isCorrect) {
                answerButton4.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_dark_default_color_secondary_variant, getTheme()));
            }
        });

        Log.d("QuestionList", "onCreate: " + questionList);
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getQuestion();
        String escapedQuestion = StringEscapeUtils.unescapeHtml4(question);
        questionTextView.setText(escapedQuestion);
    }

    private void updateAnswer() {
        List<String> answerList = questionList.get(currentQuestionIndex).getIncorrectAnswers();
        answerList.add(questionList.get(currentQuestionIndex).getCorrectAnswer());
        Collections.shuffle(answerList);
        for (int i = 0; i < answerList.size(); i++) {
            switch (i) {
                case 0:
                    answerButton1.setText(answerList.get(i));
                    break;
                case 1:
                    answerButton2.setText(answerList.get(i));
                    break;
                case 2:
                    answerButton3.setText(answerList.get(i));
                    break;
                case 3:
                    answerButton4.setText(answerList.get(i));
                    break;
            }
        }
    }

    private boolean checkAnswer(String answer) {
        String correctAnswer = questionList.get(currentQuestionIndex).getCorrectAnswer();
        if (answer.equals(correctAnswer)) {
            shakeAnimation();
            return true;
        } else {
            return false;
        }
    }

    private void shakeAnimation() {
        // Create shake animation
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        // Shake the textview
        questionTextView.startAnimation(shake);
    }

    private void stopShakeAnimation() {
        questionTextView.clearAnimation();
    }

    private void resetButtonColor() {
        answerButton1.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.button_material_dark, getTheme()));
        answerButton2.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.button_material_dark, getTheme()));
        answerButton3.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.button_material_dark, getTheme()));
        answerButton4.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.button_material_dark, getTheme()));
    }
}