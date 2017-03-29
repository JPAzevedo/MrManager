package pt.jpazevedo.mrmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.jpazevedo.mrmanager.R;

/**
 * Created by joaopedroazevedo11 on 28/02/17.
 */

public class FriendsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View dashview = inflater.inflate(R.layout.fragment_friends,container,false);
        return dashview;
    }
}
