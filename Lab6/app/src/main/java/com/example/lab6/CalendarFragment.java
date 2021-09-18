package com.example.lab6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;

    // вызывается когда фрагмент связывается с активностью
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // получаем слушатель для отправки данных
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // получаем виджет фрагмента на основе созданого макета
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        // получаем виджет календаря
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            // вызывается при изменении выбранной даты в календаре
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                String numOfSeason = Integer.toString(Seasons.getSeason(month));
                fragmentSendDataListener.onSendData(numOfSeason);
            }
        });

        return view;
    }

}