package com.debtcoin.debtcoinapp.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.Payment;
import com.debtcoin.debtcoinapp.API.service.rx.PaymentRxService;
import com.debtcoin.debtcoinapp.API.service.rx.S3RxService;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MakePayment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakePayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakePayment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TAKEPICTURE = 100;

    private static final String TAG = "MakePayment";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.relative_makepayment_container)
    RelativeLayout rlContainer;
    @BindView(R.id.spinner_makepayment_cashouthmethod)
    Spinner spMethod;
    @BindView(R.id.edit_makepayment_amount)
    EditText etAmount;
    @BindView(R.id.edit_makepayment_userid)
    EditText etUserId;
    @BindView(R.id.edit_makepayment_datedue)
    EditText etDateDue;
    @BindView(R.id.edit_makepayment_date)
    EditText etDate;
    @BindView(R.id.edit_makepayment_time)
    EditText etTime;
    @BindView(R.id.edit_makepayment_reference)
    EditText etReference;
    @BindView(R.id.img_makepayment_daterange)
    ImageView ivDate;
    @BindView(R.id.img_makepayment_time)
    ImageView ivTime;
    @BindView(R.id.img_makepayment_duedaterange)
    ImageView ivDueDate;
    @BindView(R.id.img_makepayment_camera)
    ImageView ivCamera;
    @BindView(R.id.img_makepayment_result)
    ImageView ivResult;
    @BindView(R.id.text_makepayment_retake)
    TextView tvRetake;
    @BindView(R.id.edit_makepayment_booking)
    EditText etBooking;
    private boolean isDueDate;
private  Calendar myCalendar;
    private OnFragmentInteractionListener mListener;
    private Uri file;
    private String mCurrentPhotoPath;

    @BindView(R.id.text_makepayment_next)
    TextView tvSubmit;
    private Payment paymentDetails;
    private ArrayAdapter banks;

    public MakePayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakePayment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakePayment newInstance(String param1, String param2) {
        MakePayment fragment = new MakePayment();
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
        View view = inflater.inflate(R.layout.fragment_make_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        Methods.customActionBar(getActivity(), "Make a Payment");
        banks = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_banks));
        spMethod.setAdapter(banks);


        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
               updateLabel();
            }

        };
        etUserId.setText(APIVariables.username);
        etDateDue.setText( APIVariables.currentPaymentDueDate);

