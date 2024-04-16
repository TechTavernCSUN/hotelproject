module com.mycompany.demoproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.demoproj to javafx.fxml;
    exports com.mycompany.demoproj;
}
