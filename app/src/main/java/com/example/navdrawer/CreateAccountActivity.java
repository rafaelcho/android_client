package com.example.navdrawer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import static com.example.navdrawer.LoginActivity.REQUEST_CODE_MENU;

public class CreateAccountActivity extends AppCompatActivity {
    EditText emailInput;
    EditText pwInput;
    EditText pwInput2;

    //kdh
    //object email , password , passing to asynctask
    private static class EmailPassWord{
        String Email;
        String PassWord;

        EmailPassWord(String Email,String PassWord){
            this.Email = Email;
            this.PassWord = PassWord;
        }
    }


//    Button birthButton;

//    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    Date selectedDate;
/*
    public CreateAccountActivity() {

    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        setContentView(R.layout.activity_create_account);

        emailInput = findViewById(R.id.emailInput);
        pwInput = findViewById(R.id.pwInput);
        pwInput2 = findViewById(R.id.pwInput2);

        //       birthButton = rootView.findViewById(R.id.birthButton);
        //       birthButton.setOnClickListener(new View.OnClickListener() {
        //           public void onClick(View v) {
        //               showDateDialog();
        //           }
        //       });

        Button saveButton = findViewById(R.id.loginButton); // 의미상 submitButton이 바르지만, dependency 문제로 보류.
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new NetworkTask();
                String emailStr = emailInput.getText().toString();
                String pwStr = pwInput.getText().toString();
                String pwStr2 = pwInput2.getText().toString();
//                String birthStr = birthButton.getText().toString();
                EmailPassWord params = new EmailPassWord(emailStr, pwStr);
                ConnectCreateVoteModel.execute(params);

                // [yh] 디버깅용 코드.
                //Toast.makeText(getApplicationContext(), "이메일 : " + emailStr + ", 비밀번호 : " + pwStr, Toast.LENGTH_SHORT).show();

                
                // [yh] 비번, 비번확인 일치여부 검사.
                if (!pwStr.equals(pwStr2)) {
                    Toast.makeText(getApplicationContext(), "비밀번호와 비번확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    //startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_MENU);
                }


                Intent resultIntent = new Intent();
                resultIntent.putExtra("email", emailStr);
                resultIntent.putExtra("password", pwStr);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
        //       setSelectedDate(curDate);

        // return rootView;
    }//oncreate

    //thread for http connection, real http conntection is executed on 'HttpConnectionToServer' class
    public class NetworkTask extends AsyncTask<EmailPassWord,Void,Boolean> {
        private String url;
        private ContentValues values;

        public NetworkTask(){
            ;
        }

        @Override
        protected Boolean doInBackground(EmailPassWord... params) {
            HttpConnectionToServer ConnectCreateVoteModel = new HttpConnectionToServer();
            /*
            String email = "pineleaf1216@gmail.com";
            String pass = "12345";
            */
            String email = params[0].Email;
            String pass = params[0].PassWord;



            if(ConnectCreateVoteModel.CreateAccount(email,pass)) {
                System.out.println("server connected true\n");
                // [yh] 토스트 실행시 에러.
                //Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                // [yh] 토스트 실행시 에러.
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
    }//networktask

}
