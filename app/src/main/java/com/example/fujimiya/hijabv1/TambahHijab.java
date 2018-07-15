package com.example.fujimiya.hijabv1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TambahHijab extends AppCompatActivity {

    Spinner dynamicSpinner;
    ImageView gambar;
    private static final int PICK_IMAGE_REQUEST = 234;

    EditText txtnama,txtdeskrip,txtharga,txt_stok;
    Spinner txtKategori;
    ImageView gmbbarang;

    String SIDKategori;

    Bitmap bitmap;
    private Uri filePath;
    private String JSON_STRING;

    ArrayList<String> idKat = new ArrayList<String>();
    ArrayList<String> namaKat = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_hijab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nama Toko");
        toolbar.setSubtitle("Input Barang");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gmbbarang = findViewById(R.id.gambarBarang);
        txtnama = findViewById(R.id.nama_barang);
        txtdeskrip = findViewById(R.id.deskripsiBarang);
        txtKategori = findViewById(R.id.KategoriBarang);
        txtharga = findViewById(R.id.hargaBarang);
        txt_stok = findViewById(R.id.stok_barang);

        getJSON();


//
//        dynamicSpinner = (Spinner) findViewById(R.id.KategoriBarang);
//
//        ArrayList<String> items = new ArrayList<String>(Arrays.asList("Chai Latte", "Green Tea", "Black Tea"));
//        final ArrayList<String> iditems = new ArrayList<String>();
//        iditems.add("CLT");
//        iditems.add("GTa");
//        iditems.add("BtE");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, items);
//
//        dynamicSpinner.setAdapter(adapter);
//
//        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                //Log.v("item", (String) parent.getItemAtPosition(position));
//                //Toast.makeText(TambahHijab.this,"Isi : "+iditems.get(position),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });

        txtKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SIDKategori = idKat.get(position);
                //Log.v("item", (String) parent.getItemAtPosition(position));
//                Toast.makeText(TambahHijab.this,"Isi : "+idKat.get(position),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(),Barang.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void uploadGambar(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                    editTextName.setText(getStringImage(bitmap));
                    gmbbarang.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toast.makeText(TambahHijab.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void showEmployee(){
        if(!idKat.isEmpty()){
            idKat.clear();
        }
        if(!namaKat.isEmpty()){
            namaKat.clear();
        }
        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nama_kategor = jo.getString(Konfigurasi.TAG_NAMA);

                namaKat.add(nama_kategor);
                idKat.add(id);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, namaKat);

            txtKategori.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ListAdapter adapter = new SimpleAdapter(
//                TampilSemuaPgw.this, list, R.layout.list_item,
//                new String[]{konfigurasi.TAG_ID,konfigurasi.TAG_NAMA},
//                new int[]{R.id.id, R.id.name});
//
//        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahHijab.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public void simpan(View view) {
        //Toast.makeText(TambahHijab.this,"Kode : "+SIDKategori,Toast.LENGTH_LONG).show();
        addEmployee();
    }

    private void addEmployee(){

        final String namaBarang = txtnama.getText().toString().trim();
        final String deskripsiBarang = txtdeskrip.getText().toString().trim();
        final String idKategori = SIDKategori;
        final String harga = txtharga.getText().toString().trim();
        final String stok = txt_stok.getText().toString().trim();


        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahHijab.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TambahHijab.this,s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Barang.class));
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiBarang.KEY_EMP_NAMA,namaBarang);
                params.put(KonfigurasiBarang.KEY_EMP_ID_KATEGORI,idKategori);
                params.put(KonfigurasiBarang.KEY_EMP_HARGA,harga);
                params.put(KonfigurasiBarang.KEY_EMP_STOK,stok);
                params.put(KonfigurasiBarang.KEY_EMP_DESKRIPSI,deskripsiBarang);
                params.put(KonfigurasiBarang.KEY_EMP_GAMBAR,getStringImage(bitmap));
//
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(KonfigurasiBarang.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imagebyte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return  encodedImage;
    }

}
