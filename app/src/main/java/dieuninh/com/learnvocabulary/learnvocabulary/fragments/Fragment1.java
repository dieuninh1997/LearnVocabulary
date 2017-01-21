package dieuninh.com.learnvocabulary.learnvocabulary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dieuninh.com.learnvocabulary.learnvocabulary.R;

/**
 * Created by DieuLinh on 1/2/2017.
 */

public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_layout,container,false);

        return view;
    }
}
