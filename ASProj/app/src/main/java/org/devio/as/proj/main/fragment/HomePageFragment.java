package org.devio.as.proj.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;

import org.devio.as.proj.common.ui.component.HiBaseFragment;
import org.devio.as.proj.main.R;

public class HomePageFragment extends HiBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutView.findViewById(R.id.profile).setOnClickListener(v ->
                navigation("/profile/detail")
        );



    }

    void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

}
