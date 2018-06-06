package bie.com.yuekao;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import bie.com.yuekao.view.AppUtil;


/**
 * 梯形效果界面类
 */
public class MainActivity extends AppCompatActivity {
    private MyViewGroup threeColorView;
    private int count = 0;
    private EditText etext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threeColorView = findViewById(R.id.threecolorview);
        etext = findViewById(R.id.etext);

    }

   // 添加view
    public void add(View view) {
        count++;
        int width = AppUtil.screenWidth(this);
        TextView textView = new TextView(this);


        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView,"translationX",(width-width/2),0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        if (count==1||count==3||count==5||count==7||count==9||count==11||count==13||count==15||count==17||count==19||count==21){
            textView.setText(etext.getText());
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }else{
            textView.setText(etext.getText());
            textView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        }
        threeColorView.addView(textView);

        ViewGroup.LayoutParams params = textView.getLayoutParams();
        params.width = width/2;
        params.height = 70;
        textView.setLayoutParams(params);

    }
}


