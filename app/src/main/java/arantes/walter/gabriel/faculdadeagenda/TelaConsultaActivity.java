package arantes.walter.gabriel.faculdadeagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TelaConsultaActivity extends AppCompatActivity {


    SQLiteDatabase db = null;
    Cursor cursor;
    TextView tv_data_consulta;
    EditText et_assunto, et_texto;
    Button btn_anterior, btn_voltar, btn_proximo, btn_excluir;

    Mensagem msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta);

        et_assunto = findViewById(R.id.et_nome_consulta);
        et_texto = findViewById(R.id.et_textoConsulta);
        btn_anterior = findViewById(R.id.btn_anterior);
        btn_voltar = findViewById(R.id.btn_voltar_consulta);
        btn_proximo = findViewById(R.id.btn_proximo);
        btn_excluir = findViewById(R.id.btn_excluir);
        tv_data_consulta = findViewById(R.id.tv_data_consulta);


        buscarDados();

    }

    public void fecharTela(View v) {
        this.finish();
    }

    public void abrirBanco() { // função qur abri o banco
        try {
            db = openOrCreateDatabase("BancoNotas", MODE_PRIVATE, null);
        } catch (Exception error) {
            msg.mostrar("Erro ao abrir ou criar banco de dados", this);
        }
    }

    public void fecharDB() {
        db.close();
    }

    public void buscarDados() {
        abrirBanco();
        cursor = db.query("notas",
                new String[]{"assunto", "texto", "data_hora"},
                null, null, null, null, null, null
        );

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            mostrarDados();
        } else {
            msg.mostrar("Nenhum registro encontrado", this);
        }
    }

    public void proximoRegistro(View v) {
        try {
            cursor.moveToNext();
            mostrarDados();
        } catch (Exception error) {
            if (cursor.isAfterLast()) {
                msg.mostrar("Não existem mais registros", this);
            } else {
                msg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }

    public void registroAnterior(View v) {
        try {
            cursor.moveToPrevious();
            mostrarDados();
        } catch (Exception error) {
            if (cursor.isBeforeFirst()) {
                msg.mostrar("Não existem mais registros", this);
            } else {
                msg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }

    public void mostrarDados() {
        et_assunto.setText(cursor.getString(cursor.getColumnIndexOrThrow("assunto")));
        et_texto.setText(cursor.getString(cursor.getColumnIndexOrThrow("texto")));

        // Adiciona a data ao TextView correspondente
        String dataRegistro = cursor.getString(cursor.getColumnIndexOrThrow("data_hora"));
        tv_data_consulta.setText("Data: " + dataRegistro);
    }


    public void excluirRegistro(View v) {
        if (cursor != null) {
            abrirBanco();
            try {
                String assunto = cursor.getString(cursor.getColumnIndexOrThrow("assunto"));
                String texto = cursor.getString(cursor.getColumnIndexOrThrow("texto"));

                int deletados = db.delete("notas", "assunto=? AND texto=?", new String[]{assunto, texto});

                if (deletados > 0) {
                    msg.mostrar("Registro excluído com sucesso", this);
                    et_assunto.setText("");
                    et_texto.setText("");
                    tv_data_consulta.setText(""); // Limpar o TextView da data
                    // Aqui você pode adicionar a lógica para ir para o próximo registro se necessário
                } else {
                    msg.mostrar("Registro não encontrado ou erro ao excluir", this);
                }
            } catch (Exception error) {
                msg.mostrar("Erro ao excluir registro", this);
            } finally {
                fecharDB();
            }
        } else {
            msg.mostrar("Nenhum registro para excluir", this);
        }
    }
}