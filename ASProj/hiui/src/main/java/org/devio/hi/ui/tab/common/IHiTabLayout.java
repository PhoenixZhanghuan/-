package org.devio.hi.ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public interface IHiTabLayout<Tab extends ViewGroup, D>{
    Tab findTab(@NonNull D data);
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);
    void defaultTab(@NonNull D defaultInfo);
    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChanged(int index, @Nullable D prevInfo, @NonNull D nextInfo);
    }
}
