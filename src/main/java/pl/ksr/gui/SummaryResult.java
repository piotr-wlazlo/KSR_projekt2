package pl.ksr.gui;

public class SummaryResult {
    private final String summaryText;
    private final double optimalMeasure;
    private final double t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;

    public SummaryResult(String summaryText, double optimalMeasure,
                         double t1, double t2, double t3, double t4, double t5,
                         double t6, double t7, double t8, double t9, double t10, double t11) {
        this.summaryText = summaryText;
        this.optimalMeasure = optimalMeasure;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.t11 = t11;
    }

    public String getSummaryText() { return summaryText; }
    public double getOptimalMeasure() { return optimalMeasure; }
    public double getT1() { return t1; }
    public double getT2() { return t2; }
    public double getT3() { return t3; }
    public double getT4() { return t4; }
    public double getT5() { return t5; }
    public double getT6() { return t6; }
    public double getT7() { return t7; }
    public double getT8() { return t8; }
    public double getT9() { return t9; }
    public double getT10() { return t10; }
    public double getT11() { return t11; }
}