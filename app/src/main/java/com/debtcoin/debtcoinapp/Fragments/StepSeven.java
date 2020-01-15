package com.debtcoin.debtcoinapp.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.BuildConfig;
import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Database.DBSaveProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepSeven.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepSeven#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepSeven extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNext;
    private Button btnTakePicture;
    private ImageView ivResult;
    private TextView tvRetake;
    private Uri file;
    private final static int TAKEPICTURE = 100;
    private final static int RETAKEPICTURE = 101;
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private String TAG = "Step 7";
    private  String mCurrentPhotoPath;;

    public StepSeven() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepSeven.
     */
    // TODO: Rename and change types and number of parameters
    public static StepSeven newInstance(String param1, String param2) {
        StepSeven fragment = new StepSeven();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_seven, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.stepseven));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        btnNext = view.findViewById(R.id.button_next);
        btnNext.setVisibility(View.GONE);

        btnTakePicture = view.findViewById(R.id.button_stepseven_camera);
        tvRetake = view.findViewById(R.id.text_stepseven_retake);
        ivResult = view.findViewById(R.id.img_stepseven_result);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // Do something for lollipop and above versions
                 //   file = FileProvider.getUriForFile(getActivity(), "com.debtcoin.debtcoinapp.fileprovider", getOutputMediaFile());
                    try {
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(TAG, "API : " + android.os.Build.VERSION.SDK_INT);
                    // do something for phones running an SDK before lollipop
                    file = Uri.fromFile(getOutputMediaFile());
                    Log.v(TAG, file.getPath());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                    startActivityForResult(intent, TAKEPICTURE);
                }

            }
        });

        tvRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        Variables.stepSven = false;
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Variables.stepSven = false;
                    Log.v(TAG, "API : " + android.os.Build.VERSION.SDK_INT);
                    file = Uri.fromFile(getOutputMediaFile());
                    Log.v(TAG, file.getPath());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                    startActivityForResult(intent, TAKEPICTURE);
                }


            }
        });

        if (Variables.stepSven) {
            Log.v(TAG, "Pulling data");
            btnNext.setVisibility(View.VISIBLE);
            tvRetake.setVisibility(View.VISIBLE);
            btnTakePicture.setVisibility(View.GONE);
            btnNext.setEnabled(false);
            ImageStepSeven imageStepSeven = new ImageStepSeven();
            imageStepSeven.execute();

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Variables.stepSven) {
                    Methods.switchFragment(getActivity(), new StepEight());
                } else {
                    Convertion convertion = new Convertion();
                    convertion.execute();
                }

            }
        });

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.CAMERA};

        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }

    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.debtcoin.debtcoinapp.fileprovider",
                        photoFile);
               //\ Uri photoURI = Uri.fromFile(createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKEPICTURE);
            }
        }
    }

    public void api23Up() {
        // Show the thumbnail on ImageView
        Uri imageUri = Uri.parse(mCurrentPhotoPath);
        File file = new File(imageUri.getPath());
        try {
            InputStream ims = new FileInputStream(file);
            ivResult.setImageBitmap(BitmapFactory.decodeStream(ims));
            ivResult.setScaleType(ImageView.ScaleType.FIT_XY);
            btnNext.setVisibility(View.VISIBLE);
            tvRetake.setVisibility(View.VISIBLE);
            btnTakePicture.setVisibility(View.GONE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Methods.showPopup(getActivity(), "Oops!", "Something went wrong! Please try again.", "OK");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Methods.showPopup(getActivity(), "Oops!", "Something went wrong! Please try again.", "OK");
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKEPICTURE) {
            if (resultCode == RESULT_OK) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        api23Up();
                } else {
                    ivResult.setImageURI(file);

                    ivResult.setScaleType(ImageView.ScaleType.FIT_XY);
                    btnNext.setVisibility(View.VISIBLE);
                    tvRetake.setVisibility(View.VISIBLE);
                    btnTakePicture.setVisibility(View.GONE);
                }

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "StepSeven");
        Log.v(TAG, Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + "_step7.jpg");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class ImageStepSeven extends AsyncTask<String, String, HashMap<String, String>> {
        ProgressDialog pd = new ProgressDialog(getActivity());
        String success = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Getting Image...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected HashMap<String, String> doInBackground(String... strings) {
            DBPullProcess dbPullProcess = new DBPullProcess(getActivity());

            return dbPullProcess.getStepSevenInfo();
        }

        @Override
        protected void onPostExecute(final HashMap<String, String> s) {
            super.onPostExecute(s);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pd.dismiss();
                    ivResult.setImageBitmap(Methods.base64ToBitmap(s.get("base64")));
                    ivResult.setScaleType(ImageView.ScaleType.FIT_XY);

                }
            }, 2000);
                btnNext.setEnabled(true);

        }
    }

    private class Convertion extends AsyncTask<String, String, String> {
        ProgressDialog pd = new ProgressDialog(getActivity());
        String success = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Processing Image...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, Bitmap> map = new HashMap<>();
            map.put("filepath", Methods.scale(((BitmapDrawable) ivResult.getDrawable()).getBitmap(), 600, 400));

            DBSaveProcess dbStepSeven = new DBSaveProcess(getActivity());

            String p = "";
            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                p = mCurrentPhotoPath;
                Log.v(TAG, mCurrentPhotoPath);
            } else {
                p = file.getPath();
            }
            if (dbStepSeven.stepSeven(map, p)) {
                success = "success";
            }
            return success;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            if (s.equalsIgnoreCase("success")) {
                Log.v("Saving image for step 7", "SUCCESS!");
                //   Log.v("PULL PROCESS 7", new DBPullProcess(getActivity()).getStepSevenInfo("7"));
                Variables.stepSven = true;
                Methods.switchFragment(getActivity(), new StepEight());

            } else {
                Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
