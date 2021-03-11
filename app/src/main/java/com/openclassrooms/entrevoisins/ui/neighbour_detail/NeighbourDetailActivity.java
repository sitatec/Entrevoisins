package com.openclassrooms.entrevoisins.ui.neighbour_detail;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.utils.FavoriteNeighbourIds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NeighbourDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity";
    private Neighbour neighbour;

    @BindView(R.id.neighbour_detail_header_title)
    TextView titleText;
    @BindView(R.id.neighbour_detail_header)
    RelativeLayout headerLayout;
    @BindView(R.id.neighbour_name)
    TextView neighbourNameText;
    @BindView(R.id.neighbour_phone_number)
    TextView neighbourPhoneNumberText;
    @BindView(R.id.neighbour_location)
    TextView neighbourLocationText;
    @BindView(R.id.neighbour_social_network)
    TextView neighbourSocialNetworkText;
    @BindView(R.id.neighbour_about_me)
    TextView neighbourAboutMeText;
    @BindView(R.id.neighbour_detail_root)
    FrameLayout viewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_detail);
        ButterKnife.bind(this);
        neighbour = getIntent().getParcelableExtra(EXTRA_NAME);
        setUiData();
    }

    private void setUiData(){
        setHeaderBackgroundImage();
        titleText.setText(neighbour.getName());
        neighbourNameText.setText(neighbour.getName());
        neighbourPhoneNumberText.setText(neighbour.getPhoneNumber());
        neighbourLocationText.setText(neighbour.getAddress());
        neighbourAboutMeText.setText(neighbour.getAboutMe());
        neighbourSocialNetworkText.setText(neighbour.getSocialNetworkAccount());
    }

    private void setHeaderBackgroundImage(){
        Glide.with(this)
                    .asDrawable()
                    .load(neighbour.getAvatarUrl())
                    .into(new CustomTarget<Drawable>(){
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            headerLayout.setBackground(resource);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {}
                    });
    }

    @OnClick(R.id.add_to_favorite_button)
    void addToFavorite(){
        final FavoriteNeighbourIds favoriteNeighbourIds = DI.getFavoriteNeighbourIds();
        String message;
        if(favoriteNeighbourIds.contains(neighbour.getId())){
            message = getString(R.string.favorite_already_exist, neighbour.getName());
        }else {
            favoriteNeighbourIds.put(neighbour.getId());
            message = getString(R.string.favorite_added_successfully, neighbour.getName());
        }
        Snackbar.make(viewRoot,  message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.back_button)
    void backToPreviousActivity(){
        finish();
    }
}