package com.example.votingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button buttonVote;
    private RadioButton getCandidate_1, getCandidate_2, getCandidate_3, getCandidate_4;
    private RadioGroup radioGroup;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    Boolean userVoted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        radioGroup = findViewById(R.id.radioGroup);
        getCandidate_1 = findViewById(R.id.radioOne);
        getCandidate_2 = findViewById(R.id.radioTwo);
        getCandidate_3 = findViewById(R.id.radioThree);
        getCandidate_4 = findViewById(R.id.radioFour);
        buttonVote = findViewById(R.id.btn_vote);

        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVoted(mAuth.getUid())){
                    Toast.makeText(MainActivity.this,"You can not vote",Toast.LENGTH_SHORT);
                }
                else {
                    int candidate_id = checkedButton(view);
                    FirebaseUser user = mAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("isVoted", 1);

                    DocumentReference votes = fStore.collection("Votes").document("Q1W5m4wXkiyDiYvUDCRA");
                    votes.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String,Object> vote = new HashMap<>();
                            switch (candidate_id){
                                case 1 : {
                                    int voteCount = Integer.parseInt(documentSnapshot.getString("Candidate_1"));
                                    vote.put("Candidate_1",voteCount+1);
                                    break;
                                }
                                case 2 : {
                                    int voteCount = Integer.parseInt(documentSnapshot.getString("Candidate_2"));
                                    vote.put("Candidate_2",voteCount+1);
                                    break;
                                }
                                case 3 : {
                                    int voteCount = Integer.parseInt(documentSnapshot.getString("Candidate_3"));
                                    vote.put("Candidate_3",voteCount+1);
                                    break;
                                }
                                case 4 : {
                                    int voteCount = Integer.parseInt(documentSnapshot.getString("Candidate_4"));
                                    vote.put("Candidate_4",voteCount+1);
                                    break;
                                }
                            }
                            votes.set(vote);
                        }

                    });
                }
            }
        });
    }

    public boolean isVoted(String uid){
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.getString("isVoted") != "1"){
                    userVoted = true;
                }
                if(documentSnapshot.getString("isVoted") != "0"){
                    userVoted= false;
                }
            }
        });
        return userVoted;
    }
    public int checkedButton(View v){
        if(!getCandidate_1.isChecked() && !getCandidate_2.isChecked() && !getCandidate_3.isChecked() && !getCandidate_4.isChecked()){
            Toast.makeText(this, "Select the candidate",Toast.LENGTH_SHORT);
        }
        if(getCandidate_1.isChecked()){
            return 1;
        }
        else if(getCandidate_2.isChecked()){
            return 2;
        }
        else if(getCandidate_3.isChecked()){
            return 3;
        }
        else{
            return 4;
        }
    }
}