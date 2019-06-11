package com.hangzhou.love.lovemyself.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.hangzhou.love.lovemyself.R;
import com.hangzhou.love.lovemyself.jsbridge.BridgeHandler;
import com.hangzhou.love.lovemyself.jsbridge.BridgeWebView;
import com.hangzhou.love.lovemyself.jsbridge.CallBackFunction;
import com.hangzhou.love.lovemyself.widget.MOPTextDialog;

import java.io.File;

public class H5Activity extends AppCompatActivity {
    private String path;

    private final String TAG = "WebViewActivity";

    private String mCameraFilePath;

    private BridgeWebView webView;

    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

    private MOPTextDialog confrimDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        webView = (BridgeWebView) findViewById(R.id.webView);
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(wvcc);
        // WebView加载web资源
        path = "file:///android_asset/wx.html";

//        path ="http://111.231.206.11:80/";

        webView.loadUrl(path);

        webView.addJavascriptInterface(new javaScriptInterce(),"bjj");

        //必须和js同名函数，注册具体执行函数，类似java实现类。
        //第一参数是订阅的java本地函数名字 第二个参数是回调Handler , 参数返回js请求的resqustData,function.onCallBack（）回调到js，调用function(responseData)
        webView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e(TAG, "指定Handler接收来自web的数据：" + data);
                function.onCallBack("指定Handler收到Web发来的数据，回传数据给你");
            }
        });
    }







    WebChromeClient wvcc = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 android.webkit.JsResult result) {
            Toasts.showToast(H5Activity.this, message);
            result.confirm();
            return true;
        };

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final android.webkit.JsResult result) {
            confrimDialog = new MOPTextDialog(H5Activity.this, true, false);
            confrimDialog.setTitle(H5Activity.this.getResources()
                    .getString(R.string.app_alert_title));
            confrimDialog.setContent(message);
            confrimDialog.setCancelable(true);
            confrimDialog.setConfirmable(true);
            confrimDialog.setConfirmClickListener(H5Activity.this
                            .getResources().getString(R.string.alert_dialog_ok),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            confrimDialog.dismiss();
                            result.confirm();
                        }
                    });
            confrimDialog.setCancelClickListener(H5Activity.this
                            .getResources().getString(R.string.alert_dialog_cancel),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            confrimDialog.dismiss();
                            result.cancel();
                        }
                    });
            confrimDialog.show();
            return true;
        };

        // For Android > 4.1.1
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String AcceptType, String capture) {
            this.openFileChooser(uploadMsg);
        }

        // For Android 3.0+
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String AcceptType) {
            this.openFileChooser(uploadMsg);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            pickFile();
        }

    };


    final class javaScriptInterce{
        @JavascriptInterface
       public void get(){
            String str = "卜俊杰操刀。哈哈，来自安卓原生的数据呢";
            final String call = "javascript:showMsg(\"" + str + "\")";
//            final String call = "javascript:jump()";
           webView.post(new Runnable() {
               @Override
               public void run() {
                   webView.loadUrl(call);

               }
           });
       }
    }
    public void pickFile() {

        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        chooserIntent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent chooser = createChooserIntent(createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, chooserIntent);
        startActivityForResult(chooser, RESULT_CODE);
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "请选择");
        return chooser;
    }

    private Intent createCameraIntent() {

        // 指定拍照的目录
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getPath()
                + "/platform/images/");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getPath() + File.separator
                + System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }
}
