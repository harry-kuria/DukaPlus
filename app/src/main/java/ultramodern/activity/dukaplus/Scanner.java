package ultramodern.activity.dukaplus;

import android.app.Activity;
import android.content.Context;

import com.google.zxing.integration.android.IntentIntegrator;

public class Scanner {
    public void ScanCode(Context context){
        IntentIntegrator intentIntegrator =  new IntentIntegrator((Activity) context);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("SCANNING CODE");
        intentIntegrator.initiateScan();

    }
}
