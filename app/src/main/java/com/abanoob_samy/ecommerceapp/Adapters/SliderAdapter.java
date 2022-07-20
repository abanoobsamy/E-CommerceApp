package com.abanoob_samy.ecommerceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abanoob_samy.ecommerceapp.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    private final Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int imagesArray[] = {

            R.drawable.mongolian_soldier_vector,
            R.drawable.mongolian_eagle_vector,
            R.drawable.mongolian_warrior_vector
    };

    int headingArray[] = {

            R.string.first_slide,
            R.string.second_slide,
            R.string.third_slide
    };

    int descriptionArray[] = {

            R.string.description1,
            R.string.description2,
            R.string.description3
    };

    @Override
    public int getCount() {
        return headingArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_sliding, container, false);

        ImageView imageView = view.findViewById(R.id.slider_img);
        TextView tvHeading = view.findViewById(R.id.heading);
        TextView tvDesc = view.findViewById(R.id.description);

        imageView.setImageResource(imagesArray[position]);
        tvHeading.setText(headingArray[position]);
        tvDesc.setText(descriptionArray[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
