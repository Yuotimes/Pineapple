package com.example.pineapple.ui.flight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pineapple.R;
import com.example.pineapple.ticketadapters.FlightAdapter;
import com.example.pineapple.ticketbeans.FlightInfo;
import com.example.pineapple.databinding.FragmentFlightBinding;

import java.util.ArrayList;
import java.util.List;


public class FlightFragment extends Fragment {

    private FragmentFlightBinding binding;

    private BusTicketFragment busTicketFragment;
    private PlaneTicketFragment planeTicketFragment;
    private TrainTicketFragment trainTicketFragment;

    //碎片管理者，操作碎片显示和隐藏
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private int selectPos = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FlightViewModel homeViewModel =
                new ViewModelProvider(this).get(FlightViewModel.class);
        binding = FragmentFlightBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mFragmentManager = this.getChildFragmentManager();

        List<FlightInfo> flightInfos = new ArrayList<>();
        flightInfos.add(new FlightInfo("汽车", true));
        flightInfos.add(new FlightInfo("飞机", false));
        flightInfos.add(new FlightInfo("火车", false));
        binding.rvFlight.setLayoutManager(new LinearLayoutManager(getContext()));
        FlightAdapter adapter = new FlightAdapter(getContext(), flightInfos);
        binding.rvFlight.setAdapter(adapter);

        adapter.setOnItemClickListener(new FlightAdapter.OnItemClickListener() {
            @Override
            public void onContentClick(FlightInfo info, int position) {
                if (info.isSelect()) return;
                flightInfos.get(selectPos).setSelect(false);
                info.setSelect(true);
                adapter.notifyDataSetChanged();
                selectPos = position;
                setSelectFrame(position);
            }
        });

        setSelectFrame(0);

        return root;
    }

    //用于控制显示哪个碎片
    public void setSelectFrame(int index) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(mFragmentTransaction, false);
        switch (index) {
            case Main.BUS:
                busHandle();
                break;
            case Main.PLANE:
                planeHandler();
                break;
            case Main.TRAIN:
                trainHandler();
                break;
        }
        mFragmentTransaction.commitAllowingStateLoss();
    }

    //bus碎片显示
    private void busHandle() {
        if (busTicketFragment == null) {
            busTicketFragment = new BusTicketFragment();
            if (busTicketFragment.isAdded()) {
                mFragmentTransaction.show(busTicketFragment);
            } else {
                mFragmentTransaction.add(R.id.fl_content, busTicketFragment, "BusTicketFragment");
            }
        } else {
            mFragmentTransaction.show(busTicketFragment);
        }
    }

    //plane碎片显示
    private void planeHandler() {
        if (planeTicketFragment == null) {
            planeTicketFragment = new PlaneTicketFragment();
            if (planeTicketFragment.isAdded()) {
                mFragmentTransaction.show(planeTicketFragment);
            } else {
                mFragmentTransaction.add(R.id.fl_content, planeTicketFragment, "PlaneTicketFragment");
            }
        } else {
            mFragmentTransaction.show(planeTicketFragment);
        }
    }
    //train碎片显示
    private void trainHandler() {
        if (trainTicketFragment == null) {
            trainTicketFragment = new TrainTicketFragment();
            if (trainTicketFragment.isAdded()) {
                mFragmentTransaction.show(trainTicketFragment);
            } else {
                mFragmentTransaction.add(R.id.fl_content, trainTicketFragment, "TrainTicketFragment");
            }
        } else {
            mFragmentTransaction.show(trainTicketFragment);
        }
    }

    //隐藏碎片
    private void hideFragments(FragmentTransaction transaction, boolean isDetach) {
        if (null != busTicketFragment) {
            transaction.hide(busTicketFragment);
        }
        if (null != planeTicketFragment) {
            transaction.hide(planeTicketFragment);
        }
        if (null != trainTicketFragment) {
            transaction.hide(trainTicketFragment);
        }
    }

    //主界面底部导航定义的枚举
    public static class Main {
        private static final int BUS = 0;
        private static final int PLANE = 1;
        private static final int TRAIN = 2;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}