package test.practical.com.androidprojectsstructuredemo.Utils;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;


public abstract class FileManagerUtill extends AppCompatActivity implements ImageChooserListener, FileChooserListener {


    private final static String TAG = "FileManagerUtill";
    private String filePath;
    private int chooserType;
    private ImageChooserManager imageChooserManager;
    private FileChooserManager fm;
    private String originalFilePath;
    private String thumbnailFilePath;
    private String thumbnailSmallFilePath;
    private boolean isActivityResultOver = false;

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    public abstract Void OnImageChosanResponce(String Path, String Base64);

    public abstract Void OnFileChosan(ChosenFile file, String Base64);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    public void chooseFile() {
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

    public void chooseImage() {

        new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        chooserType = ChooserType.REQUEST_PICK_PICTURE;
                        imageChooserManager = new ImageChooserManager(FileManagerUtill.this,
                                ChooserType.REQUEST_PICK_PICTURE, true);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        imageChooserManager.setExtras(bundle);
                        imageChooserManager.setImageChooserListener(FileManagerUtill.this);
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
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {

                    }
                })
                .execute(this);
    }

    public void takePicture() {

        new Permissive.Request(Manifest.permission.CAMERA)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
                        imageChooserManager = new ImageChooserManager(FileManagerUtill.this,
                                ChooserType.REQUEST_CAPTURE_PICTURE, true);
                        imageChooserManager.setImageChooserListener(FileManagerUtill.this);
                        try {
                            //pbar.setVisibility(View.VISIBLE);
                            filePath = imageChooserManager.choose();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {

                    }
                })
                .execute(this);
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

                    String Base64 = "";
                    try {
                        Base64 = encodeFileToBase64Binary(image.getFilePathOriginal());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    OnImageChosanResponce(image.getFilePathOriginal(), Base64);
                } else {
                    Log.i(TAG, "Chosen Image: Is null");
                }


            }
        });

    }

    private void loadImage(ImageView iv, final String path) {
        Picasso.with(FileManagerUtill.this)
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
                String Base64 = "";
                try {
                    Base64 = encodeFileToBase64Binary(chosenFile.getFilePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                OnFileChosan(chosenFile, Base64);
                //populateFileDetails(chosenFile);
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
        Log.e(TAG, "File name: " + file.getFileName() + "\n\n");
        Log.e(TAG, "File path: " + file.getFilePath() + "\n\n");
        Log.e(TAG, "Mime type: " + file.getMimeType() + "\n\n");
        Log.e(TAG, "File extn: " + file.getExtension() + "\n\n");
        Log.e(TAG, "File size: " + file.getFileSize() / 1024 + "KB");
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
            Log.e("*************", "---");
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), FileManagerUtill.this));
            fm.submit(requestCode, data);
        } else {
            // pbar.setVisibility(View.GONE);
        }
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(FileManagerUtill.this, chooserType, true);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageChooserManager.setExtras(bundle);
        imageChooserManager.setImageChooserListener(FileManagerUtill.this);
        imageChooserManager.reinitialize(filePath);
    }

    private String encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encode(bytes, 1);
        String encodedString = new String(encoded);

        //Log.e("Base64",encodedString);
        return encodedString;
    }
}
