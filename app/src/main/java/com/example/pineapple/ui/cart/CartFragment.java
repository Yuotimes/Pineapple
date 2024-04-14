package com.example.pineapple.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pineapple.R;
import com.example.pineapple.shoppingactivities.ShoppingMallActivity;
import com.example.pineapple.shoppingadapters.CartAdapter;
import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.databinding.FragmentCartBinding;
import com.example.pineapple.shoppingmanagers.CartManager;
import com.example.pineapple.shoppingmanagers.GoodsManager;
import com.example.pineapple.ticketutils.QueryBmobCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class CartFragment extends Fragment implements CartAdapter.MyCartClickListener {

    private FragmentCartBinding binding;
    private List<Cart> cartList;
    private TextView cart_title;
    private TextView cart_price;
    private ListView lv_cart;
    private TextView tv_total_price;
    private CheckBox cb_all;
    private ImageButton btn_buy;


    private CartAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        showCartList();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initView(View view) {
        lv_cart = (ListView) view.findViewById(R.id.lv_cart);
        cart_price = (TextView) view.findViewById(R.id.cart_price);
        cart_title = (TextView) view.findViewById(R.id.cart_title);
        cb_all = (CheckBox) view.findViewById(R.id.cb_all);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        btn_buy = (ImageButton) view.findViewById(R.id.btn_buy);

        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cb_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cartList.isEmpty()) return;
                        for (Cart cart : cartList) {
                            cart.setSelected(b);
                        }
                        adapter.notifyDataSetChanged();
                        getCartInfoList(cartList);
                    }
                });
            }
        });
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectCount == 0) {
                    return;
                }

                List<Cart> select = new ArrayList<>();
                for (Cart cart : cartList) {
                    if (cart.isSelected()) {
                        select.add(cart);
                    }
                }
                Intent intent = new Intent(getContext(), ShoppingMallActivity.class);
                intent.putExtra("cart", (Serializable) select);
                startActivity(intent);
            }
        });
    }


    private void showCartList() {
//        cartInfoList = QueryCartDB.queryCartDB(getContext());

//        adapter = new CartAdapter(getContext(), cartInfoList, (CartAdapter.MyCartClickListener) this);
//        lv_cart.setAdapter(adapter);
        QueryBmobCart.queryCartBmob(new QueryBmobCart.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<Cart> carts, Exception e) {
                 cartList = new ArrayList<>();
                 cartList.addAll(carts);
                 adapter = new CartAdapter(getContext(), cartList, CartFragment.this);
                 lv_cart.setAdapter(adapter);
            }
        },getContext());
    }


    @Override
    public void cartclickListner(View v) {
        int position = (int) v.getTag();
        Cart cart = cartList.get(position);
        String image = cart.getImage();
        String title = cart.getTitle();
        String location = cart.getLocation();
        String price = cart.getPrice();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_image, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CartManager cartManager = new CartManager();
                GoodsManager goodsManager = new GoodsManager();
                cartManager.deleteCartBmob(getContext(), cart.getObjectId(), cart.getTitle(), cart.getPrice(), cart.getLocation(), cart.getImage());
//                SqlManager manager = new SqlManager();
//                manager.deleteCartDB(getContext(), title, price, location, image);
                cartList.remove(cart);
                System.out.println("-----"+ cart);
                adapter.notifyDataSetChanged();

                BmobQuery<Goods> query = new BmobQuery<Goods>();
                query.addWhereEqualTo("objectId", cart.getGoodsId());
                //执行查询方法
                query.findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> object, BmobException e) {
                        if(e==null){
                            if (object == null || object.isEmpty()) {
                                Toast.makeText(getContext(), "商品数据异常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Commodity commodity = Goods.convert(object.get(0));
                            commodity.setNumber(Math.max(0, commodity.getNumber() + 1));
                            goodsManager.updateGoodsBmob(commodity);
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });
        builder.setPositiveButton("再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private int selectCount = 0;
    private double totalPrice = 0;
    @Override
    public void cartSelectListener(Cart cart, int position) {
        if (cart.isSelected()) {
            selectCount = Math.min(cartList.size(), ++selectCount);
            totalPrice += Double.parseDouble(cart.getPrice());
        } else {
            selectCount = Math.max(0, --selectCount);
            totalPrice = Math.max(0, totalPrice - Double.parseDouble(cart.getPrice()));
        }
        if (selectCount == cartList.size()&& cartList.size()!=0) {
            cb_all.setChecked(true);
        } else {
            cb_all.setChecked(false);
        }
        String totalPriceString = String.format(Locale.getDefault(), "%.2f", totalPrice);
        tv_total_price.setText("¥" + totalPriceString);
    }

    public void getCartInfoList(List<Cart> cartList){
        this.cartList = cartList;
        calculateAndDisplaySelectedTotalPrice(tv_total_price);
    }


    public void calculateAndDisplaySelectedTotalPrice(TextView tv_total_price) {
        totalPrice = 0.00;
        selectCount = 0;
        for (Cart cart : cartList) {
            if (cart.isSelected()) {
                try {
                    double price = Double.parseDouble(cart.getPrice());
                    totalPrice += price;
                    selectCount = Math.min(cartList.size(), ++selectCount);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        String totalPriceString = String.format(Locale.getDefault(), "%.2f", totalPrice);
        tv_total_price.setText("¥" + totalPriceString);
        if (selectCount == cartList.size() && cartList.size()!=0) {
            cb_all.setChecked(true);
        } else {
            cb_all.setChecked(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showCartList();
    }
}