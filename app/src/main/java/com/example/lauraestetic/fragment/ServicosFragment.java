package com.example.lauraestetic.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lauraestetic.Cadastro;
import com.example.lauraestetic.Home;
import com.example.lauraestetic.R;
import com.example.lauraestetic.adapter.ItemAdapter;
import com.example.lauraestetic.classes.Servico;
import com.example.lauraestetic.dao.ServicoDAO;
import com.example.lauraestetic.helper.RecyclerItemClickListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicosFragment extends Fragment {

    private static final String FILE_USER_DATA = "DataUser";
    private RecyclerView recyclerServico;
    private TextView tvHomeValor;
    private TextView tvHomeQtd;
    private Spinner mes;
    private Spinner ano;
    private Toolbar toolbar;
    private List<Servico> listServico = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private String hoje;
    private int mesAtual;
    private int anoAtual;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    public ServicosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_servicos, container, false);

        // toolbar
        ((Home) getActivity()).getSupportActionBar().setTitle("Serviços");
        ((Home) getActivity()).findViewById(R.id.fab).setVisibility(View.VISIBLE);

        // initialize
        initialize(view);

        // carregar data atual formatada
        getDataAtualFormat();

        // carregar spinners
        getSpinnerMes();
        getSpinnerAno();

        // listar servicos
        carregarServicos();

        return view;
    }

    @Override
    public void onResume() {
        listarServicos();
        super.onResume();
    }

    public void initialize(View view){
        recyclerServico = view.findViewById( R.id.recyclerServicos );
        tvHomeValor     = view.findViewById( R.id.tvHomeValorTotal );
        tvHomeQtd       = view.findViewById( R.id.tvHomeQtd );
        mes             = view.findViewById( R.id.spinnerReferencia);
        ano             = view.findViewById( R.id.spinnerAno );
        toolbar         = view.findViewById( R.id.toolbar );
    }

    public void getSpinnerMes(){
        String[] lsMes = getResources().getStringArray(R.array.lista_mes);

        mes.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, lsMes));
        mes.setSelection(new Date().getMonth());
        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 selecionaMes(i);
                 listarServicos();
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });

    }

    public void getSpinnerAno(){
        String[] lsAno = getResources().getStringArray(R.array.lista_ano);
        ano.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, lsAno));
        for (int i=0; i < lsAno.length; i++){
            if(lsAno[i].equals(new Date().getYear())){
                ano.setSelection(i);
            }
        }
        ano.getSelectedItemPosition();
        ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listarServicos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void selecionaMes(int i) {

        switch (i+1){
            case 1:
                Toast.makeText(getContext(), "Janeiro", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getContext(), "Favereiro", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getContext(), "Março", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getContext(), "Abril", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(getContext(), "Maio", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(getContext(), "Junho", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(getContext(), "Julho", Toast.LENGTH_SHORT).show();
                break;
            case 8:
                Toast.makeText(getContext(), "Agosto", Toast.LENGTH_SHORT).show();
                break;
            case 9:
                Toast.makeText(getContext(), "Setembro", Toast.LENGTH_SHORT).show();
                break;
            case 10:
                Toast.makeText(getContext(), "Outubro", Toast.LENGTH_SHORT).show();
                break;
            case 11:
                Toast.makeText(getContext(), "Novembro", Toast.LENGTH_SHORT).show();
                break;
            case 12:
                Toast.makeText(getContext(), "Dezembro", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getContext(), "Erro ao selecionar mês.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void getDataAtualFormat(){
        hoje = formatDate.format(new Date());
        mesAtual = new Date().getMonth()+1;
        anoAtual = new Date().getYear();
    }

    public void carregarServicos(){

        // configurar layout do recycler view
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getContext(), 1 );
        recyclerServico.setLayoutManager( layoutManager );

        listarServicos();

        // recycler de serviços
        recyclerServico.addOnItemTouchListener( new RecyclerItemClickListener(getContext(), recyclerServico, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                abrirServico(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                deletarItem( position );
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(CargaActivity.this, "Info solicitada no adapter", Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void listarServicos() {

        ServicoDAO servDao = new ServicoDAO( getContext() );
        listServico = servDao.listar(mes.getSelectedItem().toString(), ano.getSelectedItem().toString());

        itemAdapter = new ItemAdapter( listServico );

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getContext(), 1 );
        recyclerServico.setLayoutManager( layoutManager );
        recyclerServico.setAdapter( itemAdapter );
        tvHomeQtd.setText(String.valueOf(listServico.size()));
        tvHomeValor.setText(String.valueOf(servDao.valorTotal(mes.getSelectedItem().toString(), ano.getSelectedItem().toString())));

        Log.i("INFO", "MES = " + mes.getSelectedItem().toString());
        Log.i("INFO", "ANO = " + ano.getSelectedItem().toString());

    }

    private void deletarItem(final int position) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        dialog.setTitle("Deletar serviço");
        dialog.setMessage("Deseja deletar o serviço ?");

        //configurar cancelamento
        dialog.setCancelable(false);

        //configurar icone da mensagem
        dialog.setIcon(R.drawable.ic_warning_red_24dp);

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Servico serv = new Servico();
                serv = listServico.get(position);

                ServicoDAO servDao = new ServicoDAO(getContext());
                if (servDao.deletar(serv.getCodigo())) {
                    Toast.makeText(getContext(), "Item deletado com sucesso", Toast.LENGTH_LONG).show();
                    listarServicos();
                } else {
                    Toast.makeText(getContext(), "Não foi possível deletar o serviço.", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.create();
        dialog.show();

    }

    public void abrirServico(int position){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        final Servico servico = listServico.get(position);

        dialog.setTitle("Serviço");
        dialog.setMessage(
                "Data       : " + servico.getData()     + "\n" +
                "Cliente   : " + servico.getCliente()  + "\n" +
                "Serviço  : " + servico.getDescricao()+ "\n" +
                "Valor      : R$ " + servico.getValor() + "\n" +
                "Obs.       : " + servico.getObs());

        dialog.setIcon(R.drawable.ic_attach_money_black_24dp);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        dialog.setNeutralButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getContext(), Cadastro.class);
                intent.putExtra("servico", (Serializable) servico);
                startActivity(intent);
            }
        });

        // exibir dialog
        dialog.create();
        dialog.show();
    }

}
