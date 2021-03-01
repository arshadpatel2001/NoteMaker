package com.google.notemaker.note;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.notemaker.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText noteTitle;
    EditText noteContent;
    ProgressBar progressBarSave;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore = FirebaseFirestore.getInstance();
        noteContent = findViewById(R.id.addNoteContent);
        noteTitle = findViewById(R.id.addNoteTitle);
        progressBarSave = findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nTitle = noteTitle.getText().toString();
                String nContent = noteContent.getText().toString();

                if(nTitle.isEmpty() || nContent.isEmpty()){
                    Toast.makeText(AddNote.this,"Cannot Save With Empty Field.",Toast.LENGTH_SHORT).show();
                    return;
                }
                //making progressbar visible
                progressBarSave.setVisibility(View.VISIBLE);
                // save notes
                //notes container contains all the notes
                DocumentReference docref = fStore.collection("notes").document(user.getUid()).collection("myNotes").document();
                //map object which will contain note titles
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNote.this,"Note Added",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNote.this,"Error occurred, Please try Again",Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

}
