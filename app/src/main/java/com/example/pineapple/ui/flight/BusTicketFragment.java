package com.example.pineapple.ui.flight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pineapple.ticketactivities.BusActivity;
import com.example.pineapple.ticketadapters.BusAdapter;
import com.example.pineapple.ticketbeans.BusInfo;
import com.example.pineapple.databinding.FragmentBusTicketBinding;

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

public class BusTicketFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public BusTicketFragment() {

    }

    public static BusTicketFragment newInstance(String param1, String param2) {
        BusTicketFragment fragment = new BusTicketFragment();
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

    private FragmentBusTicketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBusTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<BusInfo> busInfoList = parseXml(getActivity(), "Bus.xml");
        binding.lvBusticket.setLayoutManager(new LinearLayoutManager(getContext()));
        BusAdapter busAdapter = new BusAdapter(getContext(), busInfoList);
        binding.lvBusticket.setAdapter(busAdapter);

        busAdapter.setOnItemClickListener(new BusAdapter.OnItemClickListener() {
            @Override
            public void onContentClick(BusInfo busInfo, int position) {
                Intent intent = new Intent(getActivity(), BusActivity.class);
                busInfo = busInfoList.get(position);
                intent.putExtra("buslist", busInfo);
                startActivity(intent);

            }
        });

        return root;
    }

    private List<BusInfo> parseXml(Activity activity, String xml) {
        List<BusInfo> busInfoList = new ArrayList<>();
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
                BusInfo info = new BusInfo();
                info.setTitle(lan.getElementsByTagName("title").item(0).getTextContent());
                info.setTime(lan.getElementsByTagName("time").item(0).getTextContent());
                info.setImage(lan.getElementsByTagName("image").item(0).getTextContent());
                info.setNumber(lan.getElementsByTagName("number").item(0).getTextContent());
                info.setPrice(lan.getElementsByTagName("price").item(0).getTextContent());
                busInfoList.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return busInfoList;
    }
}