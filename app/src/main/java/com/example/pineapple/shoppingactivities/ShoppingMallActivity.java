package com.example.pineapple.shoppingactivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pineapple.R;
import com.example.pineapple.shoppingadapters.MallAdapter;
import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.shoppingmanagers.CartManager;
import com.example.pineapple.shoppingmanagers.CommodityManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;
import com.example.pineapple.ticketutils.bmobaccess.CommodityBmobOP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShoppingMallActivity extends AppCompatActivity {

    private TextView tv_mall;
    private EditText et_phone;
    private EditText et_location;
    private String title;
    private String description;
    private String price;
    private String location;
    private Context context;
    private ListView lv_mall;
    private ImageButton btn_buy;

    private List<Commodity> commodityList = new ArrayList<>();
    private List<Cart> cartList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_mall);
        initView();
        showCommodityList();

        bookOrder();
    }

    public void initView(){
        tv_mall = (TextView) findViewById(R.id.tv_shopping_mall);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_location = (EditText) findViewById(R.id.et_location);
        lv_mall = (ListView) findViewById(R.id.lv_shopping_mall);
        btn_buy = (ImageButton) findViewById(R.id.btn_shopping_buy);
        Intent intent = getIntent();
        Commodity commodity = (Commodity) intent.getSerializableExtra("list");
        cartList = (List<Cart>) intent.getSerializableExtra("cart");
        if (commodity != null) {
            commodityList.add(commodity);
        } else if (cartList != null) {
            for (Cart cart : cartList) {
                Commodity info = new Commodity();
                info.setObjectId(cart.getGoodsId());
                info.setTitle(cart.getTitle());
                info.setImage(cart.getImage());
                info.setLocation(cart.getLocation());
                info.setPrice(cart.getPrice());
                info.setNumber(1);
                commodityList.add(info);
            }
        }
    }
    private void showCommodityList(){
        MallAdapter adapter = new MallAdapter(getApplicationContext(), commodityList);
        lv_mall.setAdapter(adapter);
    }

    //下单
    private void bookOrder() {
        CartManager cartManager = new CartManager();
        CommodityManager commodityManager = new CommodityManager();
        GoodsManager goodsManager = new GoodsManager();
//        BmobManager bmobManager = new BmobManager();
//        SqlManager manager = new SqlManager();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();
                String address = et_location.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ShoppingMallActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(ShoppingMallActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                Commodity info = commodityList.get(0);
                info.setPhone(phone);
                info.setAddress(address);
                info.setGoodsJson(new Gson().toJson(commodityList));
                if (cartList != null) {
                    for (Cart cart : cartList) {
//                        manager.deleteCartDB(MallActivity.this, cart.getTitle(), cart.getPrice(), cart.getLocation(), cart.getImage());
                        cartManager.deleteCartBmob(ShoppingMallActivity.this, cart.getObjectId(), cart.getTitle(), cart.getPrice(), cart.getLocation(), cart.getImage());
                    }
                }
               commodityManager.saveCommodity2Bmob(ShoppingMallActivity.this, info.getTitle(), info.getPrice(), info.getLocation(), info.getImage(), info.getPhone(), info.getAddress(), info.getGoodsJson(), new CommodityBmobOP.OnQueryCompleteListener() {
                    @Override
                    public void onQueryComplete(Exception e) {
                        if (cartList == null) {
                            BmobQuery<Goods> query = new BmobQuery<Goods>();
                            query.addWhereEqualTo("objectId", commodityList.get(0).getObjectId());
                            //执行查询方法
                            query.findObjects(new FindListener<Goods>() {
                                @Override
                                public void done(List<Goods> object, BmobException e) {
                                    if(e==null){
                                        if (object == null || object.isEmpty()) {
                                            Toast.makeText(ShoppingMallActivity.this, "商品数据异常", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Commodity commodity = Goods.convert(object.get(0));
                                        commodity.setNumber(Math.max(0, commodity.getNumber() - 1));
                                        goodsManager.updateGoodsBmob(commodity);
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });
                        }
                        finish();
                    }
                });
//                long l = manager.saveCommodity2DB(MallActivity.this, info);
//                Toast.makeText(MallActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
//                for (Commodity commodity : commodityList) {
//                    CommodityInfo commodityInfo1 = manager.queryGoodsByTitle(MallActivity.this, commodityInfo.getTitle());
//                    if (commodityInfo1 != null) {
//                        commodityInfo1.setNumber(Math.max(0, commodityInfo1.getNumber() - 1));
//                        manager.updateGoodsDB(MallActivity.this, commodityInfo1);
//                    }
//                    commodity.setNumber(Math.max(0, commodity.getNumber() - 1));
//                    bmobManager.updateGoodsBmob(commodity);
//                }
            }
        });
    }
}