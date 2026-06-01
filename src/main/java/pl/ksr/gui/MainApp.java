package pl.ksr.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.ksr.*;
import pl.ksr.db.DataLoader;
import pl.ksr.measures.*;
import pl.ksr.membershipFunctions.TrapezoidalFunction;
import pl.ksr.sets.FuzzySet;
import pl.ksr.summary.FirstFormSummary;
import pl.ksr.summary.SecondFormSummary;
import pl.ksr.universe.DenseUniverse;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class MainApp extends Application {

    private List<Car> cars;
    private List<LinguisticVariable> allVariables;
    private Map<String, Function<Car, Double>> attributeExtractors;
    private List<Quantifier> allQuantifiers;

    @Override
    public void start(Stage primaryStage) {
        cars = DataLoader.loadFromDb();
        initializeData();

        TabPane tabPane = new TabPane();

        Tab basicTab = new Tab("Summary Generation");
        basicTab.setClosable(false);
        VBox basicLayout = new VBox(15);
        basicLayout.setPadding(new Insets(15));

        HBox topPanelsBox = new HBox(20);

        VBox quantBox = new VBox(5);
        quantBox.setPrefWidth(160);
        Label quantLabel = new Label("Quantifiers (Q):");
        quantLabel.setStyle("-fx-font-weight: bold;");
        quantBox.getChildren().add(quantLabel);

        ToggleGroup quantGroup = new ToggleGroup();
        for (Quantifier q : allQuantifiers) {
            RadioButton rb = new RadioButton(q.getLabel());
            rb.setToggleGroup(quantGroup);
            rb.setUserData(q);
            quantBox.getChildren().add(rb);
        }
        if (!quantGroup.getToggles().isEmpty()) {
            quantGroup.getToggles().get(0).setSelected(true);
        }

        VBox qualBox = new VBox(5);
        Label qualLabel = new Label("Qualifiers (W) - Optional:");
        qualLabel.setStyle("-fx-font-weight: bold;");
        qualBox.getChildren().add(qualLabel);
        TreeView<String> qualifierTree = createVariablesTree("Select Qualifiers");
        qualifierTree.setPrefHeight(250);
        ComboBox<LogicalOperator> qualOpCombo = new ComboBox<>();
        qualOpCombo.getItems().addAll(LogicalOperator.AND, LogicalOperator.OR);
        qualOpCombo.setValue(LogicalOperator.AND);
        qualBox.getChildren().addAll(qualifierTree, new HBox(5, new Label("Operator:"), qualOpCombo));

        VBox sumBox = new VBox(5);
        Label sumLabel = new Label("Summarizers (S) - Required:");
        sumLabel.setStyle("-fx-font-weight: bold;");
        sumBox.getChildren().add(sumLabel);
        TreeView<String> summarizerTree = createVariablesTree("Select Summarizers");
        summarizerTree.setPrefHeight(250);
        ComboBox<LogicalOperator> sumOpCombo = new ComboBox<>();
        sumOpCombo.getItems().addAll(LogicalOperator.AND, LogicalOperator.OR);
        sumOpCombo.setValue(LogicalOperator.AND);
        sumBox.getChildren().addAll(summarizerTree, new HBox(5, new Label("Operator:"), sumOpCombo));

        topPanelsBox.getChildren().addAll(quantBox, qualBox, sumBox);

        VBox middleBox = new VBox(10);

        Button toggleWeightsBtn = new Button("Show/Edit Quality Measure Weights ▼");
        toggleWeightsBtn.setStyle("-fx-font-size: 11px; -fx-base: #f0f0f0;");

        VBox weightsContainer = new VBox(10);
        weightsContainer.setVisible(false);
        weightsContainer.setManaged(false);

        Label weightsLabel = new Label("Quality measures weights (T1 - T11):");
        weightsLabel.setStyle("-fx-font-weight: bold;");
        GridPane weightsGrid = new GridPane();
        weightsGrid.setHgap(15);
        weightsGrid.setVgap(10);

        List<TextField> weightFields = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            TextField tf = new TextField();
            tf.setPrefWidth(50);

            if (i == 1) {
                tf.setText("0.5");
            } else {
                tf.setText("0.05");
            }
            weightFields.add(tf);

            int row = (i - 1) / 6;
            int col = (i - 1) % 6;
            HBox cell = new HBox(5, new Label("T" + i + ":"), tf);
            cell.setAlignment(Pos.CENTER_LEFT);
            weightsGrid.add(cell, col, row);
        }

        weightsContainer.getChildren().addAll(weightsLabel, weightsGrid);
        middleBox.getChildren().addAll(toggleWeightsBtn, weightsContainer);

        toggleWeightsBtn.setOnAction(e -> {
            boolean isCurrentlyVisible = weightsContainer.isVisible();
            weightsContainer.setVisible(!isCurrentlyVisible);
            weightsContainer.setManaged(!isCurrentlyVisible);

            if (!isCurrentlyVisible) {
                toggleWeightsBtn.setText("Hide Quality Measure Weights ▲");
            } else {
                toggleWeightsBtn.setText("Show/Edit Quality Measure Weights ▼");
            }
        });

        TableView<SummaryResult> table = new TableView<>();
        table.setPrefHeight(250);

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<SummaryResult, String> textCol = new TableColumn<>("Summary text");
        textCol.setCellValueFactory(new PropertyValueFactory<>("summaryText"));
        textCol.setPrefWidth(350);

        TableColumn<SummaryResult, Double> optCol = new TableColumn<>("Final quality measure");
        optCol.setCellValueFactory(new PropertyValueFactory<>("optimalMeasure"));
        optCol.setPrefWidth(160);
        setupFormattedColumn(optCol);

        table.getColumns().addAll(textCol, optCol);

        for (int i = 1; i <= 11; i++) {
            TableColumn<SummaryResult, Double> tCol = new TableColumn<>("T" + i);
            tCol.setCellValueFactory(new PropertyValueFactory<>("t" + i));
            tCol.setPrefWidth(55);
            setupFormattedColumn(tCol);
            table.getColumns().add(tCol);
        }

        HBox actionButtonsBox = new HBox(15);
        actionButtonsBox.setAlignment(Pos.CENTER_LEFT);

        Button generateBtn = new Button("Generate and Calculate");
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #DE68A5;");

        Button saveBtn = new Button("Save selected to file");
        saveBtn.setStyle("-fx-font-weight: bold; -fx-base: #7B448C;");

        actionButtonsBox.getChildren().addAll(generateBtn, saveBtn);

        generateBtn.setOnAction(e -> {
            try {
                double[] w = new double[11];
                for (int i = 0; i < 11; i++) {
                    w[i] = Double.parseDouble(weightFields.get(i).getText().replace(",", "."));
                }

                Toggle selectedToggle = quantGroup.getSelectedToggle();
                if (selectedToggle == null) {
                    new Alert(Alert.AlertType.WARNING, "You must select a quantifier!").show();
                    return;
                }
                Quantifier selectedQ = (Quantifier) selectedToggle.getUserData();

                List<Qualifier> selectedQualifiers = new ArrayList<>();
                List<Function<Car, Double>> qualFunctions = new ArrayList<>();
                extractQualifiers(qualifierTree.getRoot(), selectedQualifiers, qualFunctions);

                List<Summarizer> selectedSummarizers = new ArrayList<>();
                List<Function<Car, Double>> sumFunctions = new ArrayList<>();
                extractSummarizers(summarizerTree.getRoot(), selectedSummarizers, sumFunctions);

                if (selectedSummarizers.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "You must select at least one summarizer!").show();
                    return;
                }

                pl.ksr.summary.LinguisticSummary summary;
                if (selectedQualifiers.isEmpty()) {
                    summary = new FirstFormSummary(selectedQ, selectedSummarizers, sumFunctions, sumOpCombo.getValue(), cars);
                } else {
                    summary = new SecondFormSummary(selectedQ, selectedQualifiers, qualFunctions, qualOpCombo.getValue(),
                            selectedSummarizers, sumFunctions, sumOpCombo.getValue(), cars);
                }

                To toMeasure = new To();
                toMeasure.addMeasure(new T1(), w[0]);
                toMeasure.addMeasure(new T2(), w[1]);
                toMeasure.addMeasure(new T3(), w[2]);
                toMeasure.addMeasure(new T4(), w[3]);
                toMeasure.addMeasure(new T5(), w[4]);
                toMeasure.addMeasure(new T6(), w[5]);
                toMeasure.addMeasure(new T7(), w[6]);
                toMeasure.addMeasure(new T8(), w[7]);
                toMeasure.addMeasure(new T9(), w[8]);
                toMeasure.addMeasure(new T10(), w[9]);
                toMeasure.addMeasure(new T11(), w[10]);

                double optimal = toMeasure.calculate(summary);

                double t1 = new T1().calculate(summary);
                double t2 = new T2().calculate(summary);
                double t3 = new T3().calculate(summary);
                double t4 = new T4().calculate(summary);
                double t5 = new T5().calculate(summary);
                double t6 = new T6().calculate(summary);
                double t7 = new T7().calculate(summary);
                double t8 = new T8().calculate(summary);
                double t9 = new T9().calculate(summary);
                double t10 = new T10().calculate(summary);
                double t11 = new T11().calculate(summary);

                table.getItems().add(new SummaryResult(summary.getSummary(), optimal, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11));

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Error in weight format! Make sure to use numbers.").show();
            } catch (Exception ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error during generation: " + ex.getMessage()).show();
            }
        });

        saveBtn.setOnAction(e -> {
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
        });

        basicLayout.getChildren().addAll(topPanelsBox, middleBox, actionButtonsBox, table);
        basicTab.setContent(basicLayout);

        Tab advancedTab = new Tab("Advanced");
        advancedTab.setClosable(false);

        tabPane.getTabs().addAll(basicTab, advancedTab);
        Scene scene = new Scene(tabPane, 1150, 750);
        primaryStage.setTitle("KSR Project 2 - Linguistic Summaries");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void setupFormattedColumn(TableColumn<SummaryResult, Double> column) {
        column.setCellFactory(tc -> new TableCell<SummaryResult, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Wymusza np. "1.00", "0.30"
                    setText(String.format(Locale.US, "%.2f", item));
                }
            }
        });
    }

    private void initializeData() {
        allVariables = List.of(
                VariableInitializer.initializeMileage(),
                VariableInitializer.initializePrice(),
                VariableInitializer.initializeHorsepower(),
                VariableInitializer.initializeYear(),
                VariableInitializer.initializeTorque(),
                VariableInitializer.initializeFuelTankCapacity(),
                VariableInitializer.initializeLength(),
                VariableInitializer.initializeWheelbase(),
                VariableInitializer.initializeDaysOnMarket()
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

        DenseUniverse relativeUniverse = new DenseUniverse(0.0, 1.0);
        DenseUniverse absoluteUniverse = new DenseUniverse(0.0, 2335706.0);

        allQuantifiers = List.of(
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
        );
    }

    private TreeView<String> createVariablesTree(String rootName) {
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>(rootName);
        rootItem.setExpanded(true);

        for (LinguisticVariable var : allVariables) {
            CheckBoxTreeItem<String> varItem = new CheckBoxTreeItem<>(var.getName());

            for (String label : var.getLabels().keySet()) {
                CheckBoxTreeItem<String> labelItem = new CheckBoxTreeItem<>(label);
                varItem.getChildren().add(labelItem);
            }
            rootItem.getChildren().add(varItem);
        }

        TreeView<String> treeView = new TreeView<>(rootItem);
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView());
        return treeView;
    }

    private void extractQualifiers(TreeItem<String> root, List<Qualifier> list, List<Function<Car, Double>> funcs) {
        for (TreeItem<String> varNode : root.getChildren()) {
            String varName = varNode.getValue();
            LinguisticVariable variable = allVariables.stream().filter(v -> v.getName().equals(varName)).findFirst().orElse(null);
            if (variable == null) continue;

            for (TreeItem<String> labelNode : varNode.getChildren()) {
                if (((CheckBoxTreeItem<String>) labelNode).isSelected()) {
                    list.add(new Qualifier(variable, labelNode.getValue()));
                    funcs.add(attributeExtractors.get(varName));
                }
            }
        }
    }

    private void extractSummarizers(TreeItem<String> root, List<Summarizer> list, List<Function<Car, Double>> funcs) {
        for (TreeItem<String> varNode : root.getChildren()) {
            String varName = varNode.getValue();
            LinguisticVariable variable = allVariables.stream().filter(v -> v.getName().equals(varName)).findFirst().orElse(null);
            if (variable == null) continue;

            for (TreeItem<String> labelNode : varNode.getChildren()) {
                if (((CheckBoxTreeItem<String>) labelNode).isSelected()) {
                    list.add(new Summarizer(variable, labelNode.getValue()));
                    funcs.add(attributeExtractors.get(varName));
                }
            }
        }
    }
}