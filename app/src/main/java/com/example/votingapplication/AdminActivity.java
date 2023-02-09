package com.example.votingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminActivity extends AppCompatActivity {
    TextView textView_candidate1, textView_candidate2, textView_candidate3, textView_candidate4, count_candidate1, count_candidate2, count_candidate3, count_candidate4;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        textView_candidate1 = findViewById(R.id.txt_candidate_1);
        textView_candidate2 = findViewById(R.id.txt_candidate_2);
        textView_candidate3 = findViewById(R.id.txt_candidate_3);
        textView_candidate4 = findViewById(R.id.txt_candidate_4);

        count_candidate1 = findViewById(R.id.cnt_candidate_1);
        count_candidate2 = findViewById(R.id.cnt_candidate_2);
        count_candidate3 = findViewById(R.id.cnt_candidate_3);
        count_candidate4 = findViewById(R.id.cnt_candidate_4);


    }

}