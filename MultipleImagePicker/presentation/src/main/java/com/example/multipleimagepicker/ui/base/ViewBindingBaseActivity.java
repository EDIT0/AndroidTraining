package com.example.multipleimagepicker.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

abstract public class ViewBindingBaseActivity<Binding extends ViewBinding> extends AppCompatActivity {

    public Binding binding;
    public abstract  Binding getBinding();
    public Activity mActivity;

    private Toast toast = null;

    public void  initBinding(){
        binding = getBinding();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initBinding();
        setContentView(binding.getRoot());

        mActivity = this;
    }

    public void showToast(String message) {
        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}
