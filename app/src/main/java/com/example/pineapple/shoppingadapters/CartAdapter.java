package com.example.pineapple.shoppingadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingbeans.Cart;
import com.example.pineapple.smartimageview.SmartImageView;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> cartList;
    private MyCartClickListener cartlistener;

    public interface MyCartClickListener{
        void cartclickListner(View v);
        void cartSelectListener(Cart cart, int position);
    }

    public CartAdapter(Context context, List<Cart> cartList, MyCartClickListener cartlistener) {
        this.context = context;
        this.cartList = cartList;
        this.cartlistener = (MyCartClickListener) cartlistener;
    }
    public void getCartInfoList(List<Cart> cartList){
        this.cartList = cartList;
    }


    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // 如果适配器不为空，继续设置列表项的内容
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(this.context, R.layout.item_cart, null);

            viewHolder = new ViewHolder();

            viewHolder.trip_tip =  convertView.findViewById(R.id.trip_tip);
            viewHolder.cart_title = convertView.findViewById(R.id.cart_title);
            viewHolder.cart_price = convertView.findViewById(R.id.cart_price);
            viewHolder.cart_location = convertView.findViewById(R.id.cart_location);
            viewHolder.siv_image = convertView.findViewById(R.id.siv_image);
            viewHolder.btn_deletecart = convertView.findViewById(R.id.btn_deletecart);
            viewHolder.ck_cart = convertView.findViewById(R.id.ck_cart);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }if (cartList.isEmpty()) {
            viewHolder.trip_tip.setText("hello");
        }

        Cart cart = this.cartList.get(position);

        viewHolder.siv_image.setImageUrl(cart.getImage());
        viewHolder.cart_title.setText(cart.getTitle().trim());
        viewHolder.cart_price.setText("¥"+ cart.getPrice().trim());
        viewHolder.cart_location.setText("发货地"+ cart.getLocation().trim());
        viewHolder.ck_cart.setChecked(cart.isSelected());

        viewHolder.ck_cart.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cart.setSelected(isChecked);
            cartlistener.cartSelectListener(cart, position);
        });

        viewHolder.btn_deletecart.setOnClickListener(v -> cartlistener.cartclickListner(v));
        viewHolder.btn_deletecart.setTag(position);
        return convertView;
    }
    static class ViewHolder{
            TextView cart_title;
            TextView cart_price;
            TextView cart_location;
            SmartImageView siv_image;
            ImageButton btn_deletecart;
            CheckBox ck_cart;
            TextView trip_tip;
    }
}
