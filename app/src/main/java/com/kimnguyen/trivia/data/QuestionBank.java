package com.kimnguyen.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kimnguyen.trivia.controller.AppController;
import com.kimnguyen.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://opentdb.com/api.php?amount=10&type=multiple";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                // Now, get the JSON array named "results" from the main JSON object
                JSONArray resultsArray = response.getJSONArray("results");

                // Loop through the "results" array
                for (int i = 0; i < resultsArray.length(); i++) {
                    // Get each JSON object within the array
                    JSONObject questionObject = resultsArray.getJSONObject(i);

                    // Convert each JSON object to string and log it
                    String jsonString = questionObject.toString();
                    //Log.d("JSON", "Question " + (i + 1) + ": " + jsonString);

                    // You can further extract specific fields from questionObject here
                    // and add them to your questionArrayList
                    Question question = new Question();
                    question.setQuestion(questionObject.getString("question"));
                    question.setCorrectAnswer(questionObject.getString("correct_answer"));
                    JSONArray incorrectAnswersArray = questionObject.getJSONArray("incorrect_answers");
                    ArrayList<String> incorrectAnswers = new ArrayList<>();
                    for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                        incorrectAnswers.add(incorrectAnswersArray.getString(j));
                    }
                    question.setIncorrectAnswers(incorrectAnswers);
                    questionArrayList.add(question);
                    Log.d("QUESTION", "getQuestions: " + question.getIncorrectAnswers());
                }
                if (null != callBack) callBack.processFinished(questionArrayList);
            } catch (JSONException e) {
                Log.e("QuestionBankError", "Error in parsing JSON", e);
            }
        }, error -> {
            Log.e("QuestionBankError", "Error in request: " + error.getMessage());
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "jsonObjectRequest");
        // You might want to return the populated questionArrayList instead
        return questionArrayList;
    }
}

