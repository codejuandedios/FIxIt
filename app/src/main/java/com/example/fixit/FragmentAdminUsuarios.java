package com.example.fixit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminUsuarios extends Fragment {

    View vista;
    EditText carne, email, nombre, apellido, txtbuscar;
    Button btnbuscar, btnactualizar;
    Spinner spinnerRol, spinnerEstado;
    View vistaR;
    private EditText buscarRol;
    private Button btnBuscarRol;
    private EditText txtCarne;
    private EditText txtCorreo;
    private EditText txtNombre;
    private EditText txtApellido;
    private Spinner spinnerRol;
    private Spinner spinnerEstado;
    ;

    public FragmentAdminUsuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_admin_usuarios, container, false);
        carne = vista.findViewById(R.id.txt_carne);
        email = vista.findViewById(R.id.txtCorreo);
        nombre = vista.findViewById(R.id.txt_nombre);
        apellido = vista.findViewById(R.id.txt_apellido);
        txtbuscar = vista.findViewById(R.id.lblBuscar);
        btnbuscar = vista.findViewById(R.id.buscar);
        btnactualizar = vista.findViewById(R.id.actualizar);
        btnbuscar = vista.findViewById(R.id.buscar);
        spinnerEstado = vista.findViewById(R.id.SpinnerEstado);
        spinnerRol = vista.findViewById(R.id.SpinnerRol);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }

        });

        return vista;
    }

    public void buscar(){
        String sql = "select * from usuario where carne = "+Integer.parseInt(txtbuscar.getText().toString());
        Statement st = null;
        ResultSet rs = null;
        Usuario usuario = null;


        try{
            st = conexionBD().createStatement();
            rs = st.executeQuery(sql);
            if(rs.first())
            {
                do
                {
                    usuario = new Usuario(rs.getInt("carne"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"), rs.getString("password"), rs.getInt("id_rol"), rs.getInt("estado"));
                }while(rs.next());
            }
            carne.setText(String.valueOf(usuario.getCarne()));
            email.setText(usuario.getEmail());
            nombre.setText(usuario.getNombre());
            apellido.setText(usuario.getApellido());

        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());


        }
    }

    public Connection conexionBD(){
        Connection conexion = null;
        String host = "192.168.1.27";
        String port = "3306";
        String dbName = "fixit";
        String userName = "root";
        String password = "admon";
        try{

            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, userName, password);

        }catch (Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }
        vistaR = inflater.inflate(R.layout.fragment_fragment_admin_usuarios, container, false);
        spinnerRol = (Spinner) vistaR.findViewById(R.id.spinnerRol);
        spinnerEstado = (Spinner) vistaR.findViewById(R.id.spinnerEstado);
        buscarRol = (EditText) vistaR.findViewById(R.id.txtBuscarCarne);
        btnBuscarRol = (Button) vistaR.findViewById(R.id.btnBuscarCarne);
        txtCarne = (EditText) vistaR.findViewById(R.id.txtcarneRol);
        txtCorreo = (EditText) vistaR.findViewById(R.id.txtCorreoRol);
        txtNombre = (EditText) vistaR.findViewById(R.id.txtNombreRol);
        txtApellido = (EditText) vistaR.findViewById(R.id.txtApellidoRol);


        /* String [] values =
                {"Selecine el rol","1. Usuario","2. Moderador","3. Administrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRol.setAdapter(adapter);
        */
        //__________Spinner de ROl____________
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.TipodeRolUsuario, android.R.layout.simple_spinner_item);
        spinnerRol.setAdapter(adapter);

        //__________Spinner de Estado_______
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.EstadoUsuario, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter1);

        return vistaR;
    }
}
