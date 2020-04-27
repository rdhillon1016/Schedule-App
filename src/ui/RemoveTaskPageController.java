package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RemoveTaskPageController {

    public TreeView<String> treeView;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

    public void initialize(){
        constructTree();
    }

    private void constructTree(){
        ScheduleApp sa = ScheduleApp.getInstance();
        TreeItem<String> root = new TreeItem<>();
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        root.setExpanded(true);
        for (Day b : sa.getMapOfDays().values()){
            String dateString = b.getDateAsString();
            TreeItem<String> branch = makeFirstBranch(dateString, root);
            makeSecondBranches(b, branch);
        }
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    private TreeItem<String> makeFirstBranch(String dateString, TreeItem<String> parent){
        TreeItem<String> item = new TreeItem<>(dateString);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

    private void makeSecondBranches(Day b, TreeItem<String> parent){
        if (!(b.getTasks().size() == 0)){
            makeTaskLeafs(b, parent);
        }
        if (!(b.getUttasks().size() == 0)){
            makeUntimedTaskLeafs(b, parent);
        }
        if (!(b.getMeals().size() == 0)){
            makeMealLeafs(b, parent);
        }
    }

    private void makeTaskLeafs(Day b, TreeItem<String> parent){
        TreeItem<String> mealsItem = new TreeItem<>("Timed");
        parent.getChildren().add(mealsItem);
        mealsItem.setExpanded(true);
        for (Task m : b.getTasks()){
            TreeItem<String> item = new TreeItem<>(m.getNumber() + ". " + sdf2.format(m.getStartTaskHour())
                    + " to " + sdf2.format(m.getEndTaskHour()) + " : " +
                    m.getTaskDescription() + ".");
            mealsItem.getChildren().add(item);
        }
    }

    private void makeUntimedTaskLeafs(Day b, TreeItem<String> parent){
        TreeItem<String> mealsItem = new TreeItem<>("Untimed");
        parent.getChildren().add(mealsItem);
        mealsItem.setExpanded(true);
        for (UntimedTask m : b.getUttasks()){
            TreeItem<String> item = new TreeItem<>(m.getNumber() + ". " +
                    m.getTaskDescription());
            mealsItem.getChildren().add(item);
        }
    }

    private void makeMealLeafs(Day b, TreeItem<String> parent){
        TreeItem<String> mealsItem = new TreeItem<>("Meals");
        parent.getChildren().add(mealsItem);
        mealsItem.setExpanded(true);
        for (Meal m : b.getMeals()){
            TreeItem<String> item = new TreeItem<>(m.getNumber() + ". " +
                    m.getTaskDescription() + ". " + m.getCalories() + " calories.");
            mealsItem.getChildren().add(item);
        }
    }

    public void handleRemoveButton(ActionEvent event) throws IOException, ParseException {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (!(selectedItem == null) && (selectedItem.getChildren().size() == 0)){
            removeItem(selectedItem);
            handleBackButton(event);
        } else {
            ErrorWindow.show();
        }
    }

    private void removeItem(TreeItem<String> selected) throws ParseException {
        ScheduleApp sa = ScheduleApp.getInstance();
        int index = Integer.parseInt(selected.getValue().substring(0, 1));
        Day d = sa.getDay(sa.getSdf().parse(selected.getParent().getParent().getValue()));
        d.removeATask(index);
        sa.removeEmptyDays();
    }

}
