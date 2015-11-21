package sample;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Created by Isaac on 11/21/2015.  * Created by Isaac on 11/21/2015.  Inspired by
 * Mateus Vicari and JulianG, from StackExchange.  Changed to suit my nefarious purpose (not autocompletion)

 */
    public class AutoCompleteComboBoxListener{

        private ComboBox<String> comboBox;
        private StringBuilder sb;
        private int lastLength;

        public AutoCompleteComboBoxListener(final ComboBox<String> cb) {
            this.comboBox = cb;
            sb = new StringBuilder();

            this.comboBox.setEditable(true);
            // add a focus listener such that if not in focus, reset the filtered typed keys

            comboBox.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue){
                        lastLength = 0;
                        sb.delete(0, sb.length());
                        selectClosestResultBasedOnTextFieldValue(false, false);
                                }

                    }
                });


            comboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    selectClosestResultBasedOnTextFieldValue(true, true);
                }
            });

            this.comboBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event){
                // this variable is used to bypass the auto complete process if the length is the same.
                // this occurs if user types fast, the length of textfield will record after the user
                // has typed after a certain delay.
                if (lastLength != (comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length()))
                    lastLength = comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length();

                if (event.isControlDown() || event.getCode() == KeyCode.BACK_SPACE ||
                        event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT ||
                        event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.HOME ||
                        event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB
                        )
                    return;

                if (event.getCode().equals(KeyCode.DOWN)) {
                    comboBox.show();
                    return;
                }

                IndexRange ir = comboBox.getEditor().getSelection();
                sb.delete(0, sb.length());
                sb.append(comboBox.getEditor().getText());
                // remove selected string index until end so only unselected text will be recorded
                try {
                    sb.delete(ir.getStart(), sb.length());
                } catch (Exception ignored) { }

                ObservableList<String> items = comboBox.getItems();
                  ArrayList<String> tempItems = reduceArrayListFromPartialString(new ArrayList<String>(items), comboBox.getEditor().getText());
                    comboBox.show();
                    ListView<String> lv = ((ComboBoxListViewSkin) comboBox.getSkin()).getListView();
                    lv.setItems(FXCollections.observableArrayList(tempItems));
                    lv.scrollTo(0);
            }});

        }

        /*
         * selectClosestResultBasedOnTextFieldValue() - selects the item and scrolls to it when
         * the popup is shown.
         *
         * parameters:
         *  affect - true if combobox is clicked to show popup so text and caret position will be readjusted.
         *  inFocus - true if combobox has focus. If not, programmatically press enter key to add new entry to list.
         *
         */

    public ArrayList<String> reduceArrayListFromPartialString(ArrayList<String> a, String partial) {
        ArrayList<String> ret = new ArrayList<String>();
        for (String x : a) {
            if (x.toLowerCase().contains(partial.toLowerCase())) {
                ret.add(x);
            }
        }
        //return the original in case of 0 matches
        if(ret.size() == 0) return a;

        return ret;
    }
        private void selectClosestResultBasedOnTextFieldValue(boolean affect, boolean inFocus) {
            ObservableList<String> items = AutoCompleteComboBoxListener.this.comboBox.getItems();
            boolean found = false;
            for (int i=0; i<items.size(); i++) {
                if (AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase().contains(items.get(i).toLowerCase())) {
                    try {
                        ListView lv = ((ComboBoxListViewSkin) AutoCompleteComboBoxListener.this.comboBox.getSkin()).getListView();
                        lv.getSelectionModel().clearAndSelect(i);
                        lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
                        found = true;
                        break;
                    } catch (Exception ignored) { }
                }
            }

            String s = comboBox.getEditor().getText();
            if (!found && affect) {
                comboBox.getSelectionModel().clearSelection();
                comboBox.getEditor().setText(s);
                comboBox.getEditor().end();
            }

            if (!found) {
                comboBox.getEditor().setText(null);
                comboBox.getSelectionModel().select(null);
                comboBox.setValue(null);
            }

            if (!inFocus && comboBox.getEditor().getText() != null && comboBox.getEditor().getText().trim().length() > 0) {
                // press enter key programmatically to have this entry added
//          KeyEvent ke = new KeyEvent(comboBox, KeyCode.ENTER.toString(), KeyCode.ENTER.getName(),
//          KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_RELEASED);
                KeyEvent ke = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.ENTER.toString(), KeyCode.ENTER.toString(),
                        KeyCode.ENTER, false, false, false, false);
                comboBox.fireEvent(ke);
            }
        }

    }
