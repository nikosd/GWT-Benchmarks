package com.nikosd.gwtSets.client;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.*;

import java.util.*;

public class GwtSets implements EntryPoint {

    private static int REPEATS = 10000;

    private FlowPanel panel;
    private Button button;
    private FlexTable grid;

    private final String[] preInitializedArray;

    private final ArrayList<String> preInitializedArrayList;
    private final LinkedList<String> preInitializedLinkedList;

    private final HashSet<String> preInitializedHashSet;
    private final LinkedHashSet<String> preInitializedLinkedHashSet;
    private final TreeSet<String> preInitializedTreeSet;

    private final HashMap<String, Integer> preInitializedHashMap;
    private final LinkedHashMap<String, Integer> preInitializedLinkedHashMap;

    public GwtSets() {
        preInitializedArray = new String[REPEATS];
        for (int i = 0; i < preInitializedArray.length; i++) {
            preInitializedArray[i] = String.valueOf(i);
        }

        preInitializedArrayList = new ArrayList<String>(Arrays.asList(preInitializedArray));
        preInitializedLinkedList = new LinkedList<String>(Arrays.asList(preInitializedArray));

        preInitializedHashSet = new HashSet<String>(Arrays.asList(preInitializedArray));
        preInitializedLinkedHashSet = new LinkedHashSet<String>(Arrays.asList(preInitializedArray));
        preInitializedTreeSet = new TreeSet<String>(Arrays.asList(preInitializedArray));

        preInitializedHashMap = new HashMap<String, Integer>();
        for (int i = 0; i < REPEATS; i++) {
            preInitializedHashMap.put(String.valueOf(i), i);
        }
        preInitializedLinkedHashMap = new LinkedHashMap<String, Integer>(preInitializedHashMap);
    }

