package com.example.termproject;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<ListViewData> list = new ArrayList<ListViewData>();
    Context context;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    TextView tvlist1, tvlist2, tvlist3;
    Button btn1_list, btn2_list;
    TextView tv1_ml, tv2_ml, tv3_ml;
    EditText et1_ml;
    ImageView iv1_ml, iv2_ml, iv1_pv;
    RadioGroup rg_ml; RadioButton rb1_ml, rb2_ml, rb3_ml;
    Button btn3_ml, btn4_ml, btn5_ml;
    String emotion;
    View photoview;
    Bitmap cameraBitmap, albumBitmap;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        context = viewGroup.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list, viewGroup, false);
        }

        tvlist1 = view.findViewById(R.id.tvlist1);
        tvlist2 = view.findViewById(R.id.tvlist2);
        tvlist3 = view.findViewById(R.id.tvlist3);
        btn1_list = view.findViewById(R.id.btn1_list);
        btn2_list = view.findViewById(R.id.btn2_list);
        ListViewData listdata = list.get(pos);
        tvlist1.setText("???????????? : " + listdata.getCategory());
        tvlist2.setText("?????? : " + listdata.getContent());
        tvlist3.setText("?????? : " + listdata.getAddress());

        btn1_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View memolistdialog = View.inflate(context, R.layout.memolistdialog, null);
                tv1_ml = memolistdialog.findViewById(R.id.tv1_ml);
                tv2_ml = memolistdialog.findViewById(R.id.tv2_ml);
                tv3_ml = memolistdialog.findViewById(R.id.tv3_ml);
                et1_ml = memolistdialog.findViewById(R.id.et1_ml);
                iv1_ml = memolistdialog.findViewById(R.id.iv1_ml);
                iv2_ml = memolistdialog.findViewById(R.id.iv2_ml);
                rg_ml = memolistdialog.findViewById(R.id.rg_ml);
                rb1_ml = memolistdialog.findViewById(R.id.rb1_ml);
                rb2_ml = memolistdialog.findViewById(R.id.rb2_ml);
                rb3_ml = memolistdialog.findViewById(R.id.rb3_ml);
                btn3_ml = memolistdialog.findViewById(R.id.btn3_ml);
                btn4_ml = memolistdialog.findViewById(R.id.btn4_ml);
                btn5_ml = memolistdialog.findViewById(R.id.btn5_ml);

                tv1_ml.setText(listdata.getCategory());
                et1_ml.setText(listdata.getContent());
                tv2_ml.setText(listdata.getDate());
                tv3_ml.setText(listdata.getAddress());

                switch (listdata.getEmotion()) { // ?????? ?????? ??????
                    case "?????? ??????":
                        rb1_ml.setChecked(true);rb2_ml.setChecked(false);rb3_ml.setChecked(false);
                        break;
                    case "??????":
                        rb1_ml.setChecked(false);rb2_ml.setChecked(true);rb3_ml.setChecked(false);
                        break;
                    case "?????? ??????":
                        rb1_ml.setChecked(false);rb2_ml.setChecked(false);rb3_ml.setChecked(true);
                        break;
                }

                if (rb1_ml.isChecked()==true){
                    rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                }else if(rb2_ml.isChecked()==true){
                    rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                }else{
                    rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                }
                // "??????" ?????? ?????? ??? ?????? ??????
                rg_ml.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb1_ml: // ??????
                                rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                                rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                emotion = rb1_ml.getText().toString();
                                break;
                            case R.id.rb2_ml: // ??????
                                rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                                rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                emotion = rb2_ml.getText().toString();
                                break;
                            case R.id.rb3_ml: // ??????
                                rb1_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                rb2_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                                rb3_ml.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#002EFF")));
                                emotion = rb3_ml.getText().toString();
                                break;
                        }
                        return;
                    }
                });

                btn3_ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), MemoDaily.class);
                        context.startActivity(intent);
                    }
                });

                // ????????? ?????? ??????
                cameraBitmap = null;
                byte[] cameraByte = listdata.getCamera();
                if(cameraByte!=null) {
                    cameraBitmap = BitmapFactory.decodeByteArray(cameraByte, 0, cameraByte.length);
                    iv1_ml.setImageBitmap(cameraBitmap);
                }else{
                    iv1_ml.setImageBitmap(null);
                }
                // ?????? ?????? ??????
                albumBitmap = null;
                byte[] albumByte = listdata.getAlbum();
                if(albumByte!=null) {
                    albumBitmap = BitmapFactory.decodeByteArray(albumByte, 0, albumByte.length);
                    iv2_ml.setImageBitmap(albumBitmap);
                }else {
                    iv2_ml.setImageBitmap(null);
                }

                iv1_ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoview = View.inflate(context, R.layout.photoview, null);
                        iv1_pv = photoview.findViewById(R.id.iv1_pv);
                        iv1_pv.setImageBitmap(cameraBitmap);
                        new AlertDialog.Builder(context)
                                .setTitle("?????? ??????")
                                .setIcon(R.drawable.photo)
                                .setView(photoview)
                                .setPositiveButton("??????", null)
                                .show();
                    }
                });
                iv2_ml.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoview = View.inflate(context, R.layout.photoview, null);
                        iv1_pv = photoview.findViewById(R.id.iv1_pv);
                        iv1_pv.setImageBitmap(albumBitmap);
                        new AlertDialog.Builder(context)
                                .setTitle("?????? ??????")
                                .setIcon(R.drawable.photo)
                                .setView(photoview)
                                .setPositiveButton("??????", null)
                                .show();
                    }
                });

                new AlertDialog.Builder(context)
                        .setView(memolistdialog)
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MemoDBHelper memoHelper = new MemoDBHelper(context);
                                SQLiteDatabase db = memoHelper.getWritableDatabase();
                                ContentValues row = new ContentValues();
                                row.put("content", et1_ml.getText().toString());
                                row.put("emotion", emotion);
                                db.update("memo", row, "date=? and category=?",
                                        new String[]{tv2_ml.getText().toString(), tv1_ml.getText().toString()});
                                memoHelper.close();
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });

        btn2_list.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MemoDBHelper memoHelper = new MemoDBHelper(context);
                SQLiteDatabase db = memoHelper.getWritableDatabase();
                db.delete("memo", "date=? and category=?", new String[]{listdata.getDate(), listdata.getCategory()});
                memoHelper.close();
                Toast.makeText(context.getApplicationContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addItemToList(int id, String date, String category, String content,
                              String address, byte[] camera, byte[] album, String emotion,
                              int etime, int edistance){
        ListViewData listdata = new ListViewData();
        listdata.setId(id);
        listdata.setDate(date);
        listdata.setCategory(category);
        listdata.setContent(content);
        listdata.setAddress(address);
        listdata.setCamera(camera);
        listdata.setAlbum(album);
        listdata.setEmotion(emotion);
        listdata.setEtime(etime);
        listdata.setEdistance(edistance);

        list.add(listdata);
    }
}
