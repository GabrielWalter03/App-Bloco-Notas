package arantes.walter.gabriel.faculdadeagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.database.sqlite.SQLiteDatabase; //Banco da dados


public class MainActivity extends AppCompatActivity {

    EditText et_texto, et_assunto;
    Button btn_gravar, btn_consultar, btn_fechar;

    SQLiteDatabase db =null;
    Mensagem msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_texto = findViewById(R.id.et_nome);
        et_assunto = findViewById(R.id.et_texto);
        btn_gravar = findViewById(R.id.btn_gravar);
        btn_consultar = findViewById(R.id.btn_consultar);
        btn_fechar = findViewById(R.id.btnc_fechar);

        BancoDeDados.abrirBanco(this); // vai abrir o banco de dados
        BancoDeDados.AbrirOuCriartabela(this); // vai criar a tebla quando abrir o app(OnCreate)

    }

    public void inserirRegistro(View v) { // para associar essa função a algum botão tem que colocar um parâmetro view
        String st_assunto, st_texto;
        st_assunto = et_texto.getText().toString();
        st_texto = et_assunto.getText().toString();
        if(st_assunto == "" || st_texto == "") {
            msg.mostrar("Campos precisam ser preenchidos", this);
            return;
        }

        BancoDeDados.inserirRegistro(st_assunto, st_texto, this);

        et_texto.setText(null);
        et_assunto.setText(null); // dps de fechar o banco tem que limpar os campos
    }

    public void abrirTelaConsulta(View v) {
        Intent it = new Intent(this, TelaConsultaActivity.class);
        startActivity(it);
    }

    public void fecharTela(View v) {
        this.finish();
    }

}