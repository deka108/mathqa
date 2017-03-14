package dekauliya.fyp.mathqa.Views.SearchViews;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import dekauliya.fyp.mathqa.R;

/**
 * Created by dekauliya on 11/2/17.
 */
public class FormulaHelper {

    public static ArrayList<String> getFormulaStrArray(FormulaType formulaType, Context context){
        ArrayList<String> formulas = new ArrayList<>();

        switch(formulaType){
            case ALL:
                for(FormulaType type: FormulaType.values()){
                    switch (type){
                        case ARITHMETIC: case RELATION: case SYMBOLS: case ARROW:
                        case FUNCTION: case TRIGONOMETRY: case EXPONENTIAL:
                        formulas.addAll(getFormulaStrArray(type, context));
                    }
                }
                break;
            case ARITHMETIC:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_arithmetic));
                break;
            case RELATION:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_relation));
                break;
            case SYMBOLS:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_symbols));
                break;
            case ARROW:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_arrows));
                break;
            case FUNCTION:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_functions));
                break;
            case TRIGONOMETRY:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .str_trigonometry));
                break;
            case EXPONENTIAL:
                Collections.addAll(formulas, context.getResources().getStringArray(
                        R.array.str_exponential));
                break;
            default: break;
        }
        return formulas;
    }

    public static ArrayList<String> getFormulaArray(dekauliya.fyp.mathqa.Views.SearchViews.FormulaType formulaType, Context context){
        ArrayList<String> formulas = new ArrayList<>();

        switch(formulaType){
            case ALL:
                for(dekauliya.fyp.mathqa.Views.SearchViews.FormulaType type: dekauliya.fyp.mathqa.Views.SearchViews.FormulaType.values()){
                    switch (type){
                        case ARITHMETIC: case RELATION: case SYMBOLS: case ARROW:
                        case FUNCTION: case TRIGONOMETRY: case EXPONENTIAL:
                            formulas.addAll(getFormulaArray(type, context));
                    }
                }
                break;
            case ARITHMETIC:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_arithmetic));
                break;
            case RELATION:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_relations));
                break;
            case SYMBOLS:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_symbols));
                break;
            case ARROW:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_arrows));
                break;
            case FUNCTION:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_functions));
                break;
            case TRIGONOMETRY:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_trigonometry));
                break;
            case EXPONENTIAL:
                Collections.addAll(formulas, context.getResources().getStringArray(R.array
                        .formula_exponential));
                break;
        }
        return formulas;
    }


}
