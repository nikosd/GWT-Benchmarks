package com.nikosd.gwtSets.client;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class GwtSets implements EntryPoint {

    private FlexTable grid;
    private TextBox textbox;

    private int repeats = 10000;

    private String[] preInitializedArray;

    private ArrayList<String> preInitializedArrayList;
    private LinkedList<String> preInitializedLinkedList;
    private HashSet<String> preInitializedHashSet;

    private LinkedHashSet<String> preInitializedLinkedHashSet;
    private TreeSet<String> preInitializedTreeSet;
    private HashMap<String, Integer> preInitializedHashMap;
    private LinkedHashMap<String, Integer> preInitializedLinkedHashMap;

    public void onModuleLoad() {
        FlowPanel panel = new FlowPanel();

        Label label = new Label("Number of iterations : ");

        textbox = new TextBox();
        textbox.setText(String.valueOf(repeats));
        textbox.addKeyPressHandler(new KeyPressHandler(){
            public void onKeyPress(KeyPressEvent keyPressEvent) {
                if (keyPressEvent.getCharCode() == KeyCodes.KEY_ENTER) {
                    runTests();
                }
            }
        });

        Button button = new Button("Start metrics!");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                runTests();
            }
        });

        grid = new FlexTable();

        panel.add(label);
        panel.add(textbox);
        panel.add(button);
        panel.add(grid);

        RootPanel.get("gwt-container").add(panel);
    }

    private void runTests() {
        initialize();

        grid.removeAllRows();
        addHeaderRow();

        DeferredCommand.addCommand(new IncrementalCommand() {

            static final int TOTAL_STEPS = 16;
            int currentStep = 0;

            public boolean execute() {
                switch (currentStep) {
                    case 0:
                        arrayInitTest();
                        break;

                    case 1:
                        arrayListInitializationTest(true);
                        arrayListInitializationTest(false);
                        break;

                    case 2:
                        arrayListInitializationTest(Arrays.asList(preInitializedArray));
                        arrayListInitializationTest(preInitializedArrayList);
                        arrayListInitializationTest(preInitializedLinkedList);
                        break;

                    case 3:
                        arrayListInitializationTest(preInitializedHashSet);
                        arrayListInitializationTest(preInitializedLinkedHashSet);
                        arrayListInitializationTest(preInitializedTreeSet);
                        break;

                    case 4:
                        linkedListInitializationTest(Arrays.asList(preInitializedArray));
                        linkedListInitializationTest(preInitializedArrayList);
                        linkedListInitializationTest(preInitializedLinkedList);
                        break;

                    case 5:
                        linkedListInitializationTest(preInitializedHashSet);
                        linkedListInitializationTest(preInitializedLinkedHashSet);
                        linkedListInitializationTest(preInitializedTreeSet);
                        break;

                    case 6:
                        hashSetInitializationTest(true);
                        hashSetInitializationTest(false);
                        hashSetInitializationTest(Arrays.asList(preInitializedArray));
                        hashSetInitializationTest(preInitializedArrayList);
                        hashSetInitializationTest(preInitializedLinkedList);
                        break;

                    case 7:
                        hashSetInitializationTest(preInitializedHashSet);
                        hashSetInitializationTest(preInitializedLinkedHashSet);
                        hashSetInitializationTest(preInitializedTreeSet);
                        break;

                    case 8:
                        linkedHashSetInitializationTest(Arrays.asList(preInitializedArray));
                        linkedHashSetInitializationTest(preInitializedArrayList);
                        linkedHashSetInitializationTest(preInitializedLinkedList);
                        break;

                    case 9:
                        linkedHashSetInitializationTest(preInitializedHashSet);
                        linkedHashSetInitializationTest(preInitializedLinkedHashSet);
                        linkedHashSetInitializationTest(preInitializedTreeSet);
                        break;

                    case 10:
                        treeSetInitializationTest(Arrays.asList(preInitializedArray));
                        treeSetInitializationTest(preInitializedArrayList);
                        treeSetInitializationTest(preInitializedLinkedList);
                        break;

                    case 11:
                        treeSetInitializationTest(preInitializedHashSet);
                        treeSetInitializationTest(preInitializedLinkedHashSet);
                        treeSetInitializationTest(preInitializedTreeSet);
                        break;

                    case 12:
                        arrayIterateTest(preInitializedArray);
                        break;

                    case 13:
                        iterateTest(preInitializedArrayList);
                        iterateTest(preInitializedLinkedList);
                        break;

                    case 14:
                        iterateTest(preInitializedHashSet);
                        iterateTest(preInitializedLinkedHashSet);
                        iterateTest(preInitializedTreeSet);
                        break;

                    case 15:
                        mapIterateTest(preInitializedHashMap);
                        mapIterateTest(preInitializedLinkedHashMap);
                        break;
                }

                currentStep++;
                return currentStep < TOTAL_STEPS;
            }
        });

    }

    private void initialize() {
        repeats = Integer.valueOf(textbox.getText());

        preInitializedArray = new String[repeats];
        for (int i = 0; i < preInitializedArray.length; i++) {
            preInitializedArray[i] = String.valueOf(i);
        }

        preInitializedArrayList = new ArrayList<String>(Arrays.asList(preInitializedArray));
        preInitializedLinkedList = new LinkedList<String>(Arrays.asList(preInitializedArray));

        preInitializedHashSet = new HashSet<String>(Arrays.asList(preInitializedArray));
        preInitializedLinkedHashSet = new LinkedHashSet<String>(Arrays.asList(preInitializedArray));
        preInitializedTreeSet = new TreeSet<String>(Arrays.asList(preInitializedArray));

        preInitializedHashMap = new HashMap<String, Integer>();
        for (int i = 0; i < repeats; i++) {
            preInitializedHashMap.put(String.valueOf(i), i);
        }
        preInitializedLinkedHashMap = new LinkedHashMap<String, Integer>(preInitializedHashMap);
    }

    private String[] arrayInitTest() {
        Duration duration = new Duration();
        String[] array = new String[repeats];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i);
        }
        addTestRow("Array initialization", duration.elapsedMillis());
        return array;
    }

    private ArrayList<String> arrayListInitializationTest(boolean withCapacity) {
        Duration duration = new Duration();
        ArrayList<String> list;
        if (withCapacity) {
            list = new ArrayList<String>(repeats);
        } else {
            list = new ArrayList<String>();
        }
        for (int i = 0; i < repeats; i++) {
            list.add(String.valueOf(i));
        }
        addTestRow("ArrayList initialization with initial capacity " + withCapacity +" and a for loop for adding", duration.elapsedMillis());
        return list;
    }

    private ArrayList<String> arrayListInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        ArrayList<String> list = new ArrayList<String>(collection);
        addTestRow("ArrayList initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return list;
    }

    private LinkedList<String> linkedListInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        LinkedList<String> list = new LinkedList<String>(collection);
        addTestRow("LinkedList initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return list;
    }

    private HashSet<String> hashSetInitializationTest(boolean withCapacity) {
        Duration duration = new Duration();
        HashSet<String> set;
        if (withCapacity) {
            set = new HashSet<String>(repeats);
        } else {
            set = new HashSet<String>();
        }
        for (int i = 0; i < repeats; i++) {
            set.add(String.valueOf(i));
        }
        addTestRow("HashSet initialization with initial capacity " + withCapacity + " and a for loop for adding", duration.elapsedMillis());
        return set;
    }

    private HashSet<String> hashSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        HashSet<String> set = new HashSet<String>(collection);
        addTestRow("HashSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private LinkedHashSet<String> linkedHashSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        LinkedHashSet<String> set = new LinkedHashSet<String>(collection);
        addTestRow("LinkedHashSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private TreeSet<String> treeSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        TreeSet<String> set = new TreeSet<String>(collection);
        addTestRow("TreeSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private String arrayIterateTest(String[] array) {
        String dummyValue = "";
        Duration duration = new Duration();
        for (int i = 0; i < array.length; i++) {
            dummyValue = array[i];
        }
        addTestRow("Native array iteration", duration.elapsedMillis());
        return dummyValue;
    }

    private String iterateTest(Collection<String> set) {
        String dummyValue = "";
        Duration duration = new Duration();
        for (String string : set) {
            dummyValue = string;
        }
        addTestRow(set.getClass() + " iteration", duration.elapsedMillis());
        return dummyValue;
    }

    private Object mapIterateTest(Map<String, Integer> map) {
        Object dummyValue = null;
        Duration duration = new Duration();
        for (String string : map.keySet()) {
            dummyValue = string;
        }
        addTestRow(map.getClass() + " iteration around *keys*", duration.elapsedMillis());

        duration = new Duration();
        for (int i : map.values()) {
            dummyValue = i;
        }
        addTestRow(map.getClass() + " iteration around *values*", duration.elapsedMillis());

        return dummyValue;
    }

    private void addHeaderRow() {
        int numRows = grid.getRowCount();
        grid.setText(numRows, 0, "Running the tests for " + repeats + " iterations....");
        grid.setText(numRows, 1, "");
        grid.setText(numRows + 1, 0, "");
        grid.setText(numRows + 1, 1, "");
    }

    private void addTestRow(String description, int time) {
        int numRows = grid.getRowCount();
        grid.setText(numRows, 0, description);
        grid.setText(numRows, 1, time + " ms");
    }
}
