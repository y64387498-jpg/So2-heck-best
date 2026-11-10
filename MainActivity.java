package com.so2headstudio.pro;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String GAME_PACKAGE = "com.axlebolt.standoff2";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        WebView web = new WebView(this);
        web.setBackgroundColor(0xFF080C14);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.addJavascriptInterface(new GameBridge(), "So2Bridge");
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
    }

    private final class GameBridge {
        @JavascriptInterface public void launchGame() {
            runOnUiThread(() -> {
                Intent launch = getPackageManager().getLaunchIntentForPackage(GAME_PACKAGE);
                if (launch != null) startActivity(launch);
                else Toast.makeText(MainActivity.this,
                    "Standoff 2 yüklü değil veya paket adı farklı.", Toast.LENGTH_LONG).show();
            });
        }
    }
}
