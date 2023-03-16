package com.example.module_create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.example.module_create.bean.ImageBean;
import com.example.module_create.widgets.PaletteView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

@Route(path = "/hand/HandActivity")
public class HandActivity extends AppCompatActivity implements EventListener, View.OnClickListener,
        PaletteView.Callback {
    public static final String HAND_IMAGE = "imageId";
    private boolean flag = true;
    private FloatingActionButton hand_btn_change;
    private ImageView hand_iv_left, hand_iv_right, hand_back1, hand_back2, hand_back3;
    private RelativeLayout hand_background;
    private LinearLayout hand_ll_img, hand_tool;
    private BottomSheetDialog dialog;
    private TextView hand_btn_input;    //识别结果
    //private EventManager asr;       //语音识别核心库
    private ImageView hand_undo, hand_redo, hand_pen, hand_eraser, hand_clear;
    //private MySurfaceView hand_dv_draw;
    private PaletteView hand_dv_draw;
    private Button hand_btn_back;
    private Context context;

    private ImageBean[] backs = {
            new ImageBean(R.mipmap.handback1),
            new ImageBean(R.mipmap.handback2),
            new ImageBean(R.mipmap.handback3)};
    private ImageBean[] imgs = {
            new ImageBean(R.mipmap.handimg1),
            new ImageBean(R.mipmap.handimg2),
            new ImageBean(R.mipmap.handimg3),
            new ImageBean(R.mipmap.handimg4),
            new ImageBean(R.mipmap.show3),
            new ImageBean(R.mipmap.show2),
            new ImageBean(R.mipmap.show3),
            new ImageBean(R.mipmap.show4),
            new ImageBean(R.mipmap.show5)};
    private List<ImageBean> backList = new ArrayList<>();

    private int backgroundimg = R.mipmap.handback1;
    private int leftimg = 0;
    private int rightimg = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置屏幕为横屏, 设置后会锁定方向
        setContentView(R.layout.activity_hand);

        context = this;

        for(int i = 0; i < backs.length; i++){
            backList.add(backs[i]);
        }

        init();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.hand_btn_input) {
            showCommentDialog();
        } else if (id == R.id.hand_back1) {
            hand_background.setBackgroundResource(R.mipmap.handback1);
            hand_ll_img.setVisibility(View.INVISIBLE);
            hand_btn_input.setTextColor(Color.BLACK);
        } else if (id == R.id.hand_back2) {
            hand_background.setBackgroundResource(R.mipmap.handback2);
            hand_ll_img.setVisibility(View.INVISIBLE);
            hand_btn_input.setTextColor(Color.BLACK);
        } else if (id == R.id.hand_back3) {
            hand_background.setBackgroundResource(R.mipmap.handback3);
            hand_ll_img.setVisibility(View.INVISIBLE);
            hand_btn_input.setTextColor(Color.WHITE);
        } else if (id == R.id.hand_btn_change) {
            if (flag) {
                flag = false;
                hand_ll_img.setVisibility(View.VISIBLE);
                hand_tool.setVisibility(View.VISIBLE);
            } else {
                flag = true;
                hand_ll_img.setVisibility(View.INVISIBLE);
                hand_tool.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.hand_undo) {
            hand_dv_draw.undo();
        } else if (id == R.id.hand_redo) {
            hand_dv_draw.redo();
        } else if (id == R.id.hand_pen) {
            hand_dv_draw.setMode(PaletteView.Mode.DRAW);
            hand_pen.setColorFilter(Color.parseColor("#FF5C5C"));
            hand_undo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_redo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_eraser.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_clear.setColorFilter(Color.parseColor("#8A8A8A"));
            //hand_pen.setSelected(false);
        } else if (id == R.id.hand_eraser) {//hand_dv_draw.reset();
            hand_dv_draw.setMode(PaletteView.Mode.ERASER);
            hand_eraser.setColorFilter(Color.parseColor("#FF5C5C"));
            hand_undo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_redo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_pen.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_clear.setColorFilter(Color.parseColor("#8A8A8A"));
            //hand_eraser.setSelected(false);
        } else if (id == R.id.hand_clear) {//hand_dv_draw.reset();
            hand_dv_draw.clear();
            hand_clear.setColorFilter(Color.parseColor("#FF5C5C"));
            hand_undo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_redo.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_pen.setColorFilter(Color.parseColor("#8A8A8A"));
            hand_eraser.setColorFilter(Color.parseColor("#8A8A8A"));
        } else if (id == R.id.hand_btn_back) {
            HandActivity.this.finish();
        }
    }

    @Override
    public void onUndoRedoStatusChanged() {
        hand_dv_draw.setEnabled(hand_dv_draw.canUndo());
        hand_dv_draw.setEnabled(hand_dv_draw.canRedo());
    }

    private void init(){
        hand_background = findViewById(R.id.hand_background);
        hand_ll_img = findViewById(R.id.hand_ll_img);
        hand_btn_input = findViewById(R.id.hand_btn_input);
        hand_back1 = findViewById(R.id.hand_back1);
        hand_back2 = findViewById(R.id.hand_back2);
        hand_back3 = findViewById(R.id.hand_back3);
        hand_tool = findViewById(R.id.hand_tool);
        hand_btn_back = findViewById(R.id.hand_btn_back);
        hand_ll_img.setVisibility(View.INVISIBLE);
        hand_tool.setVisibility(View.INVISIBLE);

        hand_btn_input.setOnClickListener(this);
        hand_back1.setOnClickListener(this);
        hand_back2.setOnClickListener(this);
        hand_back3.setOnClickListener(this);
        hand_btn_back.setOnClickListener(this);

        hand_btn_change = findViewById(R.id.hand_btn_change);
        hand_background.setBackgroundResource(backgroundimg);
        hand_btn_change.setOnClickListener(this);

        hand_iv_left = findViewById(R.id.hand_iv_left);
        hand_iv_left.setImageResource(imgs[leftimg%9].getImageId());
        hand_iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftimg++;
                hand_iv_left.setImageResource(imgs[leftimg%9].getImageId());
            }
        });

        hand_iv_right = findViewById(R.id.hand_iv_right);
        hand_iv_right.setImageResource(imgs[rightimg%9].getImageId());
        hand_iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightimg++;
                hand_iv_right.setImageResource(imgs[rightimg%9].getImageId());
            }
        });

        //画板部分
        hand_undo = findViewById(R.id.hand_undo);
        hand_redo = findViewById(R.id.hand_redo);
        hand_pen = findViewById(R.id.hand_pen);
        hand_eraser = findViewById(R.id.hand_eraser);
        hand_clear = findViewById(R.id.hand_clear);

        hand_undo.setOnClickListener(this);
        hand_redo.setOnClickListener(this);
        hand_pen.setOnClickListener(this);
        hand_eraser.setOnClickListener(this);
        hand_clear.setOnClickListener(this);

        hand_undo.setColorFilter(Color.parseColor("#8A8A8A"));
        hand_redo.setColorFilter(Color.parseColor("#8A8A8A"));
        hand_pen.setColorFilter(Color.parseColor("#8A8A8A"));
        hand_eraser.setColorFilter(Color.parseColor("#8A8A8A"));
        hand_clear.setColorFilter(Color.parseColor("#8A8A8A"));

        hand_dv_draw = findViewById(R.id.hand_dv_draw);
//        hand_dv_draw.setOnTouchListener(new OnDoubleClickListener(
//                new OnDoubleClickListener.DoubleClickCallback() {
//                    @Override
//                    public void onDoubleClick() {
//                        hand_tool.setVisibility(View.VISIBLE);
//                    }
//                }));
    }

    /**
     * 弹出输入区
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.dialog_hand,null);
        final EditText commentText = commentView.findViewById(R.id.dialog_hand_et);
        final Button dialog_hand_voice = commentView.findViewById(R.id.dialog_hand_voice);
        final Button bt_comment = commentView.findViewById(R.id.dialog_hand_send);
        final View dialog_hand_stop = commentView.findViewById(R.id.dialog_hand_stop);
        dialog.setContentView(commentView);

        //解决显示不全的情况
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    dialog.dismiss();
                    hand_btn_input.setText(commentContent);
                }else {
                    Toast.makeText(HandActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length() > 2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
    }
}