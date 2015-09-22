package me.nereo.multi_image_selector;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import me.nereo.multi_image_selector.utils.Constants;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends FragmentActivity
        implements MultiImageSelectorFragment.Callback {

    private int    mDefaultCount;
    private Button mSubmitButton;
    private ArrayList<String> resultList = new ArrayList<>();

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(Constants.EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onImageSelected(String path) {
        if (! resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            mSubmitButton.setText(String.format(getString(R.string.complete_with_size), resultList.size(), mDefaultCount));
            if (! mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
            mSubmitButton.setText(String.format(getString(R.string.complete_with_size), resultList.size(), mDefaultCount));
        } else {
            mSubmitButton.setText(String.format(getString(R.string.complete_with_size), resultList.size(), mDefaultCount));
        }
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mSubmitButton.setText(getString(R.string.complete));
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(Constants.EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(Constants.EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(Constants.EXTRA_SELECT_MODE, Constants.MODE_MULTI);

        // If multi mode selected but pass 1 as default count, consider single mode
        {
            if (mode == Constants.MODE_MULTI) {
                if (mDefaultCount == 1) {
                    mode = Constants.MODE_SINGLE;
                }
                // Throw an exception if multiple choice mode selected with 0 default count
                else if (mDefaultCount == 0) {
                    try {
                        throw new Exception(getString(R.string.zero_multi_illegal));
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }
                }
            }
        }

        boolean isShow = intent.getBooleanExtra(Constants.EXTRA_SHOW_CAMERA, true);
        if (mode == Constants.MODE_MULTI && intent.hasExtra(Constants.EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(Constants.EXTRA_DEFAULT_SELECTED_LIST);
        }

        // Change title based on mode
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mode == Constants.MODE_MULTI ? getString(R.string.select_photos) : getString(R.string.select_photo));


        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(Constants.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(Constants.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(Constants.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class
                                           .getName(), bundle))
                                   .commit();

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 完成按钮
        mSubmitButton = (Button) findViewById(R.id.commit);
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText(getString(R.string.complete));
            mSubmitButton.setEnabled(false);
        } else {
            mSubmitButton.setText(String.format(getString(R.string.complete_with_size), resultList.size(), mDefaultCount));
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultList != null && resultList.size() > 0) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(Constants.EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}
