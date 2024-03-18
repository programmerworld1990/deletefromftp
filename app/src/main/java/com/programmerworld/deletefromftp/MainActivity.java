package com.programmerworld.deletefromftp;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName, editTextPassword;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            editTextPassword = findViewById(R.id.editTextPassword);
            editTextUserName = findViewById(R.id.editTextUserName);
            textViewStatus = findViewById(R.id.textView);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            return insets;
        });
    }

    public void buttonDeleteFile(View view){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("192.168.1.1");
            ftpClient.login(editTextUserName.getText().toString(), editTextPassword.getText().toString());
            ftpClient.changeWorkingDirectory("usb1_1/Files/");
            Boolean booleanStatus = ftpClient.deleteFile("images1.jpg");
            textViewStatus.setText(String.valueOf(booleanStatus));
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}