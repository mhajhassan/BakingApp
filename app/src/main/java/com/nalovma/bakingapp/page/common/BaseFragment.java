package com.nalovma.bakingapp.page.common;

import android.support.v4.app.Fragment;

import com.nalovma.bakingapp.page.main.MainActivity;

import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder == null) {
            return;
        }
        mUnbinder.unbind();
    }

    protected void setToolbarTitle(String message) {
        if (!(getActivity() instanceof MainActivity)) {
            return;
        }
        ((MainActivity) getActivity()).setToolbarTitle(message);
    }

}
