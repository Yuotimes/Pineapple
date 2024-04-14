package com.example.pineapple.ui.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pineapple.R;
import com.example.pineapple.shoppingadapters.DetailsAdapter;
import com.example.pineapple.shoppingadapters.OrderAdapter;
import com.example.pineapple.shoppingbeans.Commodity;
import com.example.pineapple.shoppingbeans.Goods;
import com.example.pineapple.databinding.FragmentDetailsBinding;
import com.example.pineapple.shoppingmanagers.CommodityManager;
import com.example.pineapple.ticketutils.QueryBmobCommodity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;


public class DetailsFragment extends Fragment implements DetailsAdapter.MyClickListener {

    private FragmentDetailsBinding binding;
//    private List<CommodityInfo> commodityInfoList;
    private RecyclerView lv_details;
    private TextView tv_price;
    private TextView tv_location;
    private ImageView siv_image;
    private OrderAdapter adapter;
    private List<Commodity> commodityNewList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        initView(view);
        showCommodityList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initView(View view) {
        lv_details = (RecyclerView) view.findViewById(R.id.lv_details);
        tv_price = (TextView) view.findViewById(R.id.details_price);
        tv_location = (TextView) view.findViewById(R.id.details_location);
        siv_image = (ImageView) view.findViewById(R.id.siv_image);

    }

    int finalJ = 0;
    private void showCommodityList() {
        QueryBmobCommodity.queryCommodityBmob(getContext(), new QueryBmobCommodity.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<Commodity> commodityList, Exception e) {
                commodityNewList.clear();
                commodityNewList.addAll(commodityList);
                lv_details.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new OrderAdapter(getContext(), commodityNewList);
                lv_details.setAdapter(adapter);

                adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
                    @Override
                    public void onContentClick(Commodity info, int position) {

                    }

                    @Override
                    public void onContentDelClick(Commodity commodity, int position) {
                        String title = commodity.getTitle();
                        String price = commodity.getPrice();
                        String location = commodity.getLocation();
                        String image = commodity.getImage();
                        View inflate = getLayoutInflater().inflate(R.layout.dialog_image, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setView(inflate);
                        builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finalJ = 0;
//                                SqlManager manager = new SqlManager();
                                CommodityManager commodityManager = new CommodityManager();
//                                manager.deleteCommodityDB(getContext(), title, price, location, image);
                                commodityManager.deleteCommodityBmob(getContext(), commodity.getObjectId(), commodity.getTitle(), commodity.getPrice(), commodity.getLocation(), commodity.getImage(), commodity.getPhone(), commodity.getAddress(), commodity.getGoodsJson());
                                commodityNewList.remove(commodity);
                                adapter.notifyItemRemoved(position);

                                List<Commodity> commodityList = commodity.getGoodsList();

                                HashMap<String, Integer> maps = new HashMap<>();

                                for (int j = 0; j < commodityList.size(); j++) {
                                    Commodity info = commodityList.get(j);
                                    BmobQuery<Goods> query = new BmobQuery<Goods>();
                                    query.addWhereEqualTo("objectId", info.getObjectId());
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
                                                info.setNumber(commodity.getNumber());
                                                if (finalJ == commodityList.size() - 1) {
                                                    for (Commodity info : commodityList) {
                                                        if (maps.containsKey(info.getObjectId())) {
                                                            maps.put(info.getObjectId(), maps.get(info.getObjectId()).intValue() + 1);
                                                        } else {
                                                            maps.put(info.getObjectId(), info.getNumber() + 1);
                                                        }
                                                    }

//                                                Log.d("TAG", maps.size()+";;"+maps.get(commodity.getObjectId()) + ";;" + finalJ);

                                                    List<BmobObject> goodsList = new ArrayList<>();
//                                                    for (Commodity info : commodityList) {
//                                                        info.setNumber(Math.max(0, info.getStock() + 1));
//                                                        goodsList.add(Goods.convert(info));
////                                                        bmobManager.updateGoodsBmob(info);
//                                                    }
                                                    Set<Map.Entry<String, Integer>> entrySet = maps.entrySet();
                                                    for (Map.Entry<String, Integer> entry : entrySet) {
                                                        Goods goods = new Goods();
                                                        goods.setObjectId(entry.getKey());
                                                        goods.setNumber(entry.getValue());
                                                        goodsList.add(goods);
                                                        Log.e("TAG", goods.getObjectId() + ";;" + goods.getNumber());
                                                    }
                                                    new BmobBatch().updateBatch(goodsList).doBatch(new QueryListListener<BatchResult>() {
                                                        @Override
                                                        public void done(List<BatchResult> o, BmobException e) {
                                                            if (e == null) {
                                                                for (int i = 0; i < o.size(); i++) {
                                                                    BatchResult result = o.get(i);
                                                                    BmobException ex = result.getError();
                                                                    if (ex == null) {
                                                                        Log.d("TAG", "第" + i + "个数据批量更新成功：" + result.getUpdatedAt());
                                                                    } else {
                                                                        Log.d("TAG", "第" + i + "个数据批量更新失败：" + ex.getMessage() + "," + ex.getErrorCode());
                                                                    }
                                                                }
                                                            } else {
                                                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    finalJ++;
                                                }
                                            }else{
                                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                            }
                                        }
                                    });
                                }
//                                for (Commodity info : commodityList) {
////                                    CommodityInfo goodsInfo = manager.queryGoodsByTitle(getContext(), info.getTitle());
////                                    if (goodsInfo != null) {
////                                        goodsInfo.setNumber(goodsInfo.getNumber()+1);
////                                        manager.updateGoodsDB(getContext(), goodsInfo);
////                                    }
//                                    BmobQuery<Goods> query = new BmobQuery<Goods>();
//                                    query.addWhereEqualTo("objectId", info.getObjectId());
//                                    //执行查询方法
//                                    query.findObjects(new FindListener<Goods>() {
//                                        @Override
//                                        public void done(List<Goods> object, BmobException e) {
//                                            if(e==null){
//                                                if (object == null || object.isEmpty()) {
//                                                    Toast.makeText(getContext(), "商品数据异常", Toast.LENGTH_SHORT).show();
//                                                    return;
//                                                }
//                                                Commodity commodity = Goods.convert(object.get(0));
//                                                commodity.setNumber(Math.max(0, commodity.getNumber() + 1));
//                                                bmobManager.updateGoodsBmob(commodity);
//                                            }else{
//                                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                                            }
//                                        }
//                                    });
//                                }
                            }
                        });
                        builder.setPositiveButton("再想想", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                });
            }
        });

    }

    @Override
    public void clickListener(View v) {
        int position = (int) v.getTag();


    }
}
