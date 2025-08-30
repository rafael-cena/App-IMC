package com.example.appimc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ImcsActivity extends AppCompatActivity {
    private ListView listView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imcs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        ImcAdapter imcAdapter = new ImcAdapter(this, R.layout.item_layout, Singleton.pessoaList);
        listView.setAdapter(imcAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pessoa p = (Pessoa) adapterView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(ImcsActivity.this);
                builder.setTitle("Excluir imc de "+p.getNome());
                builder.setMessage("Você deseja excluir o imc salvo de "+ p.getNome() + "?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Singleton.pessoaList.remove(i);
                        listView.setAdapter(new ArrayAdapter<>(ImcsActivity.this, android.R.layout.simple_list_item_1, Singleton.pessoaList));
                        Toast.makeText(ImcsActivity.this, "imc de " + p.getNome() + " excluído", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ImcsActivity.this, "exclusão cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.it_voltar) {
            finish();
        }
        else if (item.getItemId() == R.id.it_reset) {
            Singleton.pessoaList = new ArrayList<>();
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Singleton.pessoaList));
        }

        return super.onOptionsItemSelected(item);
    }
}