package arantes.walter.gabriel.faculdadeagenda;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class BancoDeDados {

    static SQLiteDatabase db = null;
    static Cursor cursor;

    public static void abrirBanco(Activity act) {
        try {
            Context context = act.getApplicationContext();
            db = context.openOrCreateDatabase("BancoNotas", Context.MODE_PRIVATE, null);
        } catch (Exception error) {
            Mensagem.mostrar("Erro ao abrir ou criar banco de dados", act);
        }
    }

    public static void fecharDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public static void AbrirOuCriartabela(Activity act) {
        try {
            // Exclui a tabela se ela existir
            db.execSQL("DROP TABLE IF EXISTS notas");

            // Cria a tabela
            db.execSQL("CREATE TABLE IF NOT EXISTS notas(id INTEGER PRIMARY KEY, assunto TEXT, texto TEXT, data_hora DATETIME);");
        } catch (Exception error) {
            Mensagem.mostrar("Erro ao criar tabela", act);
        }
    }

    public static void inserirRegistro(String assunto, String texto, Activity act) {
        abrirBanco(act);
        try {
            // Obtém a data e o horário atual
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataHoraAtual = dateFormat.format(new Date(System.currentTimeMillis()));

            // Insere o registro no banco de dados com a data e o horário
            db.execSQL("INSERT INTO notas(assunto, texto, data_hora) VALUES('" + assunto + "', '" + texto + "', '" + dataHoraAtual + "')");
        } catch (Exception error) {
            Mensagem.mostrar("Erro ao inserir registro", act);
        } finally {
            Mensagem.mostrar("Registro inserido", act);
            fecharDB();
        }
    }

    public static Cursor buscarTodosDados(Activity act) {
        abrirBanco(act);
        cursor = db.query("notas",
                new String[]{"assunto", "texto", "data_hora"},
                null, null, null, null, null, null
        );
        return cursor;
    }
}