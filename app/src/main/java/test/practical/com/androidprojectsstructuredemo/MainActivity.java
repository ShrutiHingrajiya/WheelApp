package test.practical.com.androidprojectsstructuredemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.FileChooserListener;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import test.practical.com.androidprojectsstructuredemo.Utils.DatePickerFragment;
import test.practical.com.androidprojectsstructuredemo.Utils.TimePickerFragment;

import static test.practical.com.androidprojectsstructuredemo.Utils.CommonUtils.showLog;


public  class MainActivity extends AppCompatActivity implements ImageChooserListener, FileChooserListener, DatePickerFragment.OnDateSetListener , TimePickerFragment.OnTimeSetListener {



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

        Button buttonTakePermission = (Button) findViewById(R.id.buttonTakePermission);
        buttonTakefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseFile();


                TimePickerFragment timePickerFragment=new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(),"Select Time");
                //DatePickerFragment mDatePicker = new DatePickerFragment();
                //mDatePicker.show(getSupportFragmentManager(), "Select date");
            }
        });

        imageViewThumbnail = (ImageView) findViewById(R.id.imageViewThumb);
        imageViewThumbSmall = (ImageView) findViewById(R.id.imageViewThumbSmall);



    }

    private void chooseFile() {
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_FILE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            // pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
            // pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            //pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Log.i(TAG, "Chosen Image: O - " + image.getFilePathOriginal());
                Log.i(TAG, "Chosen Image: T - " + image.getFileThumbnail());
                Log.i(TAG, "Chosen Image: Ts - " + image.getFileThumbnailSmall());
                isActivityResultOver = true;
                originalFilePath = image.getFilePathOriginal();
                thumbnailFilePath = image.getFileThumbnail();
                thumbnailSmallFilePath = image.getFileThumbnailSmall();
                // pbar.setVisibility(View.GONE);
                if (image != null) {
                    Log.i(TAG, "Chosen Image: Is not null");
                    //  textViewFile.setText(image.getFilePathOriginal());
                    loadImage(imageViewThumbnail, image.getFileThumbnail());
                    loadImage(imageViewThumbSmall, image.getFileThumbnailSmall());
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }

                //responce(5);
            }
        });

    }

    private void loadImage(ImageView iv, final String path) {
        Picasso.with(MainActivity.this)
                .load(Uri.fromFile(new File(path)))
                .fit()
                .centerInside()
                .into(iv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "Picasso Success Loading Thumbnail - " + path);
                    }

                    @Override
                    public void onError() {
                        Log.i(TAG, "Picasso Error Loading Thumbnail Small - " + path);
                    }
                });
    }

    @Override
    public void onFileChosen(ChosenFile chosenFile) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                populateFileDetails(chosenFile);
            }
        });
    }


    public void pickFile(View view) {
        fm = new FileChooserManager(this);
        fm.setFileChooserListener(this);
        try {

            fm.choose();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void populateFileDetails(ChosenFile file) {
        StringBuffer text = new StringBuffer();
        Log.e(TAG,"File name: " + file.getFileName() + "\n\n");
        Log.e(TAG,"File path: " + file.getFilePath() + "\n\n");
        Log.e(TAG,"Mime type: " + file.getMimeType() + "\n\n");
        Log.e(TAG,"File extn: " + file.getExtension() + "\n\n");
        Log.e(TAG,"File size: " + file.getFileSize() / 1024 + "KB");
        //textViewFileDetails.setText(text.toString());
    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "OnActivityResult");
        Log.i(TAG, "File Path : " + filePath);
        Log.i(TAG, "Chooser Type: " + chooserType);
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), this));
            fm.submit(requestCode, data);
        } else {
            // pbar.setVisibility(View.GONE);
        }
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        showLog("Hiii", String.valueOf(year)+"");
    }

    @Override
    public void onDateSet(TimePicker view, int hourOfDay, int minute, String timevalue) {
        showLog("Hiii", String.valueOf(timevalue)+"");
    }
}
