package com.intern.aqumulate.aqumulate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
public class NavigationDrawerFragment extends Fragment {
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    public static final String PREF_FILE_NAME="testpref";
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private VivzAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }

    public static String readToPreferences(Context context, String preferenceName, String defaultValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
         return sharedPreferences.getString(preferenceName,defaultValue);

    }
    private static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_menu_gallery, R.drawable.ic_menu_camera, R.drawable.ic_menu_send,
                R.drawable.ic_menu_gallery, R.drawable.ic_menu_manage};
        String[] titles = {"Dashboard", "Portfolio", "Transaction", "AddAccounts", "Help"};
        for (int i = 0; i < 5; i++) {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new VivzAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch(position) {
                    case 0:
                        startActivity(new Intent(getActivity(),MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),Portfolio.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(),Transaction.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(),AddAccount.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(),Help.class));
                        break;

                }

            }

            @Override
            public void onLongClick(View view, int potition) {

            }
        }));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }



        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int potition);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        public GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null)
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            });

        }

        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
                clickListener.onClick(child, rv.getChildPosition(child));
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
