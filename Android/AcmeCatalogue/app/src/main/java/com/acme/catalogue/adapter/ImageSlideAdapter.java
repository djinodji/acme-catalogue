package com.acme.catalogue.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acme.catalogue.Photo;
import com.acme.catalogue.PhotosFragment;
import com.acme.catalogue.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Djinodji on 3/8/2016.
 */
public class ImageSlideAdapter extends PagerAdapter {
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private ImageLoadingListener imageListener;
    Activity activity;
    List<Photo> products;
    PhotosFragment photosFragment;

    public ImageSlideAdapter(Activity activity, List<Photo> products,
                             PhotosFragment photosFragment) {
        this.activity = activity;
        this.photosFragment = photosFragment;
        this.products = products;
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_error)
                .showStubImage(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory()
                .cacheOnDisc().build();

        try {
            imageListener = (ImageLoadingListener) new ImageDisplayListener();
        }
        catch (Exception x)
        {
            String j=x.getMessage();
        }
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.vp_image, container, false);

        ImageView mImageView = (ImageView) view
                .findViewById(R.id.image_display);
       // mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        imageLoader.displayImage(
                ((Photo) products.get(position)).getImageUrl(), mImageView,
                options, imageListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private static class ImageDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
