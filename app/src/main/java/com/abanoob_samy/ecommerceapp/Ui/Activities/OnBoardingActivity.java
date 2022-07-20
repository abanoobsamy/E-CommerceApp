package com.abanoob_samy.ecommerceapp.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.abanoob_samy.ecommerceapp.Adapters.SliderAdapter;
import com.abanoob_samy.ecommerceapp.R;
import com.abanoob_samy.ecommerceapp.Ui.SignUser.RegisterActivity;
import com.abanoob_samy.ecommerceapp.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

    private ActivityOnBoardingBinding binding;
    private SliderAdapter mAdapter;

    private TextView[] dots;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addDots(0);

        binding.slider.addOnPageChangeListener(mOnPageChangeListener);

        mAdapter = new SliderAdapter(this);
        binding.slider.setAdapter(mAdapter);

        binding.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OnBoardingActivity.this, RegisterActivity.class));
            }
        });
    }

    private void addDots(int position) {

        dots = new TextView[3];

        binding.dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            binding.dots.addView(dots[i]);
        }

        if (dots.length > 0) {

            dots[position].setTextColor(getResources().getColor(R.color.colorBlue));
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);

            if (position == 0) {

                binding.getStartedBtn.setVisibility(View.INVISIBLE);
            }
            else if (position == 1) {

                binding.getStartedBtn.setVisibility(View.INVISIBLE);
            }
            else {

                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this, R.anim.slide_animation);
                binding.getStartedBtn.setVisibility(View.VISIBLE);
                binding.getStartedBtn.setAnimation(animation);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}