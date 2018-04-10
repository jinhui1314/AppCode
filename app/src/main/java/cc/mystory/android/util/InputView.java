package cc.mystory.android.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.mystory.android.R;

/**
 * 自定义有字数限制的输入框，右下角有提示字数限制
 * <p>
 * Create by Lei on 2016/10/10
 */

public class InputView extends RelativeLayout {
    EditText et_info;
    TextView tv_nums;
    private String hint;
    int MAX_COUNT;
    Context context;

    public void setText(String text) {
        et_info.setText(text);
    }

    public void setHintAndMaxCount(String hint, int MAX_COUNT) {
        this.hint = hint;
        this.MAX_COUNT = MAX_COUNT;
        initView(context);
    }

    boolean isSingleLine = false;

    public void setHintAndMaxCount(String hint, int MAX_COUNT, boolean isSingleLine) {
        this.hint = hint;
        this.MAX_COUNT = MAX_COUNT;
        this.isSingleLine = isSingleLine;
        initView(context);
    }

    DoOnTextChange change;

    public void setHintAndMaxCount(String hint, int MAX_COUNT, DoOnTextChange change) {
        this.hint = hint;
        this.MAX_COUNT = MAX_COUNT;
        this.change = change;
        initView(context);
    }

    boolean EnglishIsNormal = false;
    int inputType = 0;

    public void setHintAndMaxCount(String hint, int MAX_COUNT, boolean EnglishIsNormal, int inputType) {
        this.hint = hint;
        this.MAX_COUNT = MAX_COUNT;
        this.EnglishIsNormal = EnglishIsNormal;
        this.inputType = inputType;
        initView(context);
    }

    public void setHintAndMaxCount(String hint, int MAX_COUNT, boolean EnglishIsNormal, int inputType, DoOnTextChange change) {
        this.hint = hint;
        this.MAX_COUNT = MAX_COUNT;
        this.EnglishIsNormal = EnglishIsNormal;
        this.inputType = inputType;
        this.change = change;
        initView(context);
    }

    public InputView(Context context) {
        super(context);
        this.context = context;
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

    }

    public void initView(Context context) {
        View.inflate(context, R.layout.layout_editview, this);
        et_info = (EditText) findViewById(R.id.et_info);
        tv_nums = (TextView) findViewById(R.id.tv_nums);

        et_info.setHint(hint);
        tv_nums.setText("0/" + MAX_COUNT);
        if (inputType != 0) {
            et_info.setInputType(inputType);
        }

        if (isSingleLine) {
            et_info.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                }
            });
        }

        et_info.addTextChangedListener(mTextWatcher);
        et_info.setSelection(et_info.length()); // 将光标移动最后一个字符后面

        setLeftCount();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {

        private int editStart;

        private int editEnd;

        public void afterTextChanged(Editable s) {
            editStart = et_info.getSelectionStart();
            editEnd = et_info.getSelectionEnd();

            // 先去掉监听器，否则会出现栈溢出
            et_info.removeTextChangedListener(mTextWatcher);


            if (!EnglishIsNormal) {
                // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
                // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
                while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            } else {
                while (calculateLengthNormal(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
                    s.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }
            // mEditText.setText(s);将这行代码注释掉就不会出现后面所说的输入法在数字界面自动跳转回主界面的问题了，多谢@ainiyidiandian的提醒
            et_info.setSelection(editStart);

            // 恢复监听器
            et_info.addTextChangedListener(mTextWatcher);

            setLeftCount();

        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (change != null) {
                change.onChange(s, start, before, count);
            }
        }

    };

    public interface DoOnTextChange {
        void onChange(CharSequence s, int start, int before, int count);
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    private long calculateLengthNormal(CharSequence c) {

        return c.length();
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        if (!EnglishIsNormal) {
            tv_nums.setText(getInputCount() + "/" + MAX_COUNT);
        } else {
            tv_nums.setText(getInputCountNormal() + "/" + MAX_COUNT);
        }
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(et_info.getText().toString());
    }

    private long getInputCountNormal() {
        return calculateLengthNormal(et_info.getText().toString());
    }

    public String getText() {
        return et_info.getText().toString();
    }
}
