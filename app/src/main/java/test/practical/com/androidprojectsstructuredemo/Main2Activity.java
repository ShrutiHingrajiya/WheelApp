package test.practical.com.androidprojectsstructuredemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import test.practical.com.androidprojectsstructuredemo.Utils.FileManagerUtill;

import static test.practical.com.androidprojectsstructuredemo.Utils.CommonUtils.grantallpermission;


public  class Main2Activity extends FileManagerUtill {



    private final static String TAG = "APP";
    private ImageView imageViewThumbnail;
    private ImageView imageViewThumbSmall;
    private String filePath;
    private int chooserType;
    private ImageChooserManager imageChooserManager;
    private FileChooserManager fm;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private boolean isActivityResultOver = false;


    @Override
    public Void OnImageChosanResponce(String Path, String Base64) {
        Log.e("Path",Path);
        Log.e("Base64",Base64);
        return null;
    }

    @Override
    public Void OnFileChosan(ChosenFile file, String Base64) {
        Log.e(TAG, "*File name: " + file.getFileName() + "\n\n");
        Log.e(TAG, "*File path: " + file.getFilePath() + "\n\n");
        Log.e(TAG, "*Mime type: " + file.getMimeType() + "\n\n");
        Log.e(TAG, "*File extn: " + file.getExtension() + "\n\n");
        Log.e(TAG, "*File size: " + file.getFileSize() / 1024 + "KB");
        Log.e(TAG ,"*Base64"+Base64);
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonTakePicture = (Button) findViewById(R.id.buttonTakePicture);
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              takePicture();
            }
        });
        Button buttonChooseImage = (Button) findViewById(R.id.buttonChooseImage);
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        Button buttonTakefile = (Button) findViewById(R.id.buttonTakefile);
        buttonTakefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseFile();


                pickFile(v);
            }
        });


        Button buttonTakePermission = findViewById(R.id.buttonTakePermission);
        buttonTakePermission.setOnClickListener(v -> {
            //chooseFile();

            grantallpermission(Main2Activity.this);
        });



        imageViewThumbnail = (ImageView) findViewById(R.id.imageViewThumb);
        imageViewThumbSmall = (ImageView) findViewById(R.id.imageViewThumbSmall);
    }






}
