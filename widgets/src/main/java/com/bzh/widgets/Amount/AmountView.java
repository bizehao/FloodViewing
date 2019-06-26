package com.bzh.widgets.Amount;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.widgets.R;

/**
 * @author 毕泽浩
 * @Description: 加减器
 * @time 2018/11/12 22:32
 */
public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = "AmountView";
    private int amount = 1; //购买数量
    private int goods_storage = 100; //商品库存

    private OnAmountChangeListener mListener;
    private OnInputClickListener inputClickListener;

    private MyEditTextView etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    private boolean focus = false; //是否处于焦点状态

    private boolean sign = true; //是否可以键盘输入

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_amount, this);
        etAmount = findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);
        etAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e(TAG, "onEditorAction2: " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    etAmount.clearFocus();
                }
                return false;
            }
        });
        etAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputClickListener != null) {
                    inputClickListener.onInputClick(amount);
                }
            }
        });

        etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                focus = hasFocus;
            }
        });

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        //int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 40);
        //int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 100);
        //int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        //int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        /*LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }*/

        /*LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }*/
        if (amount == 1) {
            btnDecrease.setEnabled(false);
        } else {
            btnDecrease.setEnabled(true);
        }

        if (amount == goods_storage) {
            btnIncrease.setEnabled(false);
        } else {
            btnIncrease.setEnabled(true);
        }
    }

    public void setEditText(boolean state){
        this.sign = state;
        if(sign){
            etAmount.setFocusable(true);
        }else {
            etAmount.setKeyListener(null);
            etAmount.setFocusable(false);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setInputClickListener(OnInputClickListener inputClickListener) {
        this.inputClickListener = inputClickListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    //获取EditText的焦点状态
    public boolean getFocusState() {
        return focus;
    }

    public void setAmount(int amount) {
        if(amount > 1){
            btnDecrease.setEnabled(true);
        }else {
            btnDecrease.setEnabled(false);
        }
        this.amount = amount;
        etAmount.setText(this.amount + "");
    }

    public int getAmount() {
        return amount;
    }

    public int getGoods_storage() {
        return goods_storage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 1) {
                amount--;
                etAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < goods_storage) {
                amount++;
                etAmount.setText(amount + "");
            }
        }

        //etAmount.clearFocus();
        if (amount == 1) {
            btnDecrease.setEnabled(false);
        } else {
            btnDecrease.setEnabled(true);
        }

        if (amount == goods_storage) {
            btnIncrease.setEnabled(false);
        } else {
            btnIncrease.setEnabled(true);
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //etAmount.selectAll();
        //etAmount.clearFocus();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()) {
            etAmount.setText(1 + "");
            return;
        }
        amount = Integer.valueOf(s.toString());
        if (amount > goods_storage) {
            etAmount.setText(goods_storage + "");
            return;
        }

        /*if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }*/
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

    public interface OnInputClickListener {
        void onInputClick(int val);
    }
}
