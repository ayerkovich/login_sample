package com.donweb.demoapp;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityMain extends Activity {
	private static final String USUARIO = "donweb";
	private static final String PASSWORD = "123";
	private ImageView ivLogo;
	private EditText etUsuario;
	private EditText etPassword;
	private Button btIngresar;
	private LinearLayout llLogo;
	private LinearLayout llInputs;
	private LinearLayout llButtons;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Contexto
		ctx = this;
		
		// Referencias a Views
		ivLogo = (ImageView)findViewById(R.id.ivLogo);
		etUsuario = (EditText)findViewById(R.id.etUsuario);
		etPassword = (EditText)findViewById(R.id.etPassword);
		btIngresar = (Button)findViewById(R.id.btIngresar);
		llLogo = (LinearLayout)findViewById(R.id.llLogo);
		llInputs = (LinearLayout)findViewById(R.id.llInputs);
		llButtons = (LinearLayout)findViewById(R.id.llButton);
		// Definir listener onClick sobre el boton
		btIngresar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String usuario = etUsuario.getText().toString();
				String password = etPassword.getText().toString();
				
				if(usuario.equals("") || password.equals("")){
					Toast.makeText(ActivityMain.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
				} else if (usuario.equals(USUARIO) && password.equals(PASSWORD)){
					Toast.makeText(ActivityMain.this, "Login exitoso!", Toast.LENGTH_LONG).show();
					setStringSharedPref("str_usuario", usuario, ctx);
					setStringSharedPref("str_password", password, ctx);
				}
				else {
					Toast.makeText(ActivityMain.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
					animacionDeLogin();
				}
			}
		});

		// Chequear si nombre de usuario y password ya estan almacenados localmente.
		String usuario = getStringSharedPref("str_usuario", ctx);
		String password = getStringSharedPref("str_password", ctx);
		if (!usuario.equals("") && !password.equals("")) {
			// Si ya estan almacenados, completar campos de formulario
			etUsuario.setText(usuario);
			etPassword.setText(password);
		}
		
		// Animar Views.
		animacionDeEntrada();
	}
	
	private void animacionDeEntrada(){
		// Ocultar todas las vistas
		llLogo.setVisibility(View.INVISIBLE);
		llInputs.setVisibility(View.INVISIBLE);
		llButtons.setVisibility(View.INVISIBLE);
		
		// Cargar la animacion de entrada
		final Animation animEntradaLogo = AnimationUtils.loadAnimation(this, R.anim.animentrada);
		final Animation animEntradaInputs = AnimationUtils.loadAnimation(this, R.anim.animentrada);
		final Animation animEntradaButtons = AnimationUtils.loadAnimation(this, R.anim.animentrada);
		
		// Definir un tiempo antes de que se inicien las animaciones y el tiempo entre la animacion de cada vista
		final long retrasoInicial = 1000;
		final long retrasoEntreAnimaciones = 150;
		
		// Primero animar el logo de Donweb (empezar luego de 500 milisegundos para que se pueda apreciar bien la animacion)
		animEntradaLogo.setStartOffset(retrasoInicial);
		llLogo.startAnimation(animEntradaLogo);
		
		// Continuar con la animacion del resto de las vistas con una diferencia de "retrasoEntreAnimaciones" entre cada una de ellas
		animEntradaInputs.setStartOffset(animEntradaLogo.getStartOffset()+retrasoEntreAnimaciones);
		llInputs.startAnimation(animEntradaInputs);
		
		animEntradaButtons.setStartOffset(animEntradaInputs.getStartOffset()+retrasoEntreAnimaciones);
		llButtons.startAnimation(animEntradaButtons);
	}
	
	private void animacionDeLogin(){
		
		// Cargar la animacion shake
		final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
		
		// Animar formulario
		etUsuario.startAnimation(animShake);
		etPassword.startAnimation(animShake);
	}
	
	private static void setStringSharedPref(String name, String value, Context ctx)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name,value);
		editor.commit();
	}
	
	private static String getStringSharedPref(String name,Context ctx)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		String value = preferences.getString(name,"");
		return value;
	}
}
