package com.jiyouliang.fmap.ui.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.jiyouliang.fmap.R;
import com.jiyouliang.fmap.ui.BaseFragment;
import com.jiyouliang.fmap.view.widget.SettingItemView;
import com.jiyouliang.fmap.view.widget.TopTitleView;

/**
 * 用户设置页面
 */
public class UserSettingFragment extends BaseFragment implements View.OnClickListener {

    private static final String KEY_PHONE = "phone";
    private OnFragmentInteractionListener mListener;
    private String mPhone;
    private TopTitleView mTopTitleView;
    private SettingItemView mSivLogout;
    private View mLogoutContainer;
    private SettingItemView mSivDownload;
    private SettingItemView mSivMsgPush;

    public UserSettingFragment() {
        // Required empty public constructor
    }

    public static UserSettingFragment newInstance(String phone) {
        UserSettingFragment fragment = new UserSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHONE, phone);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhone = getArguments().getString(KEY_PHONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_setting, container, false);
        initView(rootView);
        setListener();
        return rootView;
    }

    private void initView(View rootView) {
        mTopTitleView = (TopTitleView) rootView.findViewById(R.id.ttv);
        final ViewGroup llContainer = rootView.findViewById(R.id.ll_container);
        if (llContainer != null && llContainer.getChildCount() > 0) {
            int count = llContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = llContainer.getChildAt(i);
                if (child instanceof SettingItemView) {
                    child.setOnClickListener(this);
                }
            }
        }
        mSivLogout = (SettingItemView) rootView.findViewById(R.id.siv_logout);
        mSivMsgPush = (SettingItemView) rootView.findViewById(R.id.sivMsgPush);
        mSivDownload = (SettingItemView) rootView.findViewById(R.id.sivDownloadNew);
        mLogoutContainer = rootView.findViewById(R.id.ll_login_container);

        mLogoutContainer.setVisibility(TextUtils.isEmpty(mPhone) ? View.GONE : View.VISIBLE);

    }

    private void setListener() {
        mTopTitleView.setOnTopTitleViewClickListener(new TopTitleView.OnTopTitleViewClickListener() {
            @Override
            public void onLeftClick(View v) {
                back();
            }

            @Override
            public void onRightClick(View v) {

            }
        });
        mSivLogout.setOnClickListener(this);
    }

    /**
     * 返回
     */
    private void back() {
        if (mListener != null) {
            Uri.Builder builder = Uri.parse("user://fragment").buildUpon();
            builder.appendQueryParameter("fragment", "back");
            Uri uri = Uri.parse(builder.toString());
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        if (v == mSivMsgPush) {
            mSivMsgPush.setChecked(!mSivMsgPush.isChecked());
        }
        if (v == mSivDownload) {
            mSivDownload.setChecked(!mSivDownload.isChecked());
        }
    }
}
