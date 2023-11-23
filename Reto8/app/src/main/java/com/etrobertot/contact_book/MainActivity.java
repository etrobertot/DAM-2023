package com.etrobertot.contact_book;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends Activity {

    private EditText txtCodigo;
    private EditText txtNombre;
    private EditText txtUrl;
    private EditText txtTelefono;
    private EditText txtEmail;
    private EditText txtServicio;
    private EditText txtClasificacion;
    private TextView txtResultado;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;

    private Button btnConsultarAll;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos las referencias a los controles
        txtCodigo = (EditText)findViewById(R.id.txtCodigo);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtUrl = (EditText)findViewById(R.id.txtURL);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtServicio = (EditText)findViewById(R.id.txtServicio);
        txtClasificacion = (EditText)findViewById(R.id.txtClasificacion);
        txtResultado = (TextView)findViewById(R.id.txtResultado);

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnConsultarAll = (Button)findViewById(R.id.btnConsultarAll);

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);

        db = usdbh.getWritableDatabase();

        /*btnInsertar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (txtCodigo.toString().isEmpty()||txtNombre.toString().isEmpty()||txtUrl.toString().isEmpty()||txtTelefono.toString().isEmpty()
                        ||txtEmail.toString().isEmpty()||txtServicio.toString().isEmpty()||txtClasificacion.toString().isEmpty()) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();
                String url = txtUrl.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String servicio = txtServicio.getText().toString();
                String clasificacion = txtClasificacion.getText().toString();

                                   //Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        //Alternativa 1: m�todo sqlExec()
                        //String sql = "INSERT INTO Usuarios (codigo,nombre) VALUES ('" + cod + "','" + nom + "') ";
                        //db.execSQL(sql);

                        //Alternativa 2: m�todo insert()
                        ContentValues nuevoRegistro = new ContentValues();


                        nuevoRegistro.put("codigo", cod);
                        nuevoRegistro.put("nombre", nom);
                        nuevoRegistro.put("url", url);
                        nuevoRegistro.put("telefono", telefono);
                        nuevoRegistro.put("email", email);
                        nuevoRegistro.put("servicio", servicio);
                        nuevoRegistro.put("clasificacion", clasificacion);
                        db.insert("Usuarios", null, nuevoRegistro);
                        Cursor c = db.rawQuery("SELECT codigo, nombre, url, telefono, email, servicio, clasificacion FROM Usuarios", null);
                        Log.d("myTag", c.toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Los campos no deben ser nulos", Toast.LENGTH_LONG).show();
                }
            }
        });*/


        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();
                String nom = txtNombre.getText().toString();
                String url = txtUrl.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String servicio = txtServicio.getText().toString();
                String clasificacion = txtClasificacion.getText().toString();

                // Verifica si algún campo está vacío
                if (cod.isEmpty() || nom.isEmpty() || url.isEmpty() || telefono.isEmpty()
                        || email.isEmpty() || servicio.isEmpty() || clasificacion.isEmpty()) {

                    // Si algún campo está vacío, muestra un mensaje de error
                    Toast.makeText(MainActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();

                } else {
                    // Si todos los campos están llenos, procede a la inserción en la base de datos

                    // Si hemos abierto correctamente la base de datos
                    if (db != null) {
                        // Alternativa 2: método insert()
                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("codigo", cod);
                        nuevoRegistro.put("nombre", nom);
                        nuevoRegistro.put("url", url);
                        nuevoRegistro.put("telefono", telefono);
                        nuevoRegistro.put("email", email);
                        nuevoRegistro.put("servicio", servicio);
                        nuevoRegistro.put("clasificacion", clasificacion);

                        // Inserta el nuevo registro en la base de datos
                        db.insert("Usuarios", null, nuevoRegistro);

                    }
                }
            }
        });

        btnActualizar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                String cod = txtCodigo.getText().toString();

                //Recuperamos los valores de los campos de texto
                String nom = txtNombre.getText().toString();
                String url = txtUrl.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String email = txtEmail.getText().toString();
                String servicio = txtServicio.getText().toString();
                String clasificacion = txtClasificacion.getText().toString();




                //Alternativa 1: m�todo sqlExec()
                //String sql = "UPDATE Usuarios SET nombre='" + nom + "' WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: m�todo update()
                ContentValues valores = new ContentValues();
                valores.put("nombre", nom);
                valores.put("url",url);
                valores.put("telefono",telefono);
                valores.put("email",email);
                valores.put("servicio",servicio);
                valores.put("clasificacion",clasificacion);
                db.update("Usuarios", valores, "codigo=" + cod, null);
            }
        });

        btnEliminar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //Recuperamos los valores de los campos de texto
                String cod = txtCodigo.getText().toString();

                //Alternativa 1: m�todo sqlExec()
                //String sql = "DELETE FROM Usuarios WHERE codigo=" + cod;
                //db.execSQL(sql);

                //Alternativa 2: m�todo delete()
                db.delete("Usuarios", "codigo=" + cod, null);
            }
        });

        btnConsultar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //Alternativa 1: m�todo rawQuery()

                String cod1 = txtCodigo.getText().toString();

                String query = "SELECT * FROM Usuarios WHERE codigo = '" + cod1 + "'";
                Cursor info = db.rawQuery(query, null);

                if (info != null && info.moveToFirst()) {
                    txtCodigo.setText(info.getString(0));
                    txtNombre.setText(info.getString(1));
                    txtUrl.setText(info.getString(2));
                    txtTelefono.setText(info.getString(3));
                    txtEmail.setText(info.getString(4));
                    txtServicio.setText(info.getString(5));
                    txtClasificacion.setText(info.getString(6));
                    info.close();
                } else {
                    // Manejar el caso en el que no se encontró ningún resultado para el código dado
                    Toast.makeText(MainActivity.this, "No se encontró ningún registro para el código " + cod1, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnConsultarAll.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //Alternativa 2: m�todo delete()
                String[] campos = new String[] {"codigo", "nombre", "url", "telefono", "email", "servicio", "clasificacion"};
                Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

                //Recorremos los resultados para mostrarlos en pantalla
                txtResultado.setText("");
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya m�s registros
                    do {
                        String cod = c.getString(0);
                        String nom = c.getString(1);
                        String url = c.getString(2);
                        String telefono = c.getString(3);
                        String email = c.getString(4);
                        String servicio = c.getString(5);
                        String clasificacion = c.getString(6);


                        txtResultado.append(" " + cod + " - " + nom +
                                " - " + url + " - " + telefono +
                                " - " + email + " - " + servicio +
                                " - " + clasificacion +"\n");

                    } while(c.moveToNext());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
