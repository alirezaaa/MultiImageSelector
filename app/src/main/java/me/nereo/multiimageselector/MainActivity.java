package me.nereo.multiimageselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.utils.Constants;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 2;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.choice_mode)
    RadioGroup mChoiceMode;
    @Bind(R.id.request_num)
    EditText mRequestNum;
    @Bind(R.id.result)
    TextView mResult;
    @Bind(R.id.show_camera)
    RadioGroup mShowCamera;
    @Bind(R.id.multi)
    RadioButton multi;
    @Bind(R.id.no_show)
    RadioButton noShow;
    @Bind(R.id.show)
    RadioButton show;
    @Bind(R.id.single)
    RadioButton single;
    private ArrayList<String> mSelectPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(Constants.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }
                mResult.setText(sb.toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mChoiceMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.multi) {
                    mRequestNum.setEnabled(true);
                } else {
                    mRequestNum.setEnabled(false);
                    mRequestNum.setText("");
                }
            }
        });
    }

    @OnClick(R.id.button)
    public void onButtonClick() {
        int selectedMode;

        if (mChoiceMode.getCheckedRadioButtonId() == R.id.single) {
            selectedMode = Constants.MODE_SINGLE;
        } else {
            selectedMode = Constants.MODE_MULTI;
        }

        boolean showCamera = mShowCamera.getCheckedRadioButtonId() == R.id.show;

        int maxNum = 9;
        if (!TextUtils.isEmpty(mRequestNum.getText())) {
            maxNum = Integer.valueOf(mRequestNum.getText().toString());
        }

        Intent intent = new Intent(MainActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(Constants.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(Constants.EXTRA_SELECT_COUNT, !mRequestNum.getText().toString().isEmpty() ?
                Integer
                        .parseInt(mRequestNum.getText().toString()) : Constants.DEFAULT_SELECTING_COUNT);
        // 选择模式
        intent.putExtra(Constants.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(Constants.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }
}
