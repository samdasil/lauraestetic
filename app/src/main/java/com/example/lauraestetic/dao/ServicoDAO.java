package com.example.lauraestetic.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.example.lauraestetic.classes.Servico;
import com.example.lauraestetic.helper.DbHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public ServicoDAO(Context context) {
        DbHelper db = new DbHelper( context);
        escrever = db.getWritableDatabase();
        ler      = db.getWritableDatabase();
    }

    public boolean salvar(Servico servico) {

        ContentValues cv = new ContentValues();

        cv.put("data", servico.getData());
        cv.put("cliente", servico.getCliente());
        cv.put("descricao", servico.getDescricao());
        cv.put("valor", servico.getValor());
        cv.put("obs", servico.getObs());
        cv.put("referencia", servico.getReferencia());

        if (servico.getCodigo() != 0){
            cv.put("codigo", servico.getCodigo());
            String[] args = {String.valueOf(servico.getCodigo())};
            return escrever.update(DbHelper.TB_SERVICO,cv, "codigo = ?", args) > 0;
        } else {
            return escrever.insert(DbHelper.TB_SERVICO, null, cv) > 0;
        }


    }

    public boolean deletar(int codigo) {

        String[] args = new String[] {String.valueOf(codigo)};

        return escrever.delete(DbHelper.TB_SERVICO, "codigo = ?", args) > 0;
    }

    public List<Servico> listar(String referencia, String ano){

        List<Servico> servicos = new ArrayList<>();
        String[] args;
        String sql;

        if (referencia == null){
            args = new String[] {ano};
            sql  = "SELECT * FROM " + DbHelper.TB_SERVICO +" WHERE substr(data,7,4) = ? ";
        } else if (ano == null) {
            args = null;
            sql  = "SELECT * FROM " + DbHelper.TB_SERVICO + " WHERE referencia = ? ";
        } else {
            args = new String[] {referencia, ano};
            sql  = "SELECT * FROM " + DbHelper.TB_SERVICO +" WHERE referencia = ? AND  substr(data,7,4) = ? ";
        }

        Cursor c    = ler.rawQuery( sql,args );

        while ( c.moveToNext() ) {

            Servico servico = new Servico();

            servico.setCodigo( c.getInt( c.getColumnIndex( "codigo")));
            servico.setData( c.getString( c.getColumnIndex( "data")));
            servico.setCliente( c.getString( c.getColumnIndex( "cliente")));
            servico.setDescricao( c.getString( c.getColumnIndex( "descricao")));
            servico.setValor( c.getDouble( c.getColumnIndex( "valor")));
            servico.setObs( c.getString( c.getColumnIndex( "obs")));
            servico.setReferencia( c.getString( c.getColumnIndex( "referencia")));
            Log.i("INFO", "CODIGO = " +servico.getCodigo() + " VALOR = " + servico.getValor());
            Log.i("INFO", "REFER = " + servico.getReferencia());
            servicos.add( servico );

        }

        c.close();
        return servicos;

    }

    public Double valorTotal(String referencia, String ano){

        double total = 0;

        List<Servico> list = listar(referencia, ano);

        for(int i=0; i < list.size(); i++){
            if(list.get(i).getReferencia().equals(referencia) && list.get(i).getData().substring(6,10).equals(ano)){
                total += list.get(i).getValor();
            }
        }

//        NumberFormat format = new DecimalFormat(".##");
//
//        total = Double.parseDouble(format.format(total));

        return total;
    }

}
