package com.moysa.saveup.view.users;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.moysa.saveup.R;
import com.moysa.saveup.data.entity.UserEntity;
import com.moysa.saveup.databinding.UserListItemBinding;

import java.util.List;

/**
 * Created by Sergey Moysa
 */

public class UsersSpinnerAdapter extends ArrayAdapter<UserEntity> {

    public UsersSpinnerAdapter(@NonNull Context context, @NonNull List<UserEntity> objects) {
        super(context, R.layout.support_simple_spinner_dropdown_item, objects);
        setDropDownViewResource(R.layout.user_list_item);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.user_list_item, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mBinding.setUser(getItem(position));
        return convertView;
    }

    private static class ViewHolder {

        UserListItemBinding mBinding;

        ViewHolder(View itemView) {
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
