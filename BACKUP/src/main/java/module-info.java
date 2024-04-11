module com.mycompany.roomsearchv1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.roomsearchv1 to javafx.fxml;
    exports com.mycompany.roomsearchv1;
}
