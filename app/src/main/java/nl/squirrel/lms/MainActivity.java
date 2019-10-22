package nl.squirrel.lms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.content.res.Resources;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends Activity {
    WebView wv;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        res = getResources();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtview = findViewById(R.id.tV1);
        final ProgressBar pbar = findViewById(R.id.pB1);

        if (res.getBoolean(R.bool.fullscreen)) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        wv = (WebView) findViewById(R.id.webView);

        WebSettings ws = wv.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setLoadsImagesAutomatically(true);
        ws.setJavaScriptEnabled(true);
        ws.setAllowContentAccess(true);
        ws.setDomStorageEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.setWebViewClient(new WebViewClient());

        wv.loadUrl(res.getString(R.string.server));
        //wv.clearHistory();

        wv.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (!res.getBoolean(R.bool.progressbar))
                    progress = 100;

                if (progress < 100
                        && pbar.getVisibility() == ProgressBar.GONE) {
                    pbar.setVisibility(ProgressBar.VISIBLE);
                    txtview.setVisibility(View.VISIBLE);
                }

                pbar.setProgress(progress);
                if (progress >= 100) {
                    pbar.setVisibility(ProgressBar.GONE);
                    txtview.setVisibility(View.GONE);
                }
            }
        });
    }

    private void maybe_exit() {
        // alertdialog for exit the app
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set the title of the Alert Dialog
        alertDialogBuilder.setTitle(res.getString(R.string.terminate_title));

        // set dialog message
        alertDialogBuilder
        .setMessage(R.string.terminate_query)
        .setCancelable(false)
        .setPositiveButton(R.string.terminate_yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        // what to do if YES is tapped
                        finishAffinity();
                        System.exit(0);
                    }
                })

        .setNegativeButton(R.string.terminate_no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        // code to do on NO tapped
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            maybe_exit(); //super.onBackPressed();
        }
    }

    private class WebViewClient extends android.webkit.WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
