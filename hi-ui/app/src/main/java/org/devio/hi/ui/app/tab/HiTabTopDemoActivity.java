package org.devio.hi.ui.app.tab;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import org.devio.hi.ui.app.R;
import org.devio.hi.ui.tab.top.HiTabTopInfo;
import org.devio.hi.ui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

public class HiTabTopDemoActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_tab_top_demo);

        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for(String s:tabsStr) {
            HiTabTopInfo<?> info = new HiTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener((index, prevInfo, nextInfo) -> {
            Toast.makeText(HiTabTopDemoActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
        });
        hiTabTopLayout.defaultTab(infoList.get(0));
    }
}