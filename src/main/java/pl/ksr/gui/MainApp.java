package pl.ksr.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.ksr.*;
import pl.ksr.db.DataLoader;
import pl.ksr.measures.*;
import pl.ksr.membershipFunctions.GaussianFunction;
import pl.ksr.membershipFunctions.MembershipFunction;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.summary.single.SingleFirstFormSummary;
import pl.ksr.summary.single.SingleSecondFormSummary;
import pl.ksr.universe.DenseUniverse;
import pl.ksr.summary.multi.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainApp extends Application {

    private List<Car> cars;
    private List<LinguisticVariable> allVariables;
    private Map<String, Function<Car, Double>> attributeExtractors;
    private List<Quantifier> allQuantifiers;

    private List<TextField> weightFields;

    private CheckBoxTreeItem<String> featureTreeRootBasic;
    private CheckBoxTreeItem<String> featureTreeRootAdvanced;
    private CheckBoxTreeItem<String> featureTreeRootMulti;

    private final double[] activeWeights = {0.5, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05, 0.05};

    static class SelectedFeature {
        LinguisticVariable variable;
        String label;
        Function<Car, Double> extractor;
        boolean isNot;

        SelectedFeature(LinguisticVariable v, String l, Function<Car, Double> e, boolean isNot) {
            this.variable = v;
            this.label = l;
            this.extractor = e;
            this.isNot = isNot;
        }
    }

    static class Partition {
        List<SelectedFeature> qualifiers;
        List<SelectedFeature> summarizers;

        Partition(List<SelectedFeature> q, List<SelectedFeature> s) {
            this.qualifiers = q;
            this.summarizers = s;
        }
    }

    static class SubjectFilter {
        String name;
        Predicate<Car> filter;
        SubjectFilter(String name, Predicate<Car> filter) { this.name = name; this.filter = filter; }
        @Override public String toString() { return name; }
    }

    @Override
    public void start(Stage primaryStage) {
        cars = DataLoader.loadFromDb();
        initializeData();

        featureTreeRootBasic = new CheckBoxTreeItem<>("Features"); featureTreeRootBasic.setExpanded(true);
        featureTreeRootAdvanced = new CheckBoxTreeItem<>("Features"); featureTreeRootAdvanced.setExpanded(true);
        featureTreeRootMulti = new CheckBoxTreeItem<>("Features"); featureTreeRootMulti.setExpanded(true);
        populateFeatureTrees();

        TabPane tabPane = new TabPane();

        Tab basicTab = new Tab("Basic");
        basicTab.setClosable(false);
        VBox basicGeneratorPanel = createGeneratorPanel(primaryStage, featureTreeRootBasic, null);
        basicTab.setContent(basicGeneratorPanel);

        Tab advancedTab = new Tab("Advanced");
        advancedTab.setClosable(false);
        advancedTab.setContent(createAdvancedPanel(primaryStage));

        Tab multiTab = new Tab("Compare");
        multiTab.setClosable(false);
        multiTab.setContent(createMultiSubjectPanel(primaryStage));

        tabPane.getTabs().addAll(basicTab, advancedTab, multiTab);
        Scene scene = new Scene(tabPane, 1350, 800);
        primaryStage.setTitle("Linguistic Summaries Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private VBox createMultiSubjectPanel(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15));

        HBox topSection = new HBox(20);

        VBox featuresBox = new VBox(5);
        featuresBox.setPrefWidth(260); featuresBox.setMinWidth(260);
        Label featuresLabel = new Label("Select 1 or 2 features:");
        featuresLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        TreeView<String> featureTree = new TreeView<>(featureTreeRootMulti);
        featureTree.setCellFactory(CheckBoxTreeCell.forTreeView());
        featureTree.setPrefHeight(230);
        featuresBox.getChildren().addAll(featuresLabel, featureTree);

        VBox subjectConfigBox = new VBox(15);

        Label radioLabel = new Label("1. Select feature to compare:");
        radioLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ToggleGroup compareGroup = new ToggleGroup();
        RadioButton rbMake = new RadioButton("Make Name"); rbMake.setToggleGroup(compareGroup);
        RadioButton rbFuel = new RadioButton("Fuel Type"); rbFuel.setToggleGroup(compareGroup);
        RadioButton rbSegment = new RadioButton("Segment"); rbSegment.setToggleGroup(compareGroup);

        HBox radioBox = new HBox(15, rbMake, rbFuel, rbSegment);

        Label subLabel = new Label("2. Choose subjects to compare:");
        subLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ComboBox<SubjectFilter> sub1Combo = new ComboBox<>();
        ComboBox<SubjectFilter> sub2Combo = new ComboBox<>();
        sub1Combo.setPrefWidth(200);
        sub2Combo.setPrefWidth(200);

        compareGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            sub1Combo.getItems().clear();
            sub2Combo.getItems().clear();

            List<SubjectFilter> filters = new ArrayList<>();

            if (newVal == rbMake) {
                filters = cars.stream().map(Car::makeName).filter(m -> m != null && !m.isEmpty()).distinct().sorted()
                        .map(m -> new SubjectFilter(m, c -> m.equals(c.makeName()))).toList();
            } else if (newVal == rbFuel) {
                filters = cars.stream().map(Car::fuelType).filter(f -> f != null && !f.isEmpty()).distinct().sorted()
                        .map(f -> new SubjectFilter(f, c -> f.equals(c.fuelType()))).toList();
            } else if (newVal == rbSegment) {
                filters = cars.stream().map(Car::segment).filter(s -> s != null).distinct().sorted()
                        .map(s -> new SubjectFilter("Segment " + s.name(), c -> c.segment() == s)).toList();
            }

            sub1Combo.getItems().addAll(filters);
            sub2Combo.getItems().addAll(filters);

            if (!filters.isEmpty()) {
                sub1Combo.getSelectionModel().select(0);
                sub2Combo.getSelectionModel().select(filters.size() > 1 ? 1 : 0);
            }
        });

        rbFuel.setSelected(true);

        GridPane subGrid = new GridPane();
        subGrid.setHgap(10); subGrid.setVgap(10);
        subGrid.addRow(0, new Label("Subject 1:"), sub1Combo);
        subGrid.addRow(1, new Label("Subject 2:"), sub2Combo);



        subjectConfigBox.getChildren().addAll(radioLabel, radioBox, subLabel, subGrid);
        HBox.setHgrow(subjectConfigBox, Priority.ALWAYS);
        topSection.getChildren().addAll(featuresBox, subjectConfigBox);

        HBox actionButtonsBox = new HBox(15);
        actionButtonsBox.setAlignment(Pos.CENTER_LEFT);

        Button generateBtn = new Button("Generate Multi-Subject Summaries");
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");
        Button saveBtn = new Button("Save selected to file");
        saveBtn.setStyle("-fx-font-weight: bold; -fx-base: #7B448C;");
        actionButtonsBox.getChildren().addAll(generateBtn, saveBtn);

        TableView<SummaryResult> table = new TableView<>();
        table.setPrefHeight(350); VBox.setVgrow(table, Priority.ALWAYS);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<SummaryResult, String> textCol = new TableColumn<>("Summary text");
        textCol.setCellValueFactory(new PropertyValueFactory<>("summaryText")); textCol.setPrefWidth(650);

        TableColumn<SummaryResult, Double> t1Col = new TableColumn<>("T1 (Degree of Truth)");
        t1Col.setCellValueFactory(new PropertyValueFactory<>("t1")); t1Col.setPrefWidth(150);
        setupFormattedColumn(t1Col);

        table.getColumns().addAll(textCol, t1Col);

        generateBtn.setOnAction(e -> {
            try {
                List<SelectedFeature> sel = new ArrayList<>();
                extractFeatures(featureTreeRootMulti, sel);

                if (sel.isEmpty() || sel.size() > 2) {
                    new Alert(Alert.AlertType.WARNING, "For Multi-Subject summaries, please select exactly 1 or 2 features!").show();
                    return;
                }

                SubjectFilter sf1 = sub1Combo.getValue();
                SubjectFilter sf2 = sub2Combo.getValue();

                if (sf1 == null || sf2 == null || sf1.name.equals(sf2.name)) {
                    new Alert(Alert.AlertType.WARNING, "Subject 1 and Subject 2 must be different and valid!").show();
                    return;
                }

                List<Car> p1 = cars.stream().filter(sf1.filter).collect(Collectors.toList());
                List<Car> p2 = cars.stream().filter(sf2.filter).collect(Collectors.toList());

                List<SummaryResult> resultsList = new ArrayList<>();
                MultiSubjectT t1Measure = new MultiSubjectT();

                for (Quantifier q : allQuantifiers) {

                    if (sel.size() == 1) {
                        SelectedFeature f1 = sel.get(0);
                        List<Summarizer> sList = List.of(new Summarizer(f1.variable, f1.label, f1.isNot));
                        List<Function<Car, Double>> sAttrs = List.of(f1.extractor);

                        if (q.isRelative()) {
                            MultiFirstFormSummary FirstForm = new MultiFirstFormSummary(q, sf1.name, p1, sf2.name, p2, sList, sAttrs, LogicalOperator.AND);
                            double t1 = t1Measure.calculate(FirstForm);
                            resultsList.add(new SummaryResult(FirstForm.getSummary(), t1, t1, 0,0,0,0,0,0,0,0,0,0));
                        }
                        if (q == allQuantifiers.get(0)) {
                            MultiFourthForm FourthForm = new MultiFourthForm(sf1.name, p1, sf2.name, p2, sList, sAttrs, LogicalOperator.AND);
                            double t1 = t1Measure.calculate(FourthForm);
                            resultsList.add(new SummaryResult(FourthForm.getSummary(), t1, t1, 0,0,0,0,0,0,0,0,0,0));
                        }
                    }

                    else if (sel.size() == 2) {
                        SelectedFeature f1 = sel.get(0);
                        SelectedFeature f2 = sel.get(1);

                        Summarizer s1 = new Summarizer(f1.variable, f1.label, f1.isNot);
                        Summarizer s2 = new Summarizer(f2.variable, f2.label, f2.isNot);
                        Qualifier w1 = new Qualifier(f1.variable, f1.label, f1.isNot);
                        Qualifier w2 = new Qualifier(f2.variable, f2.label, f2.isNot);

                        if (q.isRelative()) {

                            MultiFirstFormSummary f1_a = new MultiFirstFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(s1), List.of(f1.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f1_a.getSummary(), t1Measure.calculate(f1_a), t1Measure.calculate(f1_a), 0,0,0,0,0,0,0,0,0,0));

                            MultiFirstFormSummary f1_b = new MultiFirstFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(s2), List.of(f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f1_b.getSummary(), t1Measure.calculate(f1_b), t1Measure.calculate(f1_b), 0,0,0,0,0,0,0,0,0,0));

                            MultiFirstFormSummary f1_c = new MultiFirstFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(s1, s2), List.of(f1.extractor, f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f1_c.getSummary(), t1Measure.calculate(f1_c), t1Measure.calculate(f1_c), 0,0,0,0,0,0,0,0,0,0));

                            MultiFirstFormSummary f1_d = new MultiFirstFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(s1, s2), List.of(f1.extractor, f2.extractor), LogicalOperator.OR);
                            resultsList.add(new SummaryResult(f1_d.getSummary(), t1Measure.calculate(f1_d), t1Measure.calculate(f1_d), 0,0,0,0,0,0,0,0,0,0));


                            MultiSecondFormSummary f2_a = new MultiSecondFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(w1), List.of(f1.extractor), LogicalOperator.AND, List.of(s2), List.of(f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f2_a.getSummary(), t1Measure.calculate(f2_a), t1Measure.calculate(f2_a), 0,0,0,0,0,0,0,0,0,0));
                            MultiSecondFormSummary f2_b = new MultiSecondFormSummary(q, sf1.name, p1, sf2.name, p2, List.of(w2), List.of(f2.extractor), LogicalOperator.AND, List.of(s1), List.of(f1.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f2_b.getSummary(), t1Measure.calculate(f2_b), t1Measure.calculate(f2_b), 0,0,0,0,0,0,0,0,0,0));


                            MultiThirdFormSummary f3_a = new MultiThirdFormSummary(q, sf1.name, p1, List.of(w1), List.of(f1.extractor), LogicalOperator.AND, sf2.name, p2, List.of(s2), List.of(f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f3_a.getSummary(), t1Measure.calculate(f3_a), t1Measure.calculate(f3_a), 0,0,0,0,0,0,0,0,0,0));
                            MultiThirdFormSummary f3_b = new MultiThirdFormSummary(q, sf1.name, p1, List.of(w2), List.of(f2.extractor), LogicalOperator.AND, sf2.name, p2, List.of(s1), List.of(f1.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f3_b.getSummary(), t1Measure.calculate(f3_b), t1Measure.calculate(f3_b), 0,0,0,0,0,0,0,0,0,0));
                        }

                        if (q == allQuantifiers.get(0)) {
                            MultiFourthForm f4_a = new MultiFourthForm(sf1.name, p1, sf2.name, p2, List.of(s1), List.of(f1.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f4_a.getSummary(), t1Measure.calculate(f4_a), t1Measure.calculate(f4_a), 0,0,0,0,0,0,0,0,0,0));

                            MultiFourthForm f4_b = new MultiFourthForm(sf1.name, p1, sf2.name, p2, List.of(s2), List.of(f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f4_b.getSummary(), t1Measure.calculate(f4_b), t1Measure.calculate(f4_b), 0,0,0,0,0,0,0,0,0,0));

                            MultiFourthForm f4_c = new MultiFourthForm(sf1.name, p1, sf2.name, p2, List.of(s1, s2), List.of(f1.extractor, f2.extractor), LogicalOperator.AND);
                            resultsList.add(new SummaryResult(f4_c.getSummary(), t1Measure.calculate(f4_c), t1Measure.calculate(f4_c), 0,0,0,0,0,0,0,0,0,0));
                            MultiFourthForm f4_d = new MultiFourthForm(sf1.name, p1, sf2.name, p2, List.of(s1, s2), List.of(f1.extractor, f2.extractor), LogicalOperator.OR);
                            resultsList.add(new SummaryResult(f4_d.getSummary(), t1Measure.calculate(f4_d), t1Measure.calculate(f4_d), 0,0,0,0,0,0,0,0,0,0));
                        }
                    }
                }

                resultsList.sort((r1, r2) -> Double.compare(r2.getT1(), r1.getT1()));
                table.getItems().clear();
                table.getItems().addAll(resultsList);

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error during generation: " + ex.getMessage()).show();
            }
        });

        saveBtn.setOnAction(e -> saveToFile(primaryStage, table));
        layout.getChildren().addAll(topSection, actionButtonsBox, table);
        return layout;
    }

    private SummaryResult evaluateSummary(pl.ksr.summary.LinguisticSummary summary, To toMeasure) {
        double optimal = toMeasure.calculate(summary);
        return new SummaryResult(summary.getSummary(), optimal,
                new T1().calculate(summary), new T2().calculate(summary), new T3().calculate(summary),
                new T4().calculate(summary), new T5().calculate(summary), new T6().calculate(summary),
                new T7().calculate(summary), new T8().calculate(summary), new T9().calculate(summary),
                new T10().calculate(summary), new T11().calculate(summary));
    }



    private VBox createAdvancedPanel(Stage primaryStage) {
        HBox settingsRow = new HBox(15);

        TitledPane weightsPane = new TitledPane();
        weightsPane.setText("1. Quality Measures Weights");
        weightsPane.setCollapsible(false);

        GridPane weightsGrid = new GridPane();
        weightsGrid.setHgap(10); weightsGrid.setVgap(10);

        weightFields = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            TextField tf = new TextField(); tf.setPrefWidth(45); tf.setText(i == 1 ? "0.5" : "0.05"); weightFields.add(tf);
            int row = (i - 1) / 4; int col = (i - 1) % 4;
            HBox cell = new HBox(3, new Label("T" + i + ":"), tf); cell.setAlignment(Pos.CENTER_LEFT);
            weightsGrid.add(cell, col, row);
        }

        Button saveWeightsBtn = new Button("Save New Weights");
        saveWeightsBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");
        saveWeightsBtn.setOnAction(e -> {
            try {
                double[] tempWeights = new double[11]; double sumWeights = 0.0;
                for (int i = 0; i < 11; i++) {
                    tempWeights[i] = Double.parseDouble(weightFields.get(i).getText().replace(",", "."));
                    sumWeights += tempWeights[i];
                }
                if (Math.abs(sumWeights - 1.0) > 0.0001) {
                    new Alert(Alert.AlertType.WARNING, "The sum of weights must be exactly 1.0!").show(); return;
                }
                System.arraycopy(tempWeights, 0, activeWeights, 0, 11);
                new Alert(Alert.AlertType.INFORMATION, "New weights saved and applied successfully!").show();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid weight format! Use numbers only.").show();
            }
        });

        VBox weightsContentBox = new VBox(10, weightsGrid, saveWeightsBtn);
        weightsContentBox.setPadding(new Insets(10));
        weightsPane.setContent(weightsContentBox);

        TitledPane newQuantifierPane = new TitledPane();
        newQuantifierPane.setText("2. New Quantifier (Trapezoidal)");
        newQuantifierPane.setCollapsible(false);

        GridPane qGrid = new GridPane();
        qGrid.setHgap(8); qGrid.setVgap(8); qGrid.setPadding(new Insets(10));

        TextField qNameField = new TextField(); qNameField.setPromptText("Name (e.g. 70%)");
        ComboBox<String> qUniverseCombo = new ComboBox<>();
        qUniverseCombo.getItems().addAll("Relative [0.0 - 1.0]", "Absolute"); qUniverseCombo.getSelectionModel().selectFirst();
        TextField qA = new TextField(); qA.setPrefWidth(45); qA.setPromptText("a");
        TextField qB = new TextField(); qB.setPrefWidth(45); qB.setPromptText("b");
        TextField qC = new TextField(); qC.setPrefWidth(45); qC.setPromptText("c");
        TextField qD = new TextField(); qD.setPrefWidth(45); qD.setPromptText("d");

        Button addQuantifierBtn = new Button("Add Quantifier");
        addQuantifierBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");
        qGrid.addRow(0, new Label("Name:"), qNameField);
        qGrid.addRow(1, new Label("Universe:"), qUniverseCombo);
        qGrid.addRow(2, new Label("Params:"), new HBox(5, qA, qB, qC, qD));
        qGrid.addRow(3, addQuantifierBtn);
        newQuantifierPane.setContent(qGrid);

        addQuantifierBtn.setOnAction(e -> {
            try {
                String name = qNameField.getText();
                boolean isRelative = qUniverseCombo.getValue().startsWith("Relative");
                double a = Double.parseDouble(qA.getText()); double b = Double.parseDouble(qB.getText());
                double c = Double.parseDouble(qC.getText()); double d = Double.parseDouble(qD.getText());

                DenseUniverse uni = isRelative ? new DenseUniverse(0.0, 1.0) : new DenseUniverse(0.0, 2335706.0);
                allQuantifiers.add(new Quantifier(name, new FuzzySet(uni, new TrapezoidalFunction(a, b, c, d)), isRelative));
                new Alert(Alert.AlertType.INFORMATION, "Quantifier added successfully!").show();
                qNameField.clear(); qA.clear(); qB.clear(); qC.clear(); qD.clear();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid parameters! Please enter numbers.").show();
            }
        });

        TitledPane newFeaturePane = new TitledPane();
        newFeaturePane.setText("3. New Feature Label");
        newFeaturePane.setCollapsible(false);

        GridPane fGrid = new GridPane();
        fGrid.setHgap(8); fGrid.setVgap(8); fGrid.setPadding(new Insets(10));

        ComboBox<String> varCombo = new ComboBox<>();
        for(LinguisticVariable lv : allVariables) varCombo.getItems().add(lv.getName());
        varCombo.getSelectionModel().selectFirst();

        TextField fNameField = new TextField(); fNameField.setPromptText("e.g. very cheap");
        ComboBox<String> fFuncTypeCombo = new ComboBox<>();
        fFuncTypeCombo.getItems().addAll("Trapezoidal", "Gaussian"); fFuncTypeCombo.getSelectionModel().selectFirst();
        TextField fP1 = new TextField(); fP1.setPrefWidth(45); fP1.setPromptText("a");
        TextField fP2 = new TextField(); fP2.setPrefWidth(45); fP2.setPromptText("b");
        TextField fP3 = new TextField(); fP3.setPrefWidth(45); fP3.setPromptText("c");
        TextField fP4 = new TextField(); fP4.setPrefWidth(45); fP4.setPromptText("d");
        HBox fParamsBox = new HBox(5, fP1, fP2, fP3, fP4);

        fFuncTypeCombo.setOnAction(e -> {
            fParamsBox.getChildren().clear(); fP1.clear(); fP2.clear(); fP3.clear(); fP4.clear();
            if (fFuncTypeCombo.getValue().equals("Trapezoidal")) {
                fP1.setPromptText("a"); fP1.setPrefWidth(45); fP2.setPromptText("b"); fP2.setPrefWidth(45);
                fP3.setPromptText("c"); fP3.setPrefWidth(45); fP4.setPromptText("d"); fP4.setPrefWidth(45);
                fParamsBox.getChildren().addAll(fP1, fP2, fP3, fP4);
            } else {
                fP1.setPromptText("center (c)"); fP1.setPrefWidth(80); fP2.setPromptText("width (σ)"); fP2.setPrefWidth(80);
                fParamsBox.getChildren().addAll(fP1, fP2);
            }
        });

        Button addFeatureBtn = new Button("Add Feature Label");
        addFeatureBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");
        fGrid.addRow(0, new Label("Attribute:"), varCombo);
        fGrid.addRow(1, new Label("Label:"), fNameField);
        fGrid.addRow(2, new Label("Function:"), fFuncTypeCombo);
        fGrid.addRow(3, new Label("Params:"), fParamsBox);
        fGrid.addRow(4, addFeatureBtn);
        newFeaturePane.setContent(fGrid);

        addFeatureBtn.setOnAction(e -> {
            try {
                String varName = varCombo.getValue(); String labelName = fNameField.getText();
                MembershipFunction func;
                if (fFuncTypeCombo.getValue().equals("Trapezoidal")) {
                    func = new TrapezoidalFunction(Double.parseDouble(fP1.getText()), Double.parseDouble(fP2.getText()), Double.parseDouble(fP3.getText()), Double.parseDouble(fP4.getText()));
                } else {
                    func = new GaussianFunction(Double.parseDouble(fP1.getText()), Double.parseDouble(fP2.getText()));
                }
                LinguisticVariable targetVar = allVariables.stream().filter(v -> v.getName().equals(varName)).findFirst().orElseThrow();
                targetVar.addLabel(labelName, new FuzzySet(targetVar.getUniverseOfDiscourse(), func));

                populateFeatureTrees();
                new Alert(Alert.AlertType.INFORMATION, "Label added successfully!").show();
                fNameField.clear(); fP1.clear(); fP2.clear(); fP3.clear(); fP4.clear();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid parameters! Please enter correct numbers.").show();
            }
        });

        HBox.setHgrow(weightsPane, Priority.ALWAYS);
        HBox.setHgrow(newQuantifierPane, Priority.ALWAYS);
        HBox.setHgrow(newFeaturePane, Priority.ALWAYS);
        settingsRow.getChildren().addAll(weightsPane, newQuantifierPane, newFeaturePane);

        return createGeneratorPanel(primaryStage, featureTreeRootAdvanced, settingsRow);
    }


    private VBox createGeneratorPanel(Stage primaryStage, CheckBoxTreeItem<String> treeRoot, Node extraTopContent) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15));
        HBox topSection = new HBox(20);
        VBox featuresBox = new VBox(5);
        featuresBox.setPrefWidth(260); featuresBox.setMinWidth(260);

        Label featuresLabel = new Label("Select up to 3 features:");
        featuresLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TreeView<String> featureTree = new TreeView<>(treeRoot);
        featureTree.setCellFactory(CheckBoxTreeCell.forTreeView());
        featureTree.setPrefHeight(230);
        featuresBox.getChildren().addAll(featuresLabel, featureTree);
        topSection.getChildren().add(featuresBox);

        if (extraTopContent != null) {
            HBox.setHgrow(extraTopContent, Priority.ALWAYS);
            topSection.getChildren().add(extraTopContent);
        }

        HBox actionButtonsBox = new HBox(15);
        actionButtonsBox.setAlignment(Pos.CENTER_LEFT);
        Button generateBtn = new Button("Generate All Combinations");
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");
        Button saveBtn = new Button("Save selected to file");
        saveBtn.setStyle("-fx-font-weight: bold; -fx-base: #7B448C;");
        actionButtonsBox.getChildren().addAll(generateBtn, saveBtn);

        TableView<SummaryResult> table = new TableView<>();
        table.setPrefHeight(350); VBox.setVgrow(table, Priority.ALWAYS);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<SummaryResult, String> textCol = new TableColumn<>("Summary text");
        textCol.setCellValueFactory(new PropertyValueFactory<>("summaryText")); textCol.setPrefWidth(420);
        TableColumn<SummaryResult, Double> optCol = new TableColumn<>("Final quality measure");
        optCol.setCellValueFactory(new PropertyValueFactory<>("optimalMeasure")); optCol.setPrefWidth(140); setupFormattedColumn(optCol);
        table.getColumns().addAll(textCol, optCol);
        for (int i = 1; i <= 11; i++) {
            TableColumn<SummaryResult, Double> tCol = new TableColumn<>("T" + i);
            tCol.setCellValueFactory(new PropertyValueFactory<>("t" + i)); tCol.setPrefWidth(48); setupFormattedColumn(tCol); table.getColumns().add(tCol);
        }

        generateBtn.setOnAction(e -> {
            try {
                List<SelectedFeature> selectedFeatures = new ArrayList<>();
                extractFeatures(treeRoot, selectedFeatures);

                if (selectedFeatures.isEmpty() || selectedFeatures.size() > 3) {
                    new Alert(Alert.AlertType.WARNING, "Please select between 1 and 3 features!").show();
                    return;
                }

                To toMeasure = new To();
                toMeasure.addMeasure(new T1(), activeWeights[0]); toMeasure.addMeasure(new T2(), activeWeights[1]);
                toMeasure.addMeasure(new T3(), activeWeights[2]); toMeasure.addMeasure(new T4(), activeWeights[3]);
                toMeasure.addMeasure(new T5(), activeWeights[4]); toMeasure.addMeasure(new T6(), activeWeights[5]);
                toMeasure.addMeasure(new T7(), activeWeights[6]); toMeasure.addMeasure(new T8(), activeWeights[7]);
                toMeasure.addMeasure(new T9(), activeWeights[8]); toMeasure.addMeasure(new T10(), activeWeights[9]);
                toMeasure.addMeasure(new T11(), activeWeights[10]);

                List<SummaryResult> resultsList = new ArrayList<>();
                List<Partition> partitions = generatePartitions(selectedFeatures);

                for (Quantifier q : allQuantifiers) {
                    for (Partition partition : partitions) {
                        List<Qualifier> qualifiers = partition.qualifiers.stream().map(f -> new Qualifier(f.variable, f.label, f.isNot)).toList();
                        List<Function<Car, Double>> qualExtractors = partition.qualifiers.stream().map(f -> f.extractor).toList();
                        List<Summarizer> summarizers = partition.summarizers.stream().map(f -> new Summarizer(f.variable, f.label, f.isNot)).toList();
                        List<Function<Car, Double>> sumExtractors = partition.summarizers.stream().map(f -> f.extractor).toList();

                        LogicalOperator[] qualOps = qualifiers.size() > 1 ? LogicalOperator.values() : new LogicalOperator[]{LogicalOperator.AND};
                        LogicalOperator[] sumOps = summarizers.size() > 1 ? LogicalOperator.values() : new LogicalOperator[]{LogicalOperator.AND};

                        List<pl.ksr.summary.LinguisticSummary> generatedSummaries = new ArrayList<>();

                        for (LogicalOperator sumOp : sumOps) {
                            if (qualifiers.isEmpty()) {
                                generatedSummaries.add(new SingleFirstFormSummary(q, summarizers, sumExtractors, sumOp, cars));
                            } else {
                                for (LogicalOperator qualOp : qualOps) {
                                    generatedSummaries.add(new SingleSecondFormSummary(q, qualifiers, qualExtractors, qualOp, summarizers, sumExtractors, sumOp, cars));
                                }
                            }
                        }

                        for (pl.ksr.summary.LinguisticSummary summary : generatedSummaries) {
                            resultsList.add(evaluateSummary(summary, toMeasure));
                        }
                    }
                }

                resultsList.sort((r1, r2) -> Double.compare(r2.getOptimalMeasure(), r1.getOptimalMeasure()));
                table.getItems().clear();
                table.getItems().addAll(resultsList);

            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error during generation: " + ex.getMessage()).show();
            }
        });

        saveBtn.setOnAction(e -> saveToFile(primaryStage, table));
        layout.getChildren().addAll(topSection, actionButtonsBox, table);
        return layout;
    }

    private void saveToFile(Stage primaryStage, TableView<SummaryResult> table) {
        List<SummaryResult> selectedItems = new ArrayList<>(table.getSelectionModel().getSelectedItems());
        if (selectedItems.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select at least one summary from the table!").show();
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Summaries");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                for (SummaryResult res : selectedItems) {
                    writer.println("Summary: " + res.getSummaryText());
                    writer.println(String.format(Locale.US, "Optimal Measure: %.2f", res.getOptimalMeasure()));
                    writer.println(String.format(Locale.US, "Measures -> T1: %.2f | T2: %.2f | T3: %.2f | T4: %.2f | T5: %.2f | T6: %.2f | T7: %.2f | T8: %.2f | T9: %.2f | T10: %.2f | T11: %.2f",
                            res.getT1(), res.getT2(), res.getT3(), res.getT4(), res.getT5(), res.getT6(),
                            res.getT7(), res.getT8(), res.getT9(), res.getT10(), res.getT11()));
                    writer.println("----------------------------------------------------------------------------------------------------------------");
                }
                new Alert(Alert.AlertType.INFORMATION, "Selected summaries have been saved successfully!").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error saving file: " + ex.getMessage()).show();
            }
        }
    }


    private void populateFeatureTrees() {
        populateTree(featureTreeRootBasic);
        populateTree(featureTreeRootAdvanced);
        populateTree(featureTreeRootMulti);
    }

    private void populateTree(CheckBoxTreeItem<String> root) {
        root.getChildren().clear();
        for (LinguisticVariable var : allVariables) {
            CheckBoxTreeItem<String> varItem = new CheckBoxTreeItem<>(var.getName());
            for (String label : var.getLabels().keySet()) {
                CheckBoxTreeItem<String> labelItem = new CheckBoxTreeItem<>(label);
                CheckBoxTreeItem<String> notLabelItem = new CheckBoxTreeItem<>("not " + label);
                varItem.getChildren().addAll(labelItem, notLabelItem);
            }
            root.getChildren().add(varItem);
        }
    }

    private List<Partition> generatePartitions(List<SelectedFeature> selected) {
        List<Partition> partitions = new ArrayList<>();
        int n = selected.size();
        for (int mask = 1; mask < (1 << n); mask++) {
            List<SelectedFeature> qualFeats = new ArrayList<>();
            List<SelectedFeature> sumFeats = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) sumFeats.add(selected.get(i));
                else qualFeats.add(selected.get(i));
            }
            partitions.add(new Partition(qualFeats, sumFeats));
        }
        return partitions;
    }

    private void extractFeatures(TreeItem<String> root, List<SelectedFeature> list) {
        for (TreeItem<String> varNode : root.getChildren()) {
            String varName = varNode.getValue();
            LinguisticVariable variable = allVariables.stream().filter(v -> v.getName().equals(varName)).findFirst().orElse(null);
            if (variable == null) continue;

            for (TreeItem<String> labelNode : varNode.getChildren()) {
                if (((CheckBoxTreeItem<String>) labelNode).isSelected()) {
                    String nodeValue = labelNode.getValue();
                    boolean isNot = nodeValue.startsWith("not ");
                    String actualLabel = isNot ? nodeValue.substring(4) : nodeValue;

                    list.add(new SelectedFeature(variable, actualLabel, attributeExtractors.get(varName), isNot));
                }
            }
        }
    }

    private void setupFormattedColumn(TableColumn<SummaryResult, Double> column) {
        column.setCellFactory(tc -> new TableCell<SummaryResult, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(String.format(Locale.US, "%.2f", item));
            }
        });
    }

    private void initializeData() {
        allVariables = List.of(
                VariableInitializer.initializeMileage(),
                VariableInitializer.initializePrice(),
                VariableInitializer.initializeEngineDisplacement(),
                VariableInitializer.initializeHorsepower(),
                VariableInitializer.initializeYear(),
                VariableInitializer.initializeTorque(),
                VariableInitializer.initializeFuelTankCapacity(),
                VariableInitializer.initializeLength(),
                VariableInitializer.initializeWheelbase(),
                VariableInitializer.initializeDaysOnMarket(),
                VariableInitializer.initializeMakeName(),
                VariableInitializer.initializeFuelType(),
                VariableInitializer.initializeSegment()
        );

        attributeExtractors = new HashMap<>();
        attributeExtractors.put("Mileage", Car::mileage);
        attributeExtractors.put("Price", Car::price);
        attributeExtractors.put("Horsepower", Car::horsepower);
        attributeExtractors.put("Year", car -> (double) car.year());
        attributeExtractors.put("Torque", Car::torque);
        attributeExtractors.put("Fuel Tank Capacity", Car::fuelTankVolume);
        attributeExtractors.put("Length", Car::length);
        attributeExtractors.put("Wheelbase", Car::wheelbase);
        attributeExtractors.put("Days On Market", car -> (double) car.daysOnMarket());
        attributeExtractors.put("Make Name", car -> (double) VariableInitializer.MAKES.indexOf(car.makeName()));
        attributeExtractors.put("Fuel Type", car -> (double) VariableInitializer.FUEL_TYPES.indexOf(car.fuelType()));
        attributeExtractors.put("Segment", car -> car.segment() != null ? (double) car.segment().ordinal() : -1.0);
        attributeExtractors.put("Engine Displacement", Car::engineDisplacement);

        DenseUniverse relativeUniverse = new DenseUniverse(0.0, 1.0);
        DenseUniverse absoluteUniverse = new DenseUniverse(0.0, 2335706.0);

        allQuantifiers = new ArrayList<>(List.of(
                new Quantifier("almost none", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.0, 0.0, 0.10, 0.15)), true),
                new Quantifier("few", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.05, 0.15, 0.25, 0.40)), true),
                new Quantifier("about half", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.35, 0.45, 0.55, 0.65)), true),
                new Quantifier("many", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.55, 0.65, 0.8, 0.9)), true),
                new Quantifier("almost all", new FuzzySet(relativeUniverse, new TrapezoidalFunction(0.8, 0.9, 1.0, 1.0)), true),


                new Quantifier("less than 50K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(0, 0, 45000, 50000)), false),
                new Quantifier("about 100K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(45000, 90000, 110000, 170000)), false),
                new Quantifier("about 250K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(150000, 240000, 260000, 345000)), false),
                new Quantifier("about 500K", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(350000, 400000, 600000, 800000)), false),
                new Quantifier("about 1M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(750000, 900000, 1100000, 1250000)), false),
                new Quantifier("about 1.5M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(1200000, 1400000, 1600000, 2100000)), false),
                new Quantifier("over 2M", new FuzzySet(absoluteUniverse, new TrapezoidalFunction(2000000, 2001000, 2335706, 2335706)), false)
        ));
    }
}