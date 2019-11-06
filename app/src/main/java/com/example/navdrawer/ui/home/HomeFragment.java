package com.example.navdrawer.ui.home;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdrawer.HttpConnectionToServer;
import com.example.navdrawer.R;
import com.example.navdrawer.ui.create_vote.create_vote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//kdh


public class HomeFragment extends Fragment {
    EditText nameInput;
    EditText emailInput;

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

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);


        nameInput = rootView.findViewById(R.id.nameInput);
        emailInput = rootView.findViewById(R.id.emailInput);

 //       birthButton = rootView.findViewById(R.id.birthButton);
 //       birthButton.setOnClickListener(new View.OnClickListener() {
 //           public void onClick(View v) {
 //               showDateDialog();
 //           }
 //       });

        Button saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NetworkTask ConnectCreateVoteModel = new NetworkTask();
                String nameStr = nameInput.getText().toString();
                String emailStr = emailInput.getText().toString();
//                String birthStr = birthButton.getText().toString();
                EmailPassWord params =  new EmailPassWord(nameStr,emailStr);
                ConnectCreateVoteModel.execute(params);
                Toast.makeText(getContext(), "이름 : " + nameStr + ", 이메일 : " + emailStr, Toast.LENGTH_SHORT).show();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
 //       setSelectedDate(curDate);



        return rootView;
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
                //Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                System.out.println("server connected false\n");
                //Toast.makeText(getActivity(), "connect failure", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
    }//networktask

}
