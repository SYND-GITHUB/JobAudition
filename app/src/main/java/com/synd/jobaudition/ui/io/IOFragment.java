package com.synd.jobaudition.ui.io;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.synd.jobaudition.R;
import com.synd.jobaudition.adapter.IORecyclerAdapter;
import com.synd.jobaudition.databinding.FragmentIoBinding;

public class IOFragment extends Fragment {
    private IOViewModel viewModel;
    private FragmentIoBinding binding;
    private IORecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_io, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(IOViewModel.class);
        viewModel.setApplication(requireActivity().getApplication());

        binding.setLifecycleOwner(requireActivity());
        binding.btnSend.setOnClickListener(v -> {
            String input = binding.inputEdittext.getText().toString().trim();
            if (!TextUtils.isEmpty(input)) {
                viewModel.newInput(input);
                binding.inputEdittext.setText("");
            }
        });

        adapter = new IORecyclerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getLiveData().observe(requireActivity(), ioModel -> {
            adapter.updateModel(ioModel);
        });
    }

    public void reloadList(boolean enableFormatInput) {
        adapter.setEnableFormatInput(enableFormatInput);
    }
}
