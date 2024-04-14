package com.example.pineapple.shoppingactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.shoppingmanagers.BitmapFromURLManager;
import com.example.pineapple.shoppingmanagers.CartManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommodityActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String account = null ;
    private TextView cd_title;
    private TextView cd_detail;
    private ImageView cd_image;
    private TextView cd_price;
    private String title;
    private String description;
    private String price;
    private String location;
    private String image;
    private Button btn_purchase;

    private Commodity commodity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        purchase();
        initView();
    }
    public void initView(){
//        cd_title = (TextView) findViewById(R.id.cd_title);
        cd_detail = (TextView) findViewById(R.id.cd_detail);
        cd_image = (ImageView) findViewById(R.id.cd_image);
        cd_price = (TextView) findViewById(R.id.cd_price);

        Intent intent = getIntent();
        commodity = (Commodity) intent.getSerializableExtra("list");
        title = commodity.getTitle();
        description = commodity.getDescription();
        price = commodity.getPrice();
        location = commodity.getLocation();
        image = commodity.getImage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFromURLManager manager = new BitmapFromURLManager();
                Bitmap bitmap = manager.getBitmapFromURLManager(CommodityActivity.this, commodity.getImage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cd_image.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
//        cd_title.setText(title);
        cd_detail.setText(description);
        cd_price.setText("¥"+price);
    }

    public void purchase() {
        btn_purchase = (Button) findViewById(R.id.btn_purchase);
        this.btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Goods> query = new BmobQuery<Goods>();
                query.addWhereEqualTo("objectId", commodity.getObjectId());
                //执行查询方法
                query.findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> object, BmobException e) {
                        if(e==null){
                            if (object == null || object.isEmpty()) {
                                Toast.makeText(CommodityActivity.this, "商品数据异常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            commodity = Goods.convert(object.get(0));
                            if (commodity.getNumber() < 1) {
                                Toast.makeText(CommodityActivity.this, "商品数量不足！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent intent = new Intent(CommodityActivity.this, ShoppingMallActivity.class);
                            intent.putExtra("list", commodity);
                            System.out.println("========"+commodity);
                            startActivity(intent);
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
//
//                SqlManager manager = new SqlManager();
//                CommodityActivity.this.commodity = manager.queryGoodsByTitle(CommodityActivity.this, CommodityActivity.this.commodity.getTitle());
//                if (CommodityActivity.this.commodity.getNumber() < 1) {
//                    Toast.makeText(CommodityActivity.this, "商品数量不足！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Intent intent = new Intent(CommodityActivity.this, MallActivity.class);
//                Commodity commodity = new Commodity();
//                commodity.setObjectId(commodity.getObjectId());
//                commodity.setTitle(title);
//                commodity.setLocation(location);
//                commodity.setPrice(price);
//                commodity.setImage(image);
//                intent.putExtra("list", commodity);
//                startActivity(intent);
            }
        });

//        SqlManager manager = new SqlManager();
//        long l = manager.saveCommodity2DB(this,title,price,location,image);
//        Toast.makeText(this, "购买成功", Toast.LENGTH_SHORT).show();
//        if (l == -1){
//            Toast.makeText(this, "购买失败", Toast.LENGTH_SHORT).show();
//        }
//        finish();
    }

    public void addintocart(View view) {
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("objectId", commodity.getObjectId());
        //执行查询方法
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> object, BmobException e) {
                if(e==null){
                    if (object == null || object.isEmpty()) {
                        Toast.makeText(CommodityActivity.this, "商品数据异常", Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    SqlManager manager = new SqlManager();
//        commodityInfo = manager.queryGoodsByTitle(CommodityActivity.this, commodityInfo.getTitle());
                    commodity = Goods.convert(object.get(0));
                    if (commodity.getNumber() < 1) {
                        Toast.makeText(CommodityActivity.this, "商品数量不足！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CartManager cartManager = new CartManager();
                    GoodsManager goodsManager = new GoodsManager();
                    cartManager.saveCart2Bmob(CommodityActivity.this, commodity);
                    Toast.makeText(CommodityActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
//                    long l = manager.saveCart2DB(this,title,price,location,image);
//                    if (l == -1) {
//                        Toast.makeText(this, "加入购物车失败", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "加入购物车成功", Toast.LENGTH_SHORT).show();
//                    }
                    commodity.setNumber(commodity.getNumber()-1);
                    goodsManager.updateGoodsBmob(commodity);
//                    manager.updateGoodsDB(this, commodityInfo);
                    finish();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
