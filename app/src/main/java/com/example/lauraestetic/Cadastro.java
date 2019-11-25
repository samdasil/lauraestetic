package com.example.lauraestetic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lauraestetic.classes.Servico;
import com.example.lauraestetic.dao.ServicoDAO;

import java.util.Calendar;
import java.util.Date;

public class Cadastro extends AppCompatActivity implements View.OnClickListener {

    private EditText editData;
    private EditText editCliente;
    private EditText editDescricao;
    private EditText editValor;
    private EditText editObs;
    private Spinner  referencia;
    private int codigo = 0;
    private  Button btnSalvar;
    private double valor;
    private String ref;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //getSupportActionBar().setTitle("Novo serviço");

        init();

        editData.setOnClickListener(this);

        String[] lsRef = getResources().getStringArray(R.array.lista_mes);
        referencia.setAdapter(new ArrayAdapter<String>(Cadastro.this, R.layout.support_simple_spinner_dropdown_item, lsRef));
        referencia.setSelection(new Date().getMonth());
        for (int i=0; i < lsRef.length; i++){
            if(lsRef[i].equals(new Date().getMonth())){
                referencia.setSelection(i);
            }
        }

        referencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selecionaMes(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editData.getText().toString().equals("")) {
                    editData.requestFocus();
                    Toast.makeText(Cadastro.this, "Preencha o campo de data", Toast.LENGTH_SHORT).show();
                } else if (editCliente.getText().toString().equals("")) {
                    editCliente.requestFocus();
                    Toast.makeText(Cadastro.this, "Preencha o nome do cliente", Toast.LENGTH_SHORT).show();
                } else if (editDescricao.getText().toString().equals("")) {
                    editDescricao.requestFocus();
                    Toast.makeText(Cadastro.this, "Preencha o serviço", Toast.LENGTH_SHORT).show();
                } else {
                    //Servico servico = new Servico();

                    if (editValor.getText().toString().equals("")) {
                        valor = 0.00;
                    } else {
                        valor = Double.parseDouble(editValor.getText().toString());
                    }

                    Servico serv = new Servico();

                    serv.setCodigo(codigo);
                    serv.setData(editData.getText().toString());
                    serv.setCliente(editCliente.getText().toString());
                    serv.setDescricao(editDescricao.getText().toString());
                    serv.setValor(valor);
                    serv.setObs(editObs.getText().toString());
                    serv.setReferencia(ref);

                    ServicoDAO servDao = new ServicoDAO(getApplicationContext());
                    if(servDao.salvar(serv)){
                        Toast.makeText(Cadastro.this, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        AlertDialog.Builder dialogSalvar = new AlertDialog.Builder(Cadastro.this);
                        dialogSalvar.setTitle("Erro");
                        dialogSalvar.setMessage("Não foi possível excluir o serviço.");
                        dialogSalvar.setIcon(R.drawable.ic_warning_yellow_24dp);
                        dialogSalvar.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                    }

                }

            }

        });

    }

    public void init(){
        editData      = findViewById( R.id.editData);
        editCliente   = findViewById( R.id.editCliente);
        editDescricao = findViewById( R.id.editDescServico);
        editValor     = findViewById( R.id.editValor);
        editObs       = findViewById( R.id.editObs );
        btnSalvar     = findViewById( R.id.btnSalvar);
        referencia    = findViewById( R.id.spinnerReferencia );
    }

    @Override
    protected void onResume() {
        Servico servico = (Servico) getIntent().getSerializableExtra("servico");

        if (servico != null) {
            init();
            editData.setText(servico.getData());
            editCliente.setText(servico.getCliente());
            editDescricao.setText(servico.getDescricao());
            editValor.setText(servico.getValor().toString());
            editObs.setText(servico.getObs());
            referencia.setSelection(selecionaPosMes(servico.getReferencia()));
            codigo = servico.getCodigo();
        }

        super.onResume();
    }

    @Override
    public void onClick(View view) {

            if (view == editData){

            final Calendar c = Calendar.getInstance();
            mYear  = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay   = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String dia = String.valueOf(dayOfMonth);
                    String mes = String.valueOf(monthOfYear+1);

                    if (dia.length() < 2) {
                        dia = "0"+dia;
                    }
                    if (mes.length() < 2) {
                        mes = "0"+mes;
                    }
                    editData.setText(dia + "/" + mes + '/' + year);
                    editCliente.requestFocus();
                }

            }, mYear, mMonth, mDay);

            datePickerDialog.show();
        }

    }


    private int selecionaPosMes(String refer) {

        switch (refer){
            case "Janeiro":
                return 0;
            case "Favereiro":
                return 1;
            case "Março":
                return 2;
            case "Abril":
                return 3;
            case "Maio":
                return 4;
            case "Junho":
                return 5;
            case "Julho":
                return 6;
            case "Agosto":
                return 7;
            case "Setembro":
                return 8;
            case "Outubro":
                return 9;
            case "Novembro":
                return 10;
            case "Dezembro":
                return 11;
            default:
                //Toast.makeText(this, "Erro ao selecionar mês.", Toast.LENGTH_SHORT).show();
                break;
        }
        return 0;
    }

    private void selecionaMes(int i) {

        switch (i+1){
            case 1:
                ref = "Janeiro";
                break;
            case 2:

                ref = "Favereiro";
                break;
            case 3:
                ref = "Março";
                break;
            case 4:
                ref = "Abril";
                break;
            case 5:
                ref = "Maio";
                break;
            case 6:
                ref = "Junho";
                break;
            case 7:
                ref = "Julho";
                break;
            case 8:
                ref = "Agosto";
                break;
            case 9:
                ref = "Setembro";
                break;
            case 10:
                ref = "Outubro";
                break;
            case 11:
                ref = "Novembro";
                break;
            case 12:
                ref = "Dezembro";
                break;
            default:
                Toast.makeText(this, "Erro ao selecionar mês.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
