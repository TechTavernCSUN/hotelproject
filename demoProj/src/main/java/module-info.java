module com.mycompany.demoproj {
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.demoproj to javafx.fxml;
    exports com.mycompany.demoproj;
}
