    package com.example.campuscamarafp;

    import android.content.ContentValues;
    import android.content.Intent;
    import android.content.pm.ActivityInfo;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.campuscamarafp.entidades.Alumno;
    import com.example.campuscamarafp.entidades.Profesor;
    import com.example.campuscamarafp.utilidades.Utilidades;

    import java.util.ArrayList;
    import java.util.List;

    public class PasarLista extends AppCompatActivity {

        ArrayAdapter<String> adaptador;
        private Button btn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pasarlista);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            ListView lv = (ListView)findViewById(R.id.lista);
            final ArrayList<Alumno> lista;

            lista = llenar_lv();
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista);
            lv.setAdapter(adaptador);

            selectAlumnos();
        }

        public void selectAlumnos(){
            final ListView lv = (ListView)findViewById(R.id.lista);
            btn = (Button)findViewById(R.id.btnPasarLista);
            AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
            SQLiteDatabase bd = conexion.getWritableDatabase();
            Cursor fila = bd.rawQuery("select correo from alumnos", null);
            Alumno alumno = new Alumno();
            lv.setOnItemClickListener((parent, view, position, id) -> {
                if (fila.moveToPosition(position)) {
                    String correo = fila.getString(0);
                    alumno.setCorreo(correo);
                    Toast.makeText(PasarLista.this, "Correo: " + alumno.getCorreo() , Toast.LENGTH_SHORT).show();
                }
            });
            btn.setOnClickListener(v -> {
                bd.execSQL("update alumnos set faltas = faltas + '" + 1
                        + "' where correo = '" + alumno.getCorreo() + "';");
                Toast.makeText(PasarLista.this, "Hola ", Toast.LENGTH_SHORT).show();
            });
        }

        public ArrayList llenar_lv(){
            ArrayList<String> lista = new ArrayList<>();
            AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
            SQLiteDatabase bd = conexion.getWritableDatabase();

            String tabla_lista = "select nombre, apellidos from alumnos";
            Cursor registro = bd.rawQuery(tabla_lista, null);
            if(registro.moveToFirst()){
                do{
                    lista.add(registro.getString(0)
                            + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                            + registro.getString(1));
                }while(registro.moveToNext());
            }
            return lista;
        }

        //método que da paso a la actividad Perfil
        public void Perfil (){
            AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
            SQLiteDatabase bd = conexion.getWritableDatabase();

            Bundle objEnviado = getIntent().getExtras();
            Profesor profesorRecibe;
            profesorRecibe = (Profesor) objEnviado.getSerializable("profesor_iniciosesion");

            Cursor fila = bd.rawQuery("select * from " + Utilidades.TABLA_PROFESORES
                            + " where " + Utilidades.CAMPO_CORREO_PROFESORES + " = '" + profesorRecibe.getCorreo()
                            + "'"
                    , null);
            //corrección de errores
            try{
                if(fila.moveToFirst()){
                    Profesor profesorEnvia = new Profesor();
                    String nom = fila.getString(0);
                    profesorEnvia.setNombre(nom);
                    String ape = fila.getString(1);
                    profesorEnvia.setApellidos(ape);
                    String cor= fila.getString(2);
                    profesorEnvia.setCorreo(cor);
                    String pass = fila.getString(3);
                    profesorEnvia.setPassword(pass);
                    String cur = fila.getString(4);
                    profesorEnvia.setCurso(cur);

                    Intent i = new Intent(this, Perfil.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("datos_profesores", profesorEnvia);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            } catch (Exception e) {//capturamos los errores si hubieran
                Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        //método que muestra los botones de acción
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menuacciones, menu);
            return true;
        }
        //método para agregar las acciones de los botones de acción
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()) {
                case R.id.imgperfil:
                    Perfil();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }
