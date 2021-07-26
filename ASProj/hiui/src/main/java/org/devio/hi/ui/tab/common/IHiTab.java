package org.devio.hi.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * HiTab对外接口
 * @param <D>
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D>{
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     * @param height 高度
     */
    void resetHeight(@Px int height);
}
