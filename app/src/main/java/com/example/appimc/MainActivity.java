package com.example.appimc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText tiNome;
    private RadioButton rbMasc, rbFem;
    private SeekBar sbPeso, sbAltura;
    private TextView tvPeso, tvAltura;
    private Button btCalc;
    private String nome, sexo;
    private Double peso, altura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarDados();

        tiNome = findViewById(R.id.tiNome);
        rbMasc = findViewById(R.id.rbMasc);
        rbFem = findViewById(R.id.rbFem);
        sbPeso = findViewById(R.id.sbPeso);
        sbAltura = findViewById(R.id.sbAltura);
        tvPeso = findViewById(R.id.tvPeso);
        tvAltura = findViewById(R.id.tvAltura);
        btCalc = findViewById(R.id.btCalc);

        peso = (double) sbPeso.getProgress()/10;
        altura = (double) sbAltura.getProgress()/100;
        if (rbMasc.isChecked())
            sexo = "masculino";
        else
            sexo = "feminino";

        sbPeso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                peso = (double) sbPeso.getProgress()/10;
                tvPeso.setText(String.format("%.1f", peso) + " kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbAltura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                altura = (double) sbAltura.getProgress()/100;
                tvAltura.setText(String.format("%.2f", altura) + " m");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        rbMasc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                if (rbMasc.isChecked()) {
                    sexo = "masculino";
                    rbFem.setChecked(false);
                }
            }
        });
        rbFem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                if (rbFem.isChecked()) {
                    sexo = "feminino";
                    rbMasc.setChecked(false);
                }
            }
        });

        btCalc.setOnClickListener(e -> calcularIMC());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.it_imcs) {
            Intent intent = new Intent(this, ImcsActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.it_limpar) {
            tiNome.setText("");
            sbPeso.setProgress(770);
            sbAltura.setProgress(174);
        }
        else if (item.getItemId() == R.id.it_sair)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    private void carregarDados() {
        FileInputStream fout;
        ObjectInputStream out;

        try {
            fout = openFileInput("dados.dat");
            out = new ObjectInputStream(fout);
            Singleton.pessoaList = (List<Pessoa>) out.readObject();
        }
        catch (Exception e) {
            Log.e("erroxxx ao carregar dados: ", e.getMessage());
        }
    }

    private void calcularIMC() {
        nome = tiNome.getText().toString();
        Pessoa p = new Pessoa(nome, peso, altura, sexo);
        p.setImc();

        Singleton.pessoaList.add(p);

        Toast.makeText(this, p.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        FileOutputStream fout = null;
        ObjectOutputStream out;

        try {
            fout = openFileOutput("dados.dat", MODE_PRIVATE);
            out = new ObjectOutputStream(fout);
            out.writeObject(Singleton.pessoaList);
            out.close();
        }
        catch (Exception e) {
            Log.e("erroxxx ao salvar: ", e.getMessage());
        }
    }
}