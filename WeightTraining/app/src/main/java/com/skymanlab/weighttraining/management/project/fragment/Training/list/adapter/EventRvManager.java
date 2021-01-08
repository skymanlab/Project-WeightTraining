package com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EventRvManager {

    // constant
    private static final String CLASS_NAME = "[PFTLA] EventRvManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String DB_TARGET = "event";

    // constant
    public static final int LINEAR_LAYOUT_MANAGER = 0;
    public static final int GRID_LAYOUT_MANAGER = 1;

    // instance variable
    private Activity activity;
    private RecyclerView recyclerView;
    private int layoutManagerType;
    private int columnCount;

    // instance variable
    private RecyclerView.LayoutManager layoutManager;

    // constructor
    private EventRvManager(Builder builder) {
        this.activity = builder.activity;
        this.recyclerView = builder.recyclerView;
        this.layoutManagerType = builder.layoutManagerType;
        this.columnCount = builder.columnCount;
    }


    /**
     * [method] RecyclerView setting init
     */
    public void setAdapter(RecyclerView.Adapter adapter) throws Exception {

        // [iv/C]RecyclerView.LayoutManager : LinearLayoutManager is used here, this will layout the elements in a similar fashion to the way ListView would layout elements. The RecyclerView.LayoutManager defines how elements are laid out.
        this.layoutManager = getLayoutManager();

        // [method] : layoutManagerType 에 따라 다른 layoutManager setting
        setLayoutManager();

        // [iv/C]RecyclerView : recyclerView 와 adapter 를 연결하기
        this.recyclerView.setAdapter(adapter);

    } // End of method [setAdapter]


    /**
     * layoutManagerType 에 따라 LayoutManager 를 생성하여 반환한다.
     *
     * @return LayoutManager 객체
     */
    private RecyclerView.LayoutManager getLayoutManager() {

        // [switch 1] : layoutManagerType
        switch (layoutManagerType) {

            case LINEAR_LAYOUT_MANAGER:
                return new LinearLayoutManager(activity);
            case GRID_LAYOUT_MANAGER:
                return new GridLayoutManager(activity, columnCount);
            default:
                return null;
        } // [switch 1]

    }

    private void setLayoutManager() {

        // [switch 1] : layoutManagerType
        switch (layoutManagerType) {

            case LINEAR_LAYOUT_MANAGER:
                setLinearLayoutManager();
                break;
            case GRID_LAYOUT_MANAGER:
                setGridLayoutManager();
                break;
            default:
                break;
        } // [switch 1]
    }

    /**
     * [method] Set RecyclerView's Layoutmanager to the one given.
     */
    private void setLinearLayoutManager() {

        // [lv/i]scrollPosition :
        int scrollPosition = 0;

        // [check 1] : If a layout manager has already been set, get current scroll position.
        if (this.recyclerView.getLayoutManager() != null) {

            // [lv/i]scrollPosition : 첫번째 위치의 찾아서 가져오기
            scrollPosition = ((LinearLayoutManager) this.recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

        } // [check 1]

        // [iv/C]RecyclerView :
        this.recyclerView.setLayoutManager(this.layoutManager);

        // [iv/C]RecyclerView : scrollPosition 으로 recyclerView 의 스크롤 이동
        int finalScrollPosition = scrollPosition;
        this.recyclerView.post(new Runnable() {
            @Override
            public void run() {

                recyclerView.scrollToPosition(finalScrollPosition);

            }
        });

    } // End of method [setRecyclerViewLayoutManager]


    private void setGridLayoutManager() {
    }

    public static class Builder {

        // instance variable
        private Activity activity;

        // instance variable
        private RecyclerView recyclerView;

        // instance variable
        private int layoutManagerType;
        private int columnCount;

        // constructor
        public Builder(Activity activity) {
            this.activity = activity;
        }

        public void setLayoutManagerType(int layoutManagerType) {
            this.layoutManagerType = layoutManagerType;
        }

        public void setColumnCount(int columnCount) {
            this.columnCount = columnCount;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public EventRvManager build() {
            return new EventRvManager(this);
        }

    }

}
