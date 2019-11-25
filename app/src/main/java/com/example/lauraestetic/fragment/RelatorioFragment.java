package com.example.lauraestetic.fragment;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lauraestetic.Home;
import com.example.lauraestetic.R;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.lauraestetic.classes.Servico;
import com.example.lauraestetic.dao.ServicoDAO;
//import com.anychart.sample.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatorioFragment extends Fragment {

    private Spinner mes;
    private Spinner ano;
    private Toolbar toolbar;
    private TextView tvHomeValor;
    private TextView tvHomeQtd;
    private TextView lbHomeValor;
    private TextView lbHomeQtd;
    private AnyChartView anyChartView;

    public RelatorioFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout
        View view =  inflater.inflate(R.layout.fragment_relatorio, container, false);

        // Inicializador de ids
        initialize(view);

        // toolbar
        ((Home) getActivity()).getSupportActionBar().setTitle("Relatório");
        ((Home) getActivity()).findViewById(R.id.fab).setVisibility(View.INVISIBLE);

        // carregar Spinner
        getSpinnerAno();

        // construct chart
        //loadDadosGraph();

        // show graph
        //exibirGrafico(data);

        return view;

    }

    public void initialize(View view){
        ano             = view.findViewById( R.id.spinnerAno );
        mes             = view.findViewById( R.id.spinnerReferencia );
        toolbar         = view.findViewById( R.id.toolbar );
        tvHomeValor     = view.findViewById( R.id.tvHomeValorTotal );
        tvHomeQtd       = view.findViewById( R.id.tvHomeQtd );
        lbHomeQtd       = view.findViewById( R.id.lbHomeAtendimento );
        lbHomeValor     = view.findViewById( R.id.lbHomeValor );
        anyChartView    = view.findViewById(R.id.any_chart_view);

        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));
        tvHomeQtd.setVisibility(View.INVISIBLE);
        tvHomeValor.setVisibility(View.INVISIBLE);
        lbHomeQtd.setVisibility(View.INVISIBLE);
        lbHomeValor.setVisibility(View.INVISIBLE);
        mes.setVisibility(View.INVISIBLE);
        ano.setGravity(View.TEXT_ALIGNMENT_CENTER);
        ano.layout(0, 0,0, 0);

    }

    public void getSpinnerAno(){
        String[] lsAno = getResources().getStringArray(R.array.lista_ano);
        ano.setAdapter(new ArrayAdapter<String>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, lsAno));
        for (int i=0; i < lsAno.length; i++){
            if(lsAno[i].equals(new Date().getYear())){
                ano.setSelection(i);
            }
        }
        ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ano.setSelection(i);
                loadDadosGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadDadosGraph(){

        ServicoDAO servDao = new ServicoDAO(this.getContext());

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Jan", servDao.valorTotal("Janeiro"  , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Fev", servDao.valorTotal("Fevereiro", ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Mar", servDao.valorTotal("Março"    , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Abr", servDao.valorTotal("Abril"    , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Mai", servDao.valorTotal("Maio"     , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Jun", servDao.valorTotal("Junho"    , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Jul", servDao.valorTotal("Julho"    , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Ago", servDao.valorTotal("Agosto"   , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Set", servDao.valorTotal("Setembro" , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Out", servDao.valorTotal("Outubro"  , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Nov", servDao.valorTotal("Novembro" , ano.getSelectedItem().toString())));
        data.add(new ValueDataEntry("Dez", servDao.valorTotal("Dezembro" , ano.getSelectedItem().toString())));

        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("R$ {%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Relatório Anual");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("R$ {%Value}{groupsSeparator: }");
        cartesian.labels(true);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        //cartesian.xAxis(0).title("Meses");
        //cartesian.yAxis(0).title("");

        anyChartView.setChart(cartesian);

    }


}