    public void onModuleLoad() {
        panel = new FlowPanel();
        button = new Button("Start metrics!");
        grid = new FlexTable();

        button.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {

                addEmptyRow();
                DeferredCommand.addCommand(new IncrementalCommand() {

                    static final int TOTAL_STEPS = 10;
                    int currentStep = 0;

                    public boolean execute() {
                        switch (currentStep) {
                            case 0:
                                runNativeArrayInitTests();
                                break;
                            case 1:
                                runArrayListInitializationTests();
                                break;
                            case 2:
                                runLinkedListInitializationTests();
                                break;
                            case 3:
                                runHashSetInitializationTests();
                                break;
                            case 4:
                                runLinkedHashSetInitializationTests();
                                break;
                            case 5:
                                runTreeSetInitializationTests();
                                break;
                            case 6:
                                runNativeArrayIterationTests();
                                break;
                            case 7:
                                runListIterationTests();
                                break;
                            case 8:
                                runSetIterationTests();
                                break;
                            case 9:
                                runMapIterationTests();
                                break;
                        }
                        currentStep++;
                        return currentStep < TOTAL_STEPS;
                    }
                });

            }

        });

        panel.add(button);
        panel.add(grid);

        RootPanel.get("gwt-container").add(panel);
    }

    private void runNativeArrayInitTests() {
        arrayInitTest();
    }

    private void runArrayListInitializationTests() {
        // with for
        arrayListInitializationTest(true);
        arrayListInitializationTest(false);

        // from lists
        arrayListInitializationTest(Arrays.asList(preInitializedArray));
        arrayListInitializationTest(preInitializedArrayList);
        arrayListInitializationTest(preInitializedLinkedList);

        // from sets
        arrayListInitializationTest(preInitializedHashSet);
        arrayListInitializationTest(preInitializedLinkedHashSet);
        arrayListInitializationTest(preInitializedTreeSet);
    }

    private void runLinkedListInitializationTests() {
        // from lists
        linkedListInitializationTest(Arrays.asList(preInitializedArray));
        linkedListInitializationTest(preInitializedArrayList);
        linkedListInitializationTest(preInitializedLinkedList);

        // from sets
        linkedListInitializationTest(preInitializedHashSet);
        linkedListInitializationTest(preInitializedLinkedHashSet);
        linkedListInitializationTest(preInitializedTreeSet);
    }

    private void runHashSetInitializationTests() {
        // with for
        hashSetInitializationTest(true);
        hashSetInitializationTest(false);
        // from lists
        hashSetInitializationTest(Arrays.asList(preInitializedArray));
        hashSetInitializationTest(preInitializedArrayList);
        hashSetInitializationTest(preInitializedLinkedList);

        // from sets
        hashSetInitializationTest(preInitializedHashSet);
        hashSetInitializationTest(preInitializedLinkedHashSet);
        hashSetInitializationTest(preInitializedTreeSet);
    }

    private void runLinkedHashSetInitializationTests() {
        // from lists
        linkedHashSetInitializationTest(Arrays.asList(preInitializedArray));
        linkedHashSetInitializationTest(preInitializedArrayList);
        linkedHashSetInitializationTest(preInitializedLinkedList);

        // from sets
        linkedHashSetInitializationTest(preInitializedHashSet);
        linkedHashSetInitializationTest(preInitializedLinkedHashSet);
        linkedHashSetInitializationTest(preInitializedTreeSet);
    }

    private void runTreeSetInitializationTests() {
        // from lists
        treeSetInitializationTest(Arrays.asList(preInitializedArray));
        treeSetInitializationTest(preInitializedArrayList);
        treeSetInitializationTest(preInitializedLinkedList);

        // from sets
        treeSetInitializationTest(preInitializedHashSet);
        treeSetInitializationTest(preInitializedLinkedHashSet);
        treeSetInitializationTest(preInitializedTreeSet);
    }

    private void runNativeArrayIterationTests() {
        arrayIterateTest(preInitializedArray);
    }

    private void runListIterationTests() {
        iterateTest(preInitializedArrayList);
        iterateTest(preInitializedLinkedList);
    }

    private void runSetIterationTests() {
        iterateTest(preInitializedHashSet);
        iterateTest(preInitializedLinkedHashSet);
        iterateTest(preInitializedTreeSet);
    }

    private void runMapIterationTests() {
        mapIterateTest(preInitializedHashMap);
        mapIterateTest(preInitializedLinkedHashMap);
    }

    private String[] arrayInitTest() {
        Duration duration = new Duration();
        String[] array = new String[REPEATS];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(i);
        }
        addRow("Array initialization", duration.elapsedMillis());
        return array;
    }

    private ArrayList<String> arrayListInitializationTest(boolean withCapacity) {
        Duration duration = new Duration();
        ArrayList<String> list;
        if (withCapacity) {
            list = new ArrayList<String>(REPEATS);
        } else {
            list = new ArrayList<String>();
        }
        for (int i = 0; i < REPEATS; i++) {
            list.add(String.valueOf(i));
        }
        addRow("ArrayList initialization with initial capacity " + withCapacity +" and a for loop for adding", duration.elapsedMillis());
        return list;
    }

    private ArrayList<String> arrayListInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        ArrayList<String> list = new ArrayList<String>(collection);
        addRow("ArrayList initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return list;
    }

    private LinkedList<String> linkedListInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        LinkedList<String> list = new LinkedList<String>(collection);
        addRow("LinkedList initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return list;
    }

    private HashSet<String> hashSetInitializationTest(boolean withCapacity) {
        Duration duration = new Duration();
        HashSet<String> set;
        if (withCapacity) {
            set = new HashSet<String>(REPEATS);
        } else {
            set = new HashSet<String>();
        }
        for (int i = 0; i < REPEATS; i++) {
            set.add(String.valueOf(i));
        }
        addRow("HashSet initialization with initial capacity " + withCapacity + " and a for loop for adding", duration.elapsedMillis());
        return set;
    }

    private HashSet<String> hashSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        HashSet<String> set = new HashSet<String>(collection);
        addRow("HashSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private LinkedHashSet<String> linkedHashSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        LinkedHashSet<String> set = new LinkedHashSet<String>(collection);
        addRow("LinkedHashSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private TreeSet<String> treeSetInitializationTest(Collection<String> collection) {
        Duration duration = new Duration();
        TreeSet<String> set = new TreeSet<String>(collection);
        addRow("TreeSet initialization from " + collection.getClass() + "", duration.elapsedMillis());
        return set;
    }

    private String arrayIterateTest(String[] array) {
        String dummyValue = "";
        Duration duration = new Duration();
        for (int i = 0; i < array.length; i++) {
            dummyValue = array[i];
        }
        addRow("Native array iteration", duration.elapsedMillis());
        return dummyValue;
    }

    private String iterateTest(Collection<String> set) {
        String dummyValue = "";
        Duration duration = new Duration();
        for (String string : set) {
            dummyValue = string;
        }
        addRow(set.getClass() + " iteration", duration.elapsedMillis());
        return dummyValue;
    }

    private Object mapIterateTest(Map<String, Integer> map) {
        Object dummyValue = null;
        Duration duration = new Duration();
        for (String string : map.keySet()) {
            dummyValue = string;
        }
        addRow(map.getClass() + " iteration around *keys*", duration.elapsedMillis());

        duration = new Duration();
        for (int i : map.values()) {
            dummyValue = i;
        }
        addRow(map.getClass() + " iteration around *values*", duration.elapsedMillis());

        return dummyValue;
    }

    private void addEmptyRow() {
        int numRows = grid.getRowCount();
        grid.setText(numRows, 0, "------");
        grid.setText(numRows, 1, "------");
    }

    private void addRow(String description, int time) {
        int numRows = grid.getRowCount();
        grid.setText(numRows, 0, description);
        grid.setText(numRows, 1, String.valueOf(time));
    }
}
