package com.example.pineapple.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.adapters.MyMessageAdapter;
import com.example.pineapple.beans.MyMesS;
import com.example.pineapple.beans.MyMessage;
import com.example.pineapple.beans.MyUsers;
import com.example.pineapple.interfaces.GetMyMessageListener;
import com.example.pineapple.managers.MyMessageManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SendMessageActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String account;

    private String messageID;
    private TextView send_receivename;
    private EditText ed_inputMsg;
    private Button bt_messages_send;
    private ListView lv_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);
        account = sp.getString("account", "");
        messageID = getIntent().getStringExtra("messageID");
        inItView();
        showMyMessageList();
    }

    public void inItView(){
        ed_inputMsg = (EditText) findViewById(R.id.ed_inputMsg);
        bt_messages_send = (Button) findViewById(R.id.bt_messages_send);
        send_receivename = (TextView) findViewById(R.id.send_receivename);
        lv_send_message = (ListView) findViewById(R.id.lv_send_message);
        BmobQuery<MyMesS> query = new BmobQuery<>();
        query.addWhereEqualTo("sendaccount",account);
        query.addWhereEqualTo("messageID",messageID);
        query.findObjects(new FindListener<MyMesS>() {
            @Override
            public void done(List<MyMesS> list, BmobException e) {
                if (e == null){
                    MyMesS myMesS = list.get(0);
                    String receivename = myMesS.getReceivename();
                    send_receivename.setText(receivename);
                }
            }
        });
        sendMessage();
    }

    public void sendMessage(){
        bt_messages_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = ed_inputMsg.getText().toString().trim();
                if (message.equals("")){
                    Toast.makeText(SendMessageActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                }else{
                    BmobQuery<MyUsers> query = new BmobQuery<>();
                    query.addWhereEqualTo("account",account);
                    query.findObjects(new FindListener<MyUsers>() {
                        @Override
                        public void done(List<MyUsers> list, BmobException e) {
                            if (e == null){
                                MyUsers users = list.get(0);
                                String name = users.getNickname();
                                String head = users.getHead();
                                MyMessage myMessage = new MyMessage();
                                myMessage.setMessageID(messageID);
                                myMessage.setSendaccount(account);
                                myMessage.setSendname(name);
                                myMessage.setSendhead(head);
                                myMessage.setSendtext(message);
                                myMessage.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        ed_inputMsg.setText("");
                                        // 隐藏输入法键盘
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(ed_inputMsg.getWindowToken(), 0);
                                        showMyMessageList();
                                    }
                                });
                            }

                        }
                    });

                }
            }
        });
    }

    public void showMyMessageList(){
        MyMessageManager manager = new MyMessageManager();
        manager.getMyMessageList(messageID, new GetMyMessageListener() {
            @Override
            public void getMyMessageList(List<MyMessage> myMessages) {
                MyMessageAdapter myMessageAdapter = new MyMessageAdapter(SendMessageActivity.this,myMessages,account);
                lv_send_message.setAdapter(myMessageAdapter);
            }
        });
//        BmobQuery<MyMessage> query = new BmobQuery<>();
//        query.addWhereEqualTo("messageID", messageID);
//        // 发起查询请求
//        query.findObjects(new FindListener<MyMessage>() {
//            @Override
//            public void done(List<MyMessage> list, BmobException e) {
//                if (e == null) {
//                    // 查询成功，处理查询结果
//                    MyMessageAdapter myMessageAdapter = new MyMessageAdapter(SendMessageActivity.this,list,account);
//                    lv_send_message.setAdapter(myMessageAdapter);
//                }
//            }
//        });

    }

}