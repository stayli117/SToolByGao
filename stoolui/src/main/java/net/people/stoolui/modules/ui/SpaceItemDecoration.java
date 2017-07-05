package net.people.stoolui.modules.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * Title: SpaceItemDecoration <br>
 * Description: GridLayoutManager间距<br>
 * Copyright (c) 传化物流版权所有 2017 <br>
 * Created DateTime: 2017-1-10 9:18
 * Created by Wentao.Shi.
 */
public class SpaceItemDecoration extends ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;
        outRect.left=space;
//        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) %3==2) {
            outRect.right = space;
        }
    }
}
