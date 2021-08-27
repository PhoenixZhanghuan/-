package org.devio.as.proj.main.biz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.devio.as.proj.main.route.RouteFlag;

@Route(path = "/profile/vip", extras = RouteFlag.FLAG_VIP)
public class VipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
