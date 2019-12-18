package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdxxtop.guardianapp.R;

import androidx.annotation.IdRes;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberEditTextView extends RelativeLayout implements TextWatcher {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    //textview显示的edittext的最大长度
    private int maxLength;

    public NumberEditTextView(Context context) {
        this(context, null);
    }

    public NumberEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        //default
        maxLength = 100;

        settingView();
    }

    private void settingView() {
        editTextInScrollView(R.id.et_content);
        etContent.addTextChangedListener(this);
        etContent.setText("");
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_number_edit_text_view, this, true);
        ButterKnife.bind(this);
    }

    public void editTextInScrollView(final @IdRes int editId) {
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((view.getId() == editId && canVerticalScroll(etContent))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }


    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        if (editText.canScrollVertically(-1) || editText.canScrollVertically(1)) {
            //垂直方向上可以滚动
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (TextUtils.isEmpty(s)) {
            tvNumber.setText(new StringBuilder().append(0).append("/").append(maxLength));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editable editable = etContent.getText();
        int len = editable.length();

        if (len > maxLength) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取新字符串
            String newStr = str.substring(0, maxLength);
            etContent.setText(newStr);
            editable = etContent.getText();

            //新字符串的长度
            int newLen = editable.length();
            //旧光标位置超过字符串长度
            if (selEndIndex > newLen) {
                selEndIndex = editable.length();
            }
            //设置新光标所在的位置
            Selection.setSelection(editable, selEndIndex);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            int length = s.length();
            if (length > maxLength) {
                length = maxLength;
            }
            tvNumber.setText(new StringBuilder().append(length).append("/").append(maxLength));
//            tvNumber.setText(length + "/200");
            if (length == maxLength) {
                StringBuilder append = new StringBuilder().append("已超过").append(maxLength).append("字");
//                UIUtils.showToast(append.toString());
                Toast.makeText(getContext(), "超过字数限制", Toast.LENGTH_SHORT).show();
            }
        } else {
            tvNumber.setText(new StringBuilder().append(0).append("/").append(maxLength));
        }
    }

    /**
     * 设置最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        etContent.setText("");
    }

    public void setEditHint(String hint) {
        etContent.setHint(hint);
    }

    public String getEditValue() {
        return etContent.getText().toString().trim();
    }
}
