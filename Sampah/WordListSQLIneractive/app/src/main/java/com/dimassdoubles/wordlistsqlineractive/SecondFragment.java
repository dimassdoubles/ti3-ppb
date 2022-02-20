package com.dimassdoubles.wordlistsqlineractive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.dimassdoubles.wordlistsqlineractive.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    private static final String TAG = SecondFragment.class.getSimpleName();
    private static final int NO_ID = -99;
    private static final String NO_WORD = "";

    private EditText mEditWordView;

    // Unique tag for the intent reply
    public static final String EXTRA_REPLY = "com.dimassdoubles.wordlistsqlineractive.REPLY";

    int mId  = FirstFragment.WORD_ADD;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
