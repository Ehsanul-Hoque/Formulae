package com.example.formulae;


class FormulasItem {

    private String title;
    private String formula;
    private String notations;
    private String[] variables;
    private String calculationCode;
    private String outputTitle;

    FormulasItem(String title, String formula, String notations, String[] variables, String calculationCode, String outputTitle) {
        this.title = title;
        this.formula = formula;
        this.notations = notations;
        this.variables = variables;
        this.calculationCode = calculationCode;
        this.outputTitle = outputTitle;
    }

    String getTitle() {
        return title;
    }

    String getFormula() {
        return formula;
    }

    String getNotations() {
        return notations;
    }

    String[] getVariables() {
        return variables;
    }

    String getCalculationCode() {
        return calculationCode;
    }

    String getOutputTitle() {
        return outputTitle;
    }
}
