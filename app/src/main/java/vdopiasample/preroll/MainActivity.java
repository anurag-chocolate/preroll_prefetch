package vdopiasample.preroll;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.vdopia.ads.lw.LVDOAdRequest;
import com.vdopia.ads.lw.LVDOAdSize;
import com.vdopia.ads.lw.LVDOConstants;
import com.vdopia.ads.lw.PreRollVideoAd;
import com.vdopia.ads.lw.PrerollAdListener;

public class MainActivity extends RequestPermissionActivity implements PrerollAdListener {

    Button CLICK;
    VideoView Video;
    MediaController mc;

    //MediaController contoller;
    //ImageView Imageview;
    ProgressBar progesbar;
    private String apiKey="llpHX8";
    private static final String TAG = "MediaActivity";
    private LVDOAdRequest adRequest;
    //private preRollVideoAd preRollVideoAd;


    private int adWidth;
    private int adHeight;


    private RelativeLayout mAdLayout;
    private PreRollVideoAd preRollVideoAd;
    private static final boolean IS_MAIN_CONTENT_FULLSCREEN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Video=(VideoView) findViewById(R.id.videoView);
        CLICK=(Button) findViewById(R.id.button);
       // Imageview=(ImageView) findViewById(R.id.imageView);
        progesbar=(ProgressBar) findViewById(R.id.loading_progress);
        adRequest = createAdRequest();
        mAdLayout = (RelativeLayout) findViewById(R.id.adLayout);
        preRollVideoAd = new PreRollVideoAd(this);
        adWidth = (mAdLayout.getLayoutParams()).width;
        adHeight = (mAdLayout.getLayoutParams()).height;
        CLICK.setVisibility(View.VISIBLE);
        super.requestAppPermissions(mPermissions, R.string.runtime_permissions, REQUEST_PERMISSIONS);

        //Video.setVideoPath("http://ds.serving-sys.com/BurstingRes/Site-43752/Type-16/b20b5095-4319-4ece-8323-4f6cdf1971de.mp4");
        //Video.start();
        //requestPrerollAd();

        mc=new MediaController(MainActivity.this);
        mc.setVisibility(View.GONE);
        Video.setMediaController(mc);
        PreRollVideoAd.prefetch(this, apiKey, adRequest, LVDOAdSize.IAB_MRECT,IS_MAIN_CONTENT_FULLSCREEN);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //requestprerollad();
                //prerollvideoad.prefetch(this, apikey, adrequest, lvdoadsize.iab_mrect,is_main_content_fullscreen);

            }
        });
    }


    private void requestPrerollAd() {
         mc=new MediaController(MainActivity.this);
        mc.setVisibility(View.GONE);
        preRollVideoAd = new PreRollVideoAd(this);
        //preRollVideoAd.setMediaController(null);
        preRollVideoAd.setMediaController(mc);

        preRollVideoAd.loadAd(adRequest, apiKey, LVDOAdSize.IAB_MRECT, this, IS_MAIN_CONTENT_FULLSCREEN);
    }


    private LVDOAdRequest createAdRequest() {
        LVDOAdRequest adRequest = new LVDOAdRequest(MainActivity.this);

        adRequest.setRequester("Vdopia");
        adRequest.setAppBundle("chocolateApp");
        adRequest.setAppDomain("vdopia.com");
        adRequest.setAppName("VdopiaSampleApp");
        adRequest.setAppStoreUrl("play.google.com");
        adRequest.setCategory("Education");
        adRequest.setPublisherDomain("vdopia.com");
        adRequest.setCOPPAConfig(LVDOAdRequest.COPPA.DISABLE);
        return adRequest;
    }

    private void showPrerollAd() {
        ViewGroup parent = (ViewGroup) preRollVideoAd.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        String contentVideo = "http://www.vdopia.com/files/sample.mp4";
        preRollVideoAd.setVideoPath(contentVideo);

        mAdLayout.addView(preRollVideoAd);
        preRollVideoAd.showAd();

        /**
         * Attempt to prefetch the next pre roll ad and cache it since we have some time
         * because the current pre roll ad is playing.
         */

    }


            public void buttonclick(View v){

                //Video.start();

                Video.setVisibility(View.INVISIBLE);
                requestPrerollAd();
                //showPrerollAd();

            }



    @Override
    public void onPrerollAdLoaded(View prerollAd) {
        Log.d(TAG, "PreRoll Video Ad onPrerollAdLoaded");
         //will show ad, then your content
        progesbar.setVisibility(View.INVISIBLE);
        //CLICK.setVisibility(View.VISIBLE);
        Video.setVisibility(View.VISIBLE);
        showPrerollAd();
    }

    @Override
    public void onPrerollAdFailed(View prerollAd, LVDOConstants.LVDOErrorCode errorCode) {
        Log.d(TAG, "PreRoll Video Ad onPrerollAdFailed");
        //showPrerollAd(); //will only show your content since couldn't get ad
        CLICK.setVisibility(View.VISIBLE);
        Video.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrerollAdShown(View prerollAd) {
        Log.d(TAG, "PreRoll Video Ad onPrerollAdShown");
        CLICK.setVisibility(View.INVISIBLE);
        PreRollVideoAd.prefetch(this, apiKey, adRequest, LVDOAdSize.IAB_MRECT,IS_MAIN_CONTENT_FULLSCREEN);
    }

    @Override
    public void onPrerollAdClicked(View prerollAd) {
        Log.d(TAG, "PreRoll Video Ad onPrerollAdClicked");
    }

    @Override
    public void onPrerollAdCompleted(View prerollAd) {
        Log.d(TAG, "PreRoll Video Ad onPrerollAdCompleted");
        //Imageview.setVisibility(View.VISIBLE);


    }

    @Override
    public void onPrepareMainContent(MediaPlayer player) {
        Log.d(TAG, "PreRoll Video onPrepareMainContent");
    }

    @Override
    public void onErrorMainContent(MediaPlayer player, int code) {
        Log.d(TAG, "PreRoll Video onErrorMainContent : " + code);
        setContentVisibility();
    }

    @Override
    public void onCompleteMainContent(MediaPlayer player) {
        Log.d(TAG, "PreRoll Video onCompleteMainContent");
        setContentVisibility();
    }

    private void setContentVisibility() {
        //preRollVideoAd = new PreRollVideoAd(this);
        //preRollVideoAd.setMediaController(new MediaController(MainActivity.this));
        //MainActivity.this.finish();
        preRollVideoAd.setVideoPath(null);
        requestPrerollAd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preRollVideoAd != null) {
            preRollVideoAd.destroyView();
        }
    }
}
