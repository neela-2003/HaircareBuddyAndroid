package com.saveetha.haircarebuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.HairQuizResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HairDensity extends AppCompatActivity {

    private TextView questionText;
    private Button lightBtn, mediumBtn, thickBtn, varyingBtn, nextBtn;

    private int questionIndex = 0;
    private String selectedAnswer = "";
    private final ArrayList<String> allAnswers = new ArrayList<>();

    private final List<QuestionModel> quizData = Arrays.asList(
            new QuestionModel("What’s your hair type?", new String[]{"Straight Hair", "Curly Hair", "Wavy Hair", "Coily Hair"}),
            new QuestionModel("What is the thickness of your hair?", new String[]{"Thin", "Normal", "Thick", "Varying Thickness"}),
            new QuestionModel("How would you describe your hair damage?", new String[]{"None", "Dull hair", "Frizzy hair", "Split hair"}),
            new QuestionModel("How would you describe your scalp?", new String[]{"Oily", "Dry", "Normal", "Sensitive"}),
            new QuestionModel("Do you experience any scalp issues?", new String[]{"Dandruff", "Itching", "Redness", "Fungal"}),
            new QuestionModel("How long after hair wash does it start to feel oily?", new String[]{"Within 24hrs", "2-3 days", "More than 4 days", "More than 1 week"}),
            new QuestionModel("Describe your dandruff", new String[]{"No", "Yes, mild that comes & goes", "I have psoriasis", "Yes, I have dandruff"}),
            new QuestionModel("Have you experienced any of the below in the last 1 year?", new String[]{"None", "Illness like Dengue, Typhoid, Covid", "Surgery or heavy medication", "Heavy weight loss or gain"}),
            new QuestionModel("Are you going through any of the below?", new String[]{"None", "Thyroid", "PCOS", "Other hormonal issues"}),
            new QuestionModel("How well do you sleep?", new String[]{"Peacefully 6–8 hrs", "Disturbed sleep", "Wake at least once", "Fall asleep post midnight"}),
            new QuestionModel("How stressed are you?", new String[]{"Low", "Not at all", "Moderate", "High"}),
            new QuestionModel("If hair color is done?", new String[]{"6 months ago", "Never", "1 month ago", "1 year ago"}),
            new QuestionModel("If shampoo in the morning, how does scalp feel by night?", new String[]{"Oily", "Dry", "Both", "Neither"}),
            new QuestionModel("What is your most important goal currently?", new String[]{"Control Hairfall", "Regrow Hair", "Improve Hair Quality", "Other"}),
            new QuestionModel("Describe your energy levels", new String[]{"Always high", "Very low in afternoon", "Low by night", "Always low"}),
            new QuestionModel("Are you suffering from any of these medical conditions?", new String[]{"None", "Blood Pressure", "Cardio Issues", "Liver cirrhosis or deranged"})
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_density);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // UI elements
        questionText = findViewById(R.id.questionText);
        lightBtn = findViewById(R.id.lightBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        thickBtn = findViewById(R.id.thickBtn);
        varyingBtn = findViewById(R.id.varyingBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // Back arrow
        ImageButton backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(HairDensity.this, MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        setQuestion();

        View.OnClickListener optionClickListener = view -> {
            resetOptionColors();
            selectedAnswer = ((Button) view).getText().toString();
            view.setBackgroundResource(R.drawable.rectangle_button); // highlight
        };

        lightBtn.setOnClickListener(optionClickListener);
        mediumBtn.setOnClickListener(optionClickListener);
        thickBtn.setOnClickListener(optionClickListener);
        varyingBtn.setOnClickListener(optionClickListener);

        nextBtn.setOnClickListener(view -> {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                return;
            }

            allAnswers.add(selectedAnswer);
            selectedAnswer = "";

            if (questionIndex < quizData.size() - 1) {
                questionIndex++;
                setQuestion();
                resetOptionColors();
                if (questionIndex == quizData.size() - 1) {
                    nextBtn.setText("Submit");
                }
            } else {
                nextBtn.setEnabled(false);
                nextBtn.setText("Submitting...");
                submitAnswersToServer();
            }
        });
    }

    private void setQuestion() {
        QuestionModel current = quizData.get(questionIndex);
        questionText.setText(current.question);
        lightBtn.setText(current.options[0]);
        mediumBtn.setText(current.options[1]);
        thickBtn.setText(current.options[2]);
        varyingBtn.setText(current.options[3]);

        nextBtn.setText(questionIndex == quizData.size() - 1 ? "Submit" : "Next");
    }

    private void resetOptionColors() {
        lightBtn.setBackgroundResource(R.drawable.round_button);
        mediumBtn.setBackgroundResource(R.drawable.round_button);
        thickBtn.setBackgroundResource(R.drawable.round_button);
        varyingBtn.setBackgroundResource(R.drawable.round_button);
    }

    private void submitAnswersToServer() {
        SharedPreferences preferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "");

        if (email.isEmpty()) {
            Toast.makeText(this, "Email not found!", Toast.LENGTH_SHORT).show();
            restartQuiz();
            return;
        }

        if (allAnswers.size() != 16) {
            Toast.makeText(this, "Please answer all 16 questions.", Toast.LENGTH_SHORT).show();
            restartQuiz();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<HairQuizResponse> call = apiService.submitHairQuiz(
                email,
                allAnswers.get(0), allAnswers.get(1), allAnswers.get(2), allAnswers.get(3),
                allAnswers.get(4), allAnswers.get(5), allAnswers.get(6), allAnswers.get(7),
                allAnswers.get(8), allAnswers.get(9), allAnswers.get(10), allAnswers.get(11),
                allAnswers.get(12), allAnswers.get(13), allAnswers.get(14), allAnswers.get(15)
        );

        call.enqueue(new Callback<HairQuizResponse>() {
            @Override
            public void onResponse(Call<HairQuizResponse> call, Response<HairQuizResponse> response) {
                nextBtn.setEnabled(true);
                nextBtn.setText("Submit");

                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    String aiResponse = response.body().getAi_response();

                    if (aiResponse != null && !aiResponse.trim().isEmpty()) {
                        Intent intent = new Intent(HairDensity.this, UserContent.class);
                        intent.putExtra("ai_response", aiResponse);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(HairDensity.this, "AI response empty. Restarting quiz.", Toast.LENGTH_LONG).show();
                        restartQuiz();
                    }
                } else {
                    Toast.makeText(HairDensity.this, "Submission failed. Restarting quiz.", Toast.LENGTH_LONG).show();
                    restartQuiz();
                }
            }

            @Override
            public void onFailure(Call<HairQuizResponse> call, Throwable t) {
                nextBtn.setEnabled(true);
                nextBtn.setText("Submit");
                Toast.makeText(HairDensity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                restartQuiz();
            }
        });
    }

    private void restartQuiz() {
        questionIndex = 0;
        selectedAnswer = "";
        allAnswers.clear();
        setQuestion();
        resetOptionColors();
        nextBtn.setText("Next");
        nextBtn.setEnabled(true);
    }

    static class QuestionModel {
        String question;
        String[] options;

        QuestionModel(String question, String[] options) {
            this.question = question;
            this.options = options;
        }
    }
}
