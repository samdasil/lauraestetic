package com.example.lauraestetic.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    // DADOS DO BANCO
    private static final String DATABASE = "laurestetic.db";
    private static final int    VERSION  = 4;

    // TABELAS DO BANCO DE DADOS SQLITE
    public static final String TB_SERVICO        = "tb_servico";


    // construtor
    public DbHelper(Context ctx) {
        super(ctx, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.criarTabelas(db);
        Log.i("INFO DB", "BANCO DE DADOS CRIADO COM SUCESSO");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion){
            case 1:
                db.execSQL( "ALTER TABLE " + TB_SERVICO + " ADD COLUMN referencia INTEGER " );
                break;
            case 4:
                db.execSQL( "ALTER TABLE " + TB_SERVICO + " ADD COLUMN teste INTEGER " );
                Log.i("INFO","coluna teste adicionada");
                break;
            default:
                Log.i("INFO", "Nenhuma alteração encontrada");
                break;
        }

    }

    // executador de rotinas
    public SQLiteDatabase getConexaoDatabase() {
        return this.getWritableDatabase();
    }

    private void criarTabelas( SQLiteDatabase db){

        // tabelas não relacionadas diretamente
        // seguindo padrão das classes models

        // estrutura tabela servico
        StringBuilder tbservico = new StringBuilder();
        tbservico.append(" CREATE TABLE " + TB_SERVICO + "(      ");
        tbservico.append("    codigo       INTEGER PRIMARY KEY AUTOINCREMENT,   ");
        tbservico.append("    data         VARCHAR(10) NOT NULL,    ");
        tbservico.append("    cliente      VARCHAR(30) NOT NULL,    ");
        tbservico.append("    descricao    VARCHAR(100) NOT NULL,   ");
        tbservico.append("    valor        DOUBLE ,                 ");
        tbservico.append("    obs          VARCHAR(200) ,           ");
        tbservico.append("    referencia   VARCHAR(10) )            ");

        // criação das tabelas
        try{
            db.execSQL( tbservico.toString() );
            Log.i("INFO DB", "Tabela Servico criada com sucesso.");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela Servico.");
        }

    }

}
