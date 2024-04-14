package com.example.pineapple.ui.flight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pineapple.ticketactivities.PlaneActivity;
import com.example.pineapple.ticketadapters.PlaneAdapter;
import com.example.pineapple.ticketbeans.PlaneInfo;
import com.example.pineapple.databinding.FragmentPlaneTicketBinding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class PlaneTicketFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String EXTRA_PLANE_INFO = "planeinfo";



    private String mParam1;
    private String mParam2;
    private RecyclerView lv_planeticket;
    private PlaneAdapter adapter;

    public PlaneTicketFragment() {
    }
    public static PlaneTicketFragment newInstance(String param1, String param2) {
        PlaneTicketFragment fragment = new PlaneTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentPlaneTicketBinding binding;


    private Handler handler = new Handler(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaneTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        List<PlaneInfo> planeInfoList = parseXml(getActivity(), "Plane.xml");
        binding.lvPlaneticket.setLayoutManager(new LinearLayoutManager(getContext()));
        PlaneAdapter planeAdapter = new PlaneAdapter(getContext(), planeInfoList);
        binding.lvPlaneticket.setAdapter(planeAdapter);

        planeAdapter.setOnItemClickListener(new PlaneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaneInfo planeInfo, int position) {
                Intent intent = new Intent(getActivity(), PlaneActivity.class);
                planeInfo = planeInfoList.get(position);
                intent.putExtra("planelist",planeInfo);
                startActivity(intent);
            }
        });
        return root;
    }

    private List<PlaneInfo> parseXml(Activity activity, String xml) {
        List<PlaneInfo> planeInfoList = new ArrayList<>();
        try {
            //传入文件名：language.xml；用来获取流
            InputStream is = activity.getAssets().open(xml);
            //首先创造：DocumentBuilderFactory对象
            DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
            //获取：DocumentBuilder对象
            DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
            //将数据源转换成：document 对象
            Document document = dBuilder.parse(is);
            //获取根元素
            Element element = (Element) document.getDocumentElement();
            //获取子对象的数值 读取lan标签的内容
            NodeList nodeList = element.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                //获取对应的对象
                Element lan = (Element) nodeList.item(i);
                PlaneInfo info = new PlaneInfo();
                info.setTitle(lan.getElementsByTagName("title").item(0).getTextContent());
                info.setTime(lan.getElementsByTagName("time").item(0).getTextContent());
                info.setImage(lan.getElementsByTagName("image").item(0).getTextContent());
                info.setNumber(lan.getElementsByTagName("number").item(0).getTextContent());
                info.setPrice(lan.getElementsByTagName("price").item(0).getTextContent());
                planeInfoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return planeInfoList;
    }


}