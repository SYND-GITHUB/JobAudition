package com.synd.jobaudition.adapter;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.synd.jobaudition.R;
import com.synd.jobaudition.databinding.ItemLayoutIoBinding;
import com.synd.jobaudition.model.IOModel;
import com.synd.jobaudition.utils.GeneralUtils;
import com.synd.jobaudition.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IORecyclerAdapter extends RecyclerView.Adapter<IORecyclerAdapter.IOHolder> {
    private HashMap<Long, IOModel> modelMap;
    private boolean enableFormatInput = true;

    public IORecyclerAdapter() {
        modelMap = new HashMap<>();
    }

    public void setEnableFormatInput(boolean enableFormatInput) {
        this.enableFormatInput = enableFormatInput;
        notifyDataSetChanged();
    }

    public void updateModel(IOModel ioModel) {
        modelMap.put(ioModel.getId(), ioModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IOHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLayoutIoBinding binding = ItemLayoutIoBinding.inflate(inflater, parent, false);
        return new IOHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IOHolder holder, int position) {
        ArrayList<IOModel> list = new ArrayList<>(modelMap.values());
        Collections.sort(list, (m1, m2) -> (int) (m2.getId() - m1.getId()));
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return modelMap.size();
    }

    class IOHolder extends RecyclerView.ViewHolder {

        private ItemLayoutIoBinding binding;

        public IOHolder(ItemLayoutIoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(IOModel ioModel) {
            if (enableFormatInput) {
                binding.inputText.setMovementMethod(LinkMovementMethod.getInstance());
                binding.inputText.setAutoLinkMask(Linkify.WEB_URLS);
                binding.inputText.setText(StringUtils.formatSpannableInput(ioModel.getInput(), ioModel.getMentions()));
            } else {
                binding.inputText.setMovementMethod(null);
                binding.inputText.setAutoLinkMask(0);
                binding.inputText.setText(ioModel.getInput());
            }
            binding.outputText.setText(ioModel.getOutput());
            if (ioModel.isFinishedLoadTitles()) {
                binding.loadingOutput.setVisibility(View.GONE);
            } else {
                binding.loadingOutput.setVisibility(View.VISIBLE);
            }

            binding.inputText.setOnLongClickListener(view -> {
                GeneralUtils.copyToClipboard(view.getContext(), binding.inputText.getText().toString());
                toastCopied();
                return true;
            });
            binding.outputText.setOnLongClickListener(view -> {
                GeneralUtils.copyToClipboard(view.getContext(), binding.outputText.getText().toString());
                toastCopied();
                return true;
            });
        }

        private void toastCopied() {
            Toast.makeText(itemView.getContext(), R.string.text_copied, Toast.LENGTH_SHORT).show();
        }
    }
}
