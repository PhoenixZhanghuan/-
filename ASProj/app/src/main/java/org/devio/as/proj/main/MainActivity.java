package org.devio.as.proj.main;

import android.os.Bundle;

import androidx.annotation.NonNull;

import org.devio.as.proj.common.ui.component.HiBaseActivity;
import org.devio.as.proj.main.logic.MainActivityLogic;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider{
    private MainActivityLogic activityLogic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityLogic = new MainActivityLogic(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }
}