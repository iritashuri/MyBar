package com.example.mybar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class FreeDrinksCardsDialog extends AppCompatDialogFragment {

    private int numberOfDrinks;

    private TextView FreeDrinksCards_TXT_freeDrinks;
    private TextView FreeDrinksCards_TXT_drinksLeft;


    public FreeDrinksCardsDialog() {
    }

    public FreeDrinksCardsDialog(int numberOfDrinks) {
        this.numberOfDrinks = numberOfDrinks;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog__free_drinks_cards, null);

        findViews(view);

        // Display the amount of drinks customer need to order in order to get free drinks
        showCurrentCardStatus();
        // Display the amount of free drinks accrued
        showFreeDrinks();

        builder.setView(view).setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Close dialog
            }
        });
        return builder.create();
    }

    private void findViews(View view) {
        FreeDrinksCards_TXT_freeDrinks = view.findViewById(R.id.FreeDrinksCards_TXT_freeDrinks);
        FreeDrinksCards_TXT_drinksLeft = view.findViewById(R.id.FreeDrinksCards_TXT_drinksLeft);
    }

    private void showCurrentCardStatus() {
        int numberOfDrinksInCurrentCard = numberOfDrinks%10;
        FreeDrinksCards_TXT_drinksLeft.setText("You have accumulated " + numberOfDrinksInCurrentCard + " drinks\n for the current card,\n You have left " + (10-numberOfDrinksInCurrentCard));

    }

    // Display the amount of free drinks accrued
    private void showFreeDrinks() {
        int free_drinks = numberOfDrinks/10;
        if(free_drinks > 0){
            FreeDrinksCards_TXT_freeDrinks.setText("You have " + free_drinks + " free drinks");
        }else{
            FreeDrinksCards_TXT_freeDrinks.setText("You have no free drinks");
        }
    }
}
