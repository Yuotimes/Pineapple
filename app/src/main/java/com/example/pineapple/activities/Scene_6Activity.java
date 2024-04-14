package com.example.pineapple.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.example.pineapple.R;
import com.example.pineapple.beans.MyScenes;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Scene_6Activity extends AppCompatActivity {
    private RelativeLayout scene_locate;
    private RelativeLayout scene_phone;
    private Button bt_6_set_1;
    private Button bt_6_set_2;
    private Button bt_6_set_3;
    private Button bt_6_set_4;
    private Button bt_6_set_5;
    private SharedPreferences sp;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene6);
        initView();
        click();
    }
    public void initView(){
        bt_6_set_1 = (Button) findViewById(R.id.bt_6_set_1);
        bt_6_set_2 = (Button) findViewById(R.id.bt_6_set_2);
        bt_6_set_3 = (Button) findViewById(R.id.bt_6_set_3);
        bt_6_set_4 = (Button) findViewById(R.id.bt_6_set_4);
        bt_6_set_5 = (Button) findViewById(R.id.bt_6_set_5);
        scene_locate = (RelativeLayout) findViewById(R.id.scene_6_locate);
        scene_phone = (RelativeLayout) findViewById(R.id.scene_6_phone);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        account = sp.getString("account", "");
    }
    public void click(){
        scene_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Scene_6Activity.this , MapActivity_6.class);
                startActivity(intent);
            }
        });
        scene_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"0411-82816666"));
                startActivity(intent);
            }
        });

        bt_6_set_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Scene_6Activity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Scene_6Activity.this);
                            builder.setMessage("确定要订购"+"盛世东方"+year+"年"+(month+1)+"月"+day+"日"+"成人票？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String time = year+"年"+(month+1)+"月"+day+"日".toString().trim();
                                    saveScenes(view,time,"盛世东方","成人票¥49");
                                }
                            }).setCancelable(false).show();
                        }
                    });
                    dialog.show();
                }
            }
        });

        bt_6_set_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Scene_6Activity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Scene_6Activity.this);
                            builder.setMessage("确定要订购"+"盛世东方"+year+"年"+(month+1)+"月"+day+"日"+"的亲子票？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String time = year+"年"+(month+1)+"月"+day+"日".toString().trim();
                                    saveScenes(view,time,"盛世东方","亲子票¥79");
                                }
                            }).setCancelable(false).show();
                        }
                    });
                    dialog.show();
                }
            }
        });

        bt_6_set_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Scene_6Activity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Scene_6Activity.this);
                            builder.setMessage("确定要订购"+"盛世东方"+year+"年"+(month+1)+"月"+day+"日"+"的门票+足疗？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String time = year+"年"+(month+1)+"月"+day+"日".toString().trim();
                                    saveScenes(view,time,"盛世东方","门票+足疗¥119");
                                }
                            }).setCancelable(false).show();
                        }
                    });
                    dialog.show();
                }
            }
        });

        bt_6_set_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Scene_6Activity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Scene_6Activity.this);
                            builder.setMessage("确定要订购"+"盛世东方"+year+"年"+(month+1)+"月"+day+"日"+"的门票+项背舒缓？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String time = year+"年"+(month+1)+"月"+day+"日".toString().trim();
                                    saveScenes(view,time,"盛世东方","门票+项背舒缓¥199");
                                }
                            }).setCancelable(false).show();
                        }
                    });
                    dialog.show();
                }
            }
        });

        bt_6_set_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(Scene_6Activity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Scene_6Activity.this);
                            builder.setMessage("确定要订购"+"盛世东方"+year+"年"+(month+1)+"月"+day+"日"+"的门票+搓澡+奶浴？").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String time = year+"年"+(month+1)+"月"+day+"日".toString().trim();
                                    saveScenes(view,time,"盛世东方","门票+搓澡+奶浴¥199");
                                }
                            }).setCancelable(false).show();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    public void saveScenes(View view,String time,String scene,String ticket){
        MyScenes myScenes = new MyScenes();
        myScenes.setAccount(account);
        myScenes.setScene(scene);
        myScenes.setDate(time);
        myScenes.setTicket(ticket);
        myScenes.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Snackbar.make(view, "购票成功", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "购票失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}