//        Toast.makeText(getActivity(), "" + Variables.isImageUploaded, Toast.LENGTH_SHORT).show();

        if(!Variables.isImageUploaded) {
            Methods.showPopup(getActivity(), "Oops!", "Something went wrong while uploading your image. Please try again", "OK");
            isImageUploaded();

        }

        ivTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        ivDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDueDate = true;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
      ivDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              isDueDate = false;
              DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                      .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                      myCalendar.get(Calendar.DAY_OF_MONTH));
             // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
              datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
              datePickerDialog.show();

          }
      });

      ivCamera.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
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

                  // do something for phones running an SDK before lollipop
                  file = Uri.fromFile(getOutputMediaFile());

                  intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                  startActivityForResult(intent, TAKEPICTURE);
              }
          }
      });

      tvSubmit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(isComplete() && ivResult.getDrawable() != null) {

                 popup("", "Please make sure that all info is correct to prevent delays", "SUBMIT");
              } else {
                  if(ivResult.getDrawable() == null) {
                      Methods.showPopup(getActivity(), "Oops!", "Picture of payment slip is required.", "OK");
                  } else {
                      Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
                  }

              }
          }
      });

    }

    private void isImageUploaded() {
        new PaymentRxService(Variables.token)
                .getPaymentById(Variables.paymentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentResultMessage -> {
                    if(paymentResultMessage.getResponseCode().equalsIgnoreCase("200")) {
                        Log.v("MakePayment", paymentResultMessage.getBody().getBankName());
                       paymentDetails =  paymentResultMessage.getBody();
                        Log.d(TAG, "isImageUploaded: USER ID: " + paymentDetails.getUserId());
                        Log.d(TAG, "isImageUploaded: EDITTEXT: " + etUserId);
                        etUserId.setText(paymentDetails.getUserId());
                        etDateDue.setText(paymentDetails.getDueDate());
                        etDate.setText(paymentDetails.getDate());
                        etBooking.setText(paymentDetails.getBookingId());
                        etAmount.setText(String.valueOf(paymentDetails.getAmountPaid()));
                        etReference.setText(paymentDetails.getReference());
                        int pos = banks.getPosition(paymentDetails.getBankName());
                        spMethod.setSelection(pos);
                    }
                });

       // return isUploaded;

    }


    private boolean isComplete(){
        boolean isComplete = true;
        ArrayList<EditText> list = new ArrayList<>();
        list.add(etUserId);
        list.add(etDate);
        list.add(etDateDue);
        list.add(etReference);
        list.add(etTime);
        list.add(etAmount);
        list.add(etBooking);
        for(EditText t : list) {
            if(Methods.isEmpty(t)) {
                isComplete = false;
                break;
            }
        }


        return isComplete ;
    }


    private void popup(String header, String message, String okBtn){
        LayoutInflater inflate = LayoutInflater.from(getActivity());
        View promptsView = inflate.inflate(R.layout.error_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_popup_header);
        TextView tvMsg = promptsView.findViewById(R.id.text_popup_message);
        TextView tvCancel = promptsView.findViewById(R.id.text_popup_cancel);
        Button btnOk = promptsView.findViewById(R.id.button_popup_ok);
        ImageView ivIcon = promptsView.findViewById(R.id.img_popup_icon);
        tvHeader.setText(header);
        tvMsg.setText(message);
        tvCancel.setVisibility(View.VISIBLE);
        tvCancel.setText("Review");
        btnOk.setText(okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert2.dismiss();
                Payment payment = new Payment(
                        APIVariables.name,
                        etUserId.getText().toString(),
                        etBooking.getText().toString(),
                        etDateDue.getText().toString(),
                        spMethod.getSelectedItem().toString(),
                        etReference.getText().toString(),
                        etDate.getText().toString(),
                        etTime.getText().toString(),
                        Float.parseFloat(etAmount.getText().toString())
                );

                Log.d(TAG, "onClick: File Path: " + getFilePath());

                makePayment(payment, getFilePath());
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert2.dismiss();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKEPICTURE) {
            if (resultCode == RESULT_OK) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    api23Up();
                } else {
                    ivResult.setVisibility(View.VISIBLE);
                    Picasso.get().load(file).into(ivResult);
                    ivResult.setScaleType(ImageView.ScaleType.FIT_XY);
                    tvRetake.setVisibility(View.VISIBLE);
                    ivCamera.setVisibility(View.GONE);
                }

            } else if (resultCode == RESULT_CANCELED) {

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
            tvRetake.setVisibility(View.VISIBLE);
            ivCamera.setVisibility(View.GONE);
            ivResult.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Methods.showPopup(getActivity(), "Oops!", "Something went wrong! Please try again.", "OK");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Methods.showPopup(getActivity(), "Oops!", "Something went wrong! Please try again.", "OK");
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
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if(isDueDate) {
            etDateDue.setText(sdf.format(myCalendar.getTime()));
        } else {
            etDate.setText(sdf.format(myCalendar.getTime()));
        }
    }


    private String getFilePath() {
        String p;
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            p = mCurrentPhotoPath;
            Log.v(TAG, mCurrentPhotoPath);
        } else {
            p = file.getPath();
        }
        
        return p;
    }

    private void makePayment(Payment payment, String filePath) {

        final ProgressDialog pd = Methods.showProgress(getActivity(), "Sending Payment...","This may take several minutes to complete.");
        pd.show();

        compositeDisposable.add(new PaymentRxService(Variables.token)
                .makePayment(APIVariables.username, payment)
                .subscribeOn(Schedulers.io())
                .flatMap(paymentResultMessage -> {

                    if (paymentResultMessage.getResponseCode().equals("200")) {

                        Log.d(TAG, "makePayment: " + paymentResultMessage.getResponseMessage());

                        File file;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            file = new File(getRealPathFromURIPath(Uri.parse(filePath)));
                        } else {
                            file = new File(filePath);
                        }

                        RequestBody reqFile;
                        MultipartBody.Part body;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            body = MultipartBody.Part.createFormData("paymentFile", file.getName(), reqFile);
                        } else {
                            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            body = MultipartBody.Part.createFormData("paymentFile", file.getName(), reqFile);
                        }

                       // Log.d(TAG, "makePayment: reqFile: " + reqFile);

                        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), APIVariables.username);

                        return new S3RxService(Variables.token)
                                .uploadPaymentFileToS3(body, username);
                    } else {
                        Log.d(TAG, "makePayment: " + paymentResultMessage.getResponseMessage());
                        Log.d(TAG, "makePayment: " + paymentResultMessage.getResponseCode());
                        return Maybe.error(new Exception(paymentResultMessage.getResponseMessage()));
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentDepositImageResultMessage -> {

                    pd.dismiss();

                    if (paymentDepositImageResultMessage.getResponseCode().equals("200")) {

                        // TODO insert next Activity/Fragment here.

                       Methods.switchFragment(getActivity(), new MakePaymentConfirmation());
                    } else {
                        Methods.showPopup(getActivity(), "Oops!", "Something went wrong.", "OK");
                        Log.e(TAG, paymentDepositImageResultMessage.getResponseMessage());
//                        Methods.showPopup(getActivity(), "Oops!", paymentDepositImageResultMessage.getResponseMessage(), "OK");
                    }
                }, error -> {

                    pd.dismiss();

                    Methods.showPopup(getActivity(), "Oops!", "Something went wrong.", "OK");
                    Log.e(TAG, "makePayment: ", error);
                    Log.e(TAG, error.getMessage());
                }));

    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "StepSeven");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + "_step7.jpg");
    }

    private String getRealPathFromURIPath(Uri contentURI) {
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.v("MakePayment", "ON DESTROY VIEW");

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDestroy() {
        super.onDestroy();
        Log.v("MakePayment", "ON DESTROY");

        compositeDisposable.clear();
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
}